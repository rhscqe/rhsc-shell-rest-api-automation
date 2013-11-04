package com.redhat.qe.repository;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Job;
import com.redhat.qe.model.JobList;
import com.redhat.qe.repository.rest.SimpleRestRepository;

public class JobRepository extends SimpleRestRepository<Job>{

	public JobRepository(HttpSession session) {
		super(session);
	}

	@Override
	public String getCollectionPath() {
		return "/api/jobs/";
	}

	@Override
	protected ArrayList<Job> deserializeCollectionXmlToList(String raw) {
		ArrayList<Job> result = ((JobList)unmarshal(raw)).getJobs();
		return ( result == null ) ? new ArrayList<Job>() : result;
	}

}
