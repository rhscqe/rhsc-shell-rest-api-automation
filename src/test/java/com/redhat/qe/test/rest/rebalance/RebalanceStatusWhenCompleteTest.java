package com.redhat.qe.test.rest.rebalance;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.repository.JobRepoHelper;
import com.redhat.qe.helpers.repository.StepsRepositoryHelper;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil.WaitResult;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.test.rest.VolumeTestBase;

public class RebalanceStatusWhenCompleteTest extends VolumeTestBase{
	
	@Before
	public void startVolume(){
		getVolumeRepository().start(volume);
	}
	
	@After
	public void stopVolume(){
		getVolumeRepository().stop(volume);
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("rebalstatuswhencompletetest", getHost1(), getHost2());
	}
	
	@Test
	public void test(){
		Action rebalAction = getVolumeRepository().rebalance(volume);
		Job job = getJobRepo().show(rebalAction.getJob());
		WaitResult waitResult = new JobRepoHelper().waitUntilJobFinished(getJobRepo(), job);
		Assert.assertTrue(waitResult.isSuccessful());
		
		//complete
		Step executingStep = new StepsRepositoryHelper().getExecutingStep(new StepRepository(getSession(), job));


	}

	/**
	 * @return
	 */
	private JobRepository getJobRepo() {
		return new JobRepository(getSession());
	}

}
