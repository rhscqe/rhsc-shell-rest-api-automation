package com.redhat.qe.helpers.repository;

import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.WaitUtil.WaitResult;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.rest.StepRepository;

public class RebalanceStatusWaiter {
	
	private JobRepository jobRepository;

	public RebalanceStatusWaiter(JobRepository jobRepository){
		this.jobRepository = jobRepository;
	}
	
	public WaitResult waitforJobFinish(Job job){
		return new JobRepoHelper().waitUntilJobFinished(jobRepository, job);
	}
	
	public Step getRebalanceStep(Job job){
		return new StepsRepositoryHelper().getRebalanceStep(
				getStepRepository(job));
	}

	private StepRepository getStepRepository(Job job) {
		return new StepRepository(jobRepository.getSession(), job);
	}		
	
	public WaitResult waitForRebalanceStepToFinish(Job job){
		return new StepsRepositoryHelper().waitUntilStepStatus(getStepRepository(job), getRebalanceStep(job), "FINISHED");
	}
	
	

}
