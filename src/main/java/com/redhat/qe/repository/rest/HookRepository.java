package com.redhat.qe.repository.rest;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.GlusterHookList;
import com.redhat.qe.model.Hook;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.HostList;

public class HookRepository extends SimpleRestRepository<Hook>{
	
	private Cluster cluster;

	public HookRepository(HttpSession session, Cluster cluster){
		super(session);
		this.cluster = cluster;
	}

	@Override
	public String getCollectionPath() {
		return String.format("/api/clusters/%s/glusterhooks", cluster.getId() );
		
	}

	@Override
	protected ArrayList<Hook> deserializeCollectionXmlToList(String raw) {
		ArrayList<Hook> result = ((GlusterHookList) unmarshal(raw)).getHooks();
		return (result == null) ? new ArrayList<Hook>() : result; 
	}
	

}
