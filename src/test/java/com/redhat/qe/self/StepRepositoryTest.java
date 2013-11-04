package com.redhat.qe.self;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.rest.TwoHostClusterTestBase;

public class StepRepositoryTest extends TwoHostClusterTestBase{
	
	@Test
	public void testList(){
		getHostRepository().deactivate(getHost1());
		getHostRepository().activate(getHost1());
		
		ArrayList<Job> jobs = new JobRepository(getSession()).list();
		ArrayList<Step> steps = new StepRepository(getSession(), jobs.get(0)).list();
		for(Step step: steps){
			System.out.println(String.format("%s %s %s %s", step.getId(), step.getDescription(), step.getStatus().getState(), step.getStartTime().toString()));
		}
		Assert.assertTrue(jobs.size() >= 1);
	}

}
