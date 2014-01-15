package com.redhat.qe.helpers.repository;

import java.util.ArrayList;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.model.WaitUtil.WaitResult;
import com.redhat.qe.repository.rest.StepRepository;

import dstywho.timeout.Timeout;

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
	
	public WaitResult waitUntilStepStatus(final StepRepository repo,  final Step step, final String status){
		return WaitUtil.waitUntil(new dstywho.functional.Predicate() {
			
			@Override
			public Boolean act() {
				Timeout.TIMEOUT_FIVE_SECONDS.sleep();
				return repo.show(step).getStatus().getState().equalsIgnoreCase(status);
			}
		}, 60);
		
	}
	public WaitResult waitUntilStepStatusMatches(final StepRepository repo,  final Step step, final String regex){
		return WaitUtil.waitUntil(new dstywho.functional.Predicate() {
			
			@Override
			public Boolean act() {
				Timeout.TIMEOUT_FIVE_SECONDS.sleep();
				Step steprefreshed = repo.show(step);
				System.out.println((regex));
				System.out.println(steprefreshed.getStatus().getState().matches(regex));
				return steprefreshed.getStatus().getState().matches(regex);
			}
		}, 60);
		
	}

}
