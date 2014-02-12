package com.redhat.qe.repository.rest;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Host;
import com.redhat.qe.model.Nic;
import com.redhat.qe.model.NicList;

public class NicRepository extends SimpleRestRepository<Nic>{

	private Host host;

	public NicRepository(HttpSession session, Host host){
		super(session);
		this.host = host;
	}

	@Override
	public String getCollectionPath() {
		return String.format("/api/hosts/%s/nics", host.getId());
	}

	@Override
	protected ArrayList<Nic> deserializeCollectionXmlToList(String raw) {
		return (( NicList)unmarshal(raw)).getnics();
	}

}
