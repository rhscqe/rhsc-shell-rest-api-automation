package com.redhat.qe.repository;

import com.redhat.qe.model.Host;
import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.ssh.Response;

public class HostRepository extends Repository {

	public HostRepository(RhscShell shell) {
		super(shell);
	}
	
	public Host create(Host host){
		return Host.fromResponse(getShell().send(createCommand(host)).unexpect("error:"));
	}
	
	public Host show(Host host){
		return show(host.getName());
	}
	
	public Host show(String nameOrId){
		return Host.fromResponse(getShell().send(String.format("show host %s", nameOrId)));
	}
	
	public Host createOrShow(Host host){
		if(isExist(host)){
			return show(host);
		}else{
			return create(host);
		}
	}

	private boolean isExist(Host host) {
		return show(host).getId() != null;
	}
	
	private String createCommand(Host host) {
		return String.format("add host --name %s --address '%s' --root_password %s --cluster-name %s", host.getName(), host.getAddress(), host.getRootPassword(), host.getCluster().getName());
	}
	
	public Response deactivate(Host host){
		return deactivate(host.getId());
	}
	
	public Response deactivate(String nameOrId){
		return getShell().send(String.format("action host %s deactivate", nameOrId)).expect("status-state: complete");
	}

	public Response destroy(Host host) {
		return getShell().send(String.format("remove host %s",host.getId())).expect("accepted");
	}

}
