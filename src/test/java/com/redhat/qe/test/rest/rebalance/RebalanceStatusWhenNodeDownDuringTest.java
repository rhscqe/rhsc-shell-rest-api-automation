package com.redhat.qe.test.rest.rebalance;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
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

import dstywho.timeout.Timeout;
public class RebalanceStatusWhenNodeDownDuringTest extends RebalanceTestBase{



	@Override
	protected void populateVolume() {
		AbsolutePath file = mountPoint.add(FileNameHelper.randomFileName());
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.start();
		try {
			for(int i=0; i< 16; i ++){
				sshSession.runCommandAndAssertSuccess(DD.writeZeros(file.toString(),FileSize.megaBytes(500)).toString());
				sshSession.runCommandAndAssertSuccess(DD.writeZeros(file.toString(),FileSize.megaBytes(500)).toString());
			}
		} finally {
			sshSession.stop();
 		}
	}

	@Test
	@Tcms("311413")
	public void test() throws InterruptedException{
		Action rebalAction = getVolumeRepository(volume.getCluster()).rebalance(volume);
		Job job = showJob(rebalAction.getJob());
		Timeout.TIMEOUT_ONE_SECOND.sleep();
		Timeout.TIMEOUT_ONE_SECOND.sleep();

		stopGlusterOnHost1();
		

		try{
			getGlusterdTaskSttus();
			final Job _job = showJob(job);
			Assert.assertTrue(new JobRepoHelper().waitUntilJobFinished(new JobRepository(getSession()), _job).isSuccessful());
			
			Step rebalanceStep = new StepsRepositoryHelper().getRebalanceStep(getStepRepository(job));
			Assert.assertEquals("FINISHED", rebalanceStep.getStatus().getState());
			Assert.assertEquals("FINISHED", getStepRepository(job).show(rebalanceStep.getParentStep()).getStatus().getState());
		}finally{
			startGlusterOnHost1();
			if(getHostRepository().show(getHost1()).getState().contains("operational")){
				getHostRepository().activate(getHost1());
				WaitUtil.waitForHostStatus(getHostRepository(), getHost1(), "up", 20);
			}
		}
	}

	private String getGlusterdTaskSttus() {
		ExecSshSession host2session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost2()));
		host2session.start();
		try{
			String blah = new VolumeXmlRepository(host2session).status(volume).getTasks().get(0).getStatusStr();
			host2session.runCommandAndAssertSuccess(String.format("gluster vol rebalance %s status --xml", volume.getName()));
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
		return new VolumeFactory().distributed("RebStatusWhenNodeGoesDownDuring", getHosts().toArray(new Host[0]));
	}

	

}
