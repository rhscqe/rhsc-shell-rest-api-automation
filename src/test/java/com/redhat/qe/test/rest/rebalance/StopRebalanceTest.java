package com.redhat.qe.test.rest.rebalance;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicate;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.test.rest.RebalanceTestBase;

public class StopRebalanceTest extends RebalanceTestBase{
	private Action action;

	@Before
	public void startRebalance(){
		action = getVolumeRepository(getHost1().getCluster()).rebalance(volume);
//		Job job = new JobRepository(getSession()).show(action.getJob());
	}
	
	@Tcms("318693")
	@Test
	public void test(){
		ResponseWrapper result = getVolumeRepository(getHost1().getCluster()).stopRebalance(volume);

		Job job = verifyJobAndStatus();
		
		verifyStepTypeAndStatus(job);
	}

	private Job verifyJobAndStatus() {
		Job job = new JobRepository(getSession()).show(action.getJob());
		Assert.assertEquals("Job Status", "FAILED",job.getStatus().getState());
		Assert.assertNotNull( job.getEndTime());
		return job;
	}

	/**
	 * @param job
	 */
	private void verifyStepTypeAndStatus(Job job) {
		Step step = getRebalanceStep(job);
		Assert.assertEquals("Rebalance Step Status", "ABORTED",step.getStatus().getState());
		Assert.assertNotNull( step.getEndTime());
		
		Step executingStep = getStepRepo(job).show(step.getParentStep());
		Assert.assertEquals("Executing Step type", "executing",executingStep.getType());
		Assert.assertEquals("Executing Step status", "FAILED",executingStep.getStatus().getState());
	}

	private Step getRebalanceStep(Job job) {
		return CollectionUtils.findFirst(getStepRepo(job).list(), new Predicate<Step>() {

			public boolean apply(Step step) {
				return step.getDescription().toLowerCase().contains("rebalan");
			}
		});

	}

	/**
	 * @param job
	 * @return
	 */
	private StepRepository getStepRepo(Job job) {
		return new StepRepository(getSession(), job);
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributedUneven("stoprebalancetest", getHost1(), getHost2());
	}

}
