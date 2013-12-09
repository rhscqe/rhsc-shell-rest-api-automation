package com.redhat.qe.helpers.repository;

import com.redhat.qe.model.Job;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.model.WaitUtil.WaitResult;
import com.redhat.qe.repository.JobRepository;

import dstywho.timeout.Timeout;

public class JobRepoHelper {

	public WaitResult waitUntilJobFinished(final JobRepository repo, final Job job) {
		return waitUntilJob("finished", repo, job);

	}
	public WaitResult waitUntilJobComplete(final JobRepository repo, final Job job) {
		return waitUntilJob("complete", repo, job);
	}

	public WaitResult waitUntilJob(final String status, final JobRepository repo, final Job job) {
		return WaitUtil.waitUntil(new dstywho.functional.Predicate() {
			@Override
			public Boolean act() {
				Timeout.TIMEOUT_TWO_SECONDS.sleep();
				return repo.show(job).getStatus().getState()
						.equalsIgnoreCase(status);
			}
		}, 50);
	}
}
