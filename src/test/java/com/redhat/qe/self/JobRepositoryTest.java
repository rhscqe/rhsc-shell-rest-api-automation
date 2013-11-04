package com.redhat.qe.self;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.model.Job;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.rest.TwoHostClusterTestBase;

public class JobRepositoryTest extends TwoHostClusterTestBase{
	
	@Test
	public void testList(){
		getHostRepository().deactivate(getHost1());
		getHostRepository().activate(getHost1());
		
		ArrayList<Job> jobs = new JobRepository(getSession()).list();
		for(Job job: jobs){
			System.out.println(String.format("%s %s %s", job.getId(), job.getDescription(), job.getStatus().getState()));
		}
		Assert.assertTrue(jobs.size() >= 1);
		
	}

}
