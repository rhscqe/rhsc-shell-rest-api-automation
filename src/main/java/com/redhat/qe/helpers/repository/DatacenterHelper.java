package com.redhat.qe.helpers.repository;

import com.redhat.qe.model.Datacenter;
import com.redhat.qe.repository.rest.DatacenterRepository;

public class DatacenterHelper {
	public Datacenter getDefault(DatacenterRepository repo){
			return repo.list().get(0);
	}

}
