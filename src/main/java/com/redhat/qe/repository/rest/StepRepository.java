package com.redhat.qe.repository.rest;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.StepList;

public class StepRepository extends SimpleRestRepository<Step> {

	private Job job;

	public StepRepository(HttpSession session, Job job) {
		super(session);
		this.job = job;
	}

	@Override
	public String getCollectionPath() {
		return String.format("/api/jobs/%s/steps", job.getId());

	}

	@Override
	protected ArrayList<Step> deserializeCollectionXmlToList(String raw) {
			ArrayList<Step> result = ((StepList) unmarshal(raw)).getSteps();
			return (result == null) ? new ArrayList<Step>() : result;
	}

}
