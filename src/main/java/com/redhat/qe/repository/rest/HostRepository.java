package com.redhat.qe.repository.rest;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Host;
import com.redhat.qe.model.HostList;
import com.redhat.qe.repository.IHostRepository;

public class HostRepository extends SimpleRestRepository<Host> implements IHostRepository{

	private static final String API_HOSTS = "/api/hosts";

	public HostRepository(HttpSession session) {
		super(session);
	}
	
	public ResponseWrapper activate(Host host){
		return customAction(host, getCollectionPath(), "activate");
	}

	public ResponseWrapper deactivate(Host host){
		return customAction(host, getCollectionPath(), "deactivate");
	}

	
	@Override
	public ArrayList<Host> deserializeCollectionXmlToList(String raw) {
		return  ((HostList) unmarshal(raw)).getHosts();
	}

	@Override
	public String getCollectionPath() {
		return API_HOSTS;
	}
	
}
