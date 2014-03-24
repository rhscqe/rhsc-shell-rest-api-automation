package com.redhat.qe.test.rest.rebalance;

import java.util.ArrayList;

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
import com.redhat.qe.test.rest.RebalanceTestBase;

import dstywho.timeout.Timeout;
public class RemoveBrickWhileRebalanceIsOccuringTest extends RebalanceTestBase{


//
//	@Override
//	protected void populateVolume() {
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
//	}

	@Test
	@Tcms("318696")
	@Ignore //this test case will be disabled because it's allowed
	public void test() throws InterruptedException{
		Action rebalAction = getVolumeRepository(volume.getCluster()).rebalance(volume);
		Job job = showJob(rebalAction.getJob());
		
		Timeout.TIMEOUT_ONE_SECOND.sleep();
		Timeout.TIMEOUT_ONE_SECOND.sleep();
		
		ArrayList<Brick> bricks = getBrickRepo().list();
		bricks.remove(0);
		ResponseWrapper result = 
				getBrickRepo()._collectionDelete(DeletionBrickWrapperList.fromBricks(bricks.toArray(new Brick[0])));
		result.expectSimilarCode(400);
		
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
		return new VolumeFactory().distributed("RemoveBrickWhileRebalanceIsOccuringTest", getHosts().toArray(new Host[0]));
	}

	

}
