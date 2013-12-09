package com.redhat.qe.test.rest.rebalance;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.repository.JobRepoHelper;
import com.redhat.qe.helpers.repository.StepsRepositoryHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.FileNameHelper;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.repository.sh.DD;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.RebalanceTestBase;
public class RebalanceStatusWhenNodeDownDuringTest extends RebalanceTestBase{

	private Job job = null;

	/**
	 * @return the job
	 */
	public synchronized Job getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public synchronized void setJob(Job job) {
		this.job = job;
	}

	@Override
	protected void populateVolume() {
		AbsolutePath file = mountPoint.add(FileNameHelper.randomFileName());
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.start();
		try {
			for(int i=0; i< 10 ; i ++){
				sshSession.runCommandAndAssertSuccess(DD.writeRandomData(file.toString(),FileSize.megaBytes(100)).toString());
			}
		} finally {
			sshSession.stop();
 		}
	}

	@Test
	@Ignore
	public void test() throws InterruptedException{
		Thread startRebalanceThread = new Thread(new Runnable() {
			
			public void run() {
				Action rebalAction = getVolumeRepository(volume.getCluster()).rebalance(volume);
				setJob(rebalAction.getJob());
			}
		});
		Thread stopGlusterDThread = new Thread(new Runnable() {
			
			public void run() {
				stopGlusterOnHost1();
			}
		});
		
		startRebalanceThread.start();
		Thread.sleep(1000);
		stopGlusterDThread.start();
		stopGlusterDThread.join();
		startRebalanceThread.join();

		try{
			getGlusterdTaskSttus();
			final Job job = showJob(getJob());
			assertTrue(WaitUtil.waitUntil(new dstywho.functional.Predicate() {
				@Override
				public Boolean act() {
					return showJob(job).getStatus().getState().equalsIgnoreCase("finished");
				}
			}, 20).isSuccessful());
			
			ArrayList<Step> steps = getStepRepository(job).list();
			
			Step rebalanceStep = new StepsRepositoryHelper().getRebalanceStep(getStepRepository(job));
			Assert.assertEquals("FINISHED", rebalanceStep.getStatus().getState());
			Assert.assertEquals("FINISHED", getStepRepository(job).show(rebalanceStep.getParentStep()).getStatus().getState());
		}finally{
			startGlusterOnHost1();
		}
	}

	/**
	 * 
	 */
	private String getGlusterdTaskSttus() {
		ExecSshSession host2session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost2()));
		host2session.start();
		try{
			String blah = new VolumeXmlRepository(host2session).status(volume).getTasks().get(0).getStatusStr();
			System.out.println("xxxxxxxxxxxxxxxxxx" + blah);
			return blah;
			
		}finally{
			
			host2session.stop();
		}
	}

	public void stopGlusterOnHost1() {
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		host1session.start();
		try{
			host1session.runCommandAndAssertSuccess("service glusterd stop");
		}finally{
			host1session.stop();
		}
	}

	public void startGlusterOnHost1() {
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		host1session.start();
		try{
			host1session.runCommandAndAssertSuccess("service glusterd start");
		}finally{
			host1session.stop();
		}
	}

	/**
	 * @param job
	 * @return
	 */
	private StepRepository getStepRepository(final Job job) {
		return new StepRepository(getSession(), job);
	}

	/**
	 * @param rebalAction
	 * @return
	 */
	private Job showJob(Job job) {
		return new JobRepository(getSession()).show(job);
	}
	

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("rebalstatusComplete", getHosts().toArray(new Host[0]));
	}

	

}
