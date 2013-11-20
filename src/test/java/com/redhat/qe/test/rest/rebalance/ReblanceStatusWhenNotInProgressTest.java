package com.redhat.qe.test.rest.rebalance;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.model.Job;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

public class ReblanceStatusWhenNotInProgressTest extends TwoHostClusterTestBase{

	@Tcms("311403")
	@Test
	public void test(){
		ArrayList<Job> jobs = new JobRepository(getSession()).list();
		Collection<Job> reblanceJobs = Collections2.filter(jobs, new Predicate<Job>() {

			public boolean apply(Job job) {
				return job.getStartTime().after(startTime) && job.getDescription().toLowerCase().contains("rebal");
			}
		});
		Assert.assertEquals(0,  reblanceJobs.size());
	}

}
