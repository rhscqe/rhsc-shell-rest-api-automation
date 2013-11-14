package com.redhat.qe.test.rest.rebalance;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicate;
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
	}
	
	@Test
	public void test(){
		ResponseWrapper result = getVolumeRepository(getHost1().getCluster()).stopRebalance(volume);

		System.out.println("ZZZZZZZZZZZZZZZZZZZ");
		System.out.println(result.getBody());
		System.out.println(result.getCode());
		Job job = new JobRepository(getSession()).show(action.getJob());
		Assert.assertEquals( "FAILED",job.getStatus().getState());
		Assert.assertNotNull( job.getEndTime());
		
		Step step = getRebalanceStep(job);
		Assert.assertEquals( "ABORTED",step.getStatus().getState());
		Assert.assertNotNull( step.getEndTime());
	}

	private Step getRebalanceStep(Job job) {
		return CollectionUtils.findFirst(new StepRepository(getSession(), job).list(), new Predicate<Step>() {

			public boolean apply(Step step) {
				return step.getDescription().toLowerCase().contains("rebalan");
			}
		});

	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("stoprebalancetest", getHost1(), getHost2());
	}

}
