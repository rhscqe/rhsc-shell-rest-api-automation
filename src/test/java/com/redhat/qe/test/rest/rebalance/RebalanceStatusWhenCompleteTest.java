package com.redhat.qe.test.rest.rebalance;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.repository.JobRepoHelper;
import com.redhat.qe.helpers.repository.StepsRepositoryHelper;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil.WaitResult;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.glustercli.RebalanceStatus;
import com.redhat.qe.repository.glustercli.VolumeRebalanceRepository;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.RebalanceTestBase;
import com.redhat.qe.test.rest.VolumeTestBase;

public class RebalanceStatusWhenCompleteTest extends RebalanceTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributed("rebalstatuswhencompletetest", getHost1(), getHost2());
	}
	
	@Test
	public void test(){
		Action rebalAction = getVolumeRepository().rebalance(volume);
		Job job = getJobRepo().show(rebalAction.getJob());
		WaitResult waitResult = new JobRepoHelper().waitUntilJobFinished(getJobRepo(), job);
		Assert.assertTrue(waitResult.isSuccessful());
		
		//complete
		Step rebalanceStep = new StepsRepositoryHelper().getRebalanceStep(new StepRepository(getSession(), job));
		Asserts.assertEqualsIgnoreCase("finished", rebalanceStep.getStatus().getState());
		ExecSshSession host1session = ExecSshSession.fromHost(getHost1ToBeCreated());
		host1session.start();
		try{
			RebalanceStatus expectedRebalanceStats = new VolumeRebalanceRepository(host1session).getRebalanceAggregateStatus(volume);
			Assert.assertTrue(rebalanceStep.getDescription().contains(String.format("scanned: %s, moved: %s, failed: %s", expectedRebalanceStats.getLookups(), expectedRebalanceStats.getFiles(), expectedRebalanceStats.getFailures())));
			Asserts.assertEqualsIgnoreCase("completed", expectedRebalanceStats.getStatusDecode());
		}finally{
			host1session.stop();
		}

	}

	/**
	 * @return
	 */
	private JobRepository getJobRepo() {
		return new JobRepository(getSession());
	}

}
