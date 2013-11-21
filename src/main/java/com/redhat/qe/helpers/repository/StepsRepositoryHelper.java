package com.redhat.qe.helpers.repository;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
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
	
	public ArrayList<Step> getChildren(StepRepository repository, final Step step) {
		return new ArrayList<Step>(Collections2.filter(repository.list(), new Predicate<Step>() {
			public boolean apply(Step currentstep) {
				if(currentstep.getParentStep() != null)
					return currentstep.getParentStep().getId().equals(step.getId());
				else
					return false;
			}
		}));
	}

}
