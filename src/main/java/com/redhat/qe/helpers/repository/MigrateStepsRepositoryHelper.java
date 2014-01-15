package com.redhat.qe.helpers.repository;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.WaitUtil.WaitResult;
import com.redhat.qe.repository.rest.StepRepository;

public class MigrateStepsRepositoryHelper extends StepsRepositoryHelper{

	private Step removeBrickStep(HttpSession session, Job migrateJob) {
		Step executingStep = new StepsRepositoryHelper().getExecutingStep(new StepRepository(session, migrateJob));
		Step removeBrickStep = new StepsRepositoryHelper().getChildren(new StepRepository(session, migrateJob), executingStep).get(0);
		return removeBrickStep;
	}
	
	public WaitResult waitForMigrateToFinish(HttpSession session, Job migrateJob) {
		Step removeBrickStep = removeBrickStep(session, migrateJob);
		return new StepsRepositoryHelper().waitUntilStepStatus(new StepRepository(session, migrateJob), removeBrickStep, "finished");
	}
	public WaitResult waitForMigrateToMatch(HttpSession session, Job migrateJob, String regex) {
		Step removeBrickStep = removeBrickStep(session, migrateJob);
		return new StepsRepositoryHelper().waitUntilStepStatusMatches(new StepRepository(session, migrateJob), removeBrickStep, regex);
	}

}
