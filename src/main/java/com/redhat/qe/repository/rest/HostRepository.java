package com.redhat.qe.repository.rest;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.DELETE;

import org.apache.http.client.methods.HttpGet;
import org.calgb.test.performance.HttpSession;

import com.redhat.qe.annoations.Alias;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.HostList;
import com.redhat.qe.repository.IHostRepository;
import com.redhat.qe.ssh.IResponse;

public class HostRepository extends SimpleRestRepository<Host> implements IHostRepository {

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

	public ResponseWrapper _deactivate(Host host){
		return _customAction(host, getCollectionPath(), "deactivate");
	}
	
	public ResponseWrapper _search(Host host){
		return sendTransaction(new HttpGet(getCollectionPath() + "?name=" + URLEncoder.encode(host.getName()) ));
	}
	
	@Override
	public ArrayList<Host> deserializeCollectionXmlToList(String raw) {
		ArrayList<Host> result = ((HostList) unmarshal(raw)).getHosts();
		return (result == null) ? new ArrayList<Host>() : result;
	}

	@Override
	public String getCollectionPath() {
		return API_HOSTS;
	}
	

}
