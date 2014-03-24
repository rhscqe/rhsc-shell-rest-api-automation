package com.redhat.qe.test.rest.rebalance;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.repository.StepsRepositoryHelper;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.rest.StepRepository;

import dstywho.timeout.Duration;
import dstywho.timeout.Timeout;
public class RebalanceStatusWhenRebalanceIsCompleteTest extends VolumeTestBase{
	
	
	@Before
	public void startTheVolume(){
		getVolumeRepository(getVolume().getCluster()).start(getVolume());
	}
	
	@After
	public void stopTheVolume(){
		getVolumeRepository(getVolume().getCluster()).stop(getVolume());
	}

	@Test
	public void test(){
		Volume volume = getVolume();
		rebalanceAndVerifyJobFinished(volume);
	}

	private void rebalanceAndVerifyJobFinished(Volume volume) {
		Action rebalAction = getVolumeRepository(volume.getCluster()).rebalance(volume);
		final Job job = showJob(rebalAction.getJob());
		assertTrue(WaitUtil.waitUntil(new dstywho.functional.Predicate() {
			@Override
			public Boolean act() {
				Timeout.TIMEOUT_FIVE_SECONDS.sleep();
				return showJob(job).getStatus().getState().equalsIgnoreCase("finished");
			}
		}, 20).isSuccessful());
		
		ArrayList<Step> steps = getStepRepository(job).list();
		
		Step rebalanceStep = new StepsRepositoryHelper().getRebalanceStep(getStepRepository(job));
		Assert.assertEquals("FINISHED", rebalanceStep.getStatus().getState());
		Assert.assertEquals("FINISHED", getStepRepository(job).show(rebalanceStep.getParentStep()).getStatus().getState());
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
	protected List<Volume> getVolumesToBeCreated() {
		ArrayList<Volume> volumes = new ArrayList<Volume>();
		volumes.add(new VolumeFactory().distributed("rebal", getHosts().toArray(new Host[0])));
		return volumes;
	}

	

}
