package com.redhat.qe.repository.rest;

import java.util.ArrayList;

import org.apache.http.client.methods.HttpGet;
import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Datacenter;
import com.redhat.qe.model.DatacenterList;
import com.redhat.qe.repository.DatacenterXmlParser;

public class DatacenterRepository extends Repository<Datacenter> {

	public DatacenterRepository(HttpSession session) {
		super(session);
	}
	
	public ArrayList<Datacenter> list() {
		ResponseWrapper response = sendTransaction(new HttpGet("/api/datacenters")); 
		response.expectCode(200);
		return new DatacenterXmlParser().parseDatacenters(response.getBody());
	}

	@Override
	protected ArrayList<Datacenter> deserializeCollectionXmlToList(String raw) {
		return null;
	}


}
