package com.redhat.qe.helpers.repository;

import com.google.common.base.Predicate;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.model.Step;
import com.redhat.qe.repository.rest.StepRepository;

public class StepsRepositoryHelper {

	
	public Step getRebalanceStep(StepRepository repository) {
		return CollectionUtils.findFirst(repository.list(), new Predicate<Step>() {

			public boolean apply(Step step) {
				return step.getDescription().toLowerCase().contains("rebalan");
			}
		});

	}

	public Step getExecutingStep(StepRepository repository) {
		return CollectionUtils.findFirst(repository.list(), new Predicate<Step>() {
			public boolean apply(Step step) {
				return step.getType().toLowerCase().contains("execu");
			}
		});
	}

}
