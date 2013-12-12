package com.redhat.qe.test.rest.rebalance;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.repository.JobRepoHelper;
import com.redhat.qe.helpers.repository.StepsRepositoryHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.FileNameHelper;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.model.jaxb.DeletionBrickWrapperList;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.repository.sh.DD;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;
import com.redhat.qe.test.rest.RebalanceTestBase;

import dstywho.timeout.Timeout;
public class StartMigrationDuringRebalanceTest extends RebalanceTestBase{



	@Override
	protected void populateVolume() {
//		AbsolutePath file = mountPoint.add(FileNameHelper.randomFileName());
//		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
//		sshSession.start();
//		try {
//			for(int i=0; i< 15; i ++){
//				sshSession.runCommandAndAssertSuccess(DD.writeZeros(file.toString(),FileSize.megaBytes(500)).toString());
//				sshSession.runCommandAndAssertSuccess(DD.writeZeros(file.toString(),FileSize.megaBytes(500)).toString());
//			}
//		} finally {
//			sshSession.stop();
// 		}
	}

	@Test
	@Tcms("321106")
	public void test() throws InterruptedException{
		Action rebalAction = getVolumeRepository(volume.getCluster()).rebalance(volume);
		Job job = showJob(rebalAction.getJob());
		
		Timeout.TIMEOUT_ONE_SECOND.sleep();
		
		getGlusterVolumeStatus2();
		ArrayList<Brick> bricks = getBrickRepo().list();
		ResponseWrapper response = getBrickRepo()._migrate(bricks.get(0), bricks.get(1));
		response.expectSimilarCode(400);
		getGlusterVolumeStatus2();
		
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
		return VolumeFactory.distributed("StartMigrationDuringRebalanceTest", getHosts().toArray(new Host[0]));
	}

	private void getGlusterVolumeStatus2() {
		ExecSshSession hostsession = ExecSshSession.fromHost(getHost1ToBeCreated());
		hostsession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				return session.runCommandAndAssertSuccess("gluster vol status");
			}
		});
	}	

}
