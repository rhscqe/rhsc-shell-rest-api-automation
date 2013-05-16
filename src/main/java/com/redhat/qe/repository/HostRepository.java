package com.redhat.qe.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Host;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.ssh.Response;

public class HostRepository extends Repository<Host> {

	public HostRepository(RhscShellSession shell) {
		super(shell);
	}
	
	public Host create(Host host){
		return Host.fromResponse(getShell().send(createCommand(host)).unexpect("error:"));
	}
	
	public Host show(Host host){
		return show(host.getName());
	}
	
	public Host show(String nameOrId){
		return Host.fromResponse(_show(nameOrId).unexpect("error:"));
	}
	
	private Host _show(Host host){
		return Host.fromResponse(_show(host.getName()));
	}
	
	private Response _show(String nameOrId){
		return getShell().send(String.format("show host %s", nameOrId));
	}
	
	public Host createOrShow(Host host){
		if(isExist(host)){
			return show(host);
		}else{
			return create(host);
		}
	}

	public boolean isExist(Host host) {
		return _show(host).getId() != null;
	}
	
	private String createCommand(Host host) {
		return String.format("add host --name %s --address '%s' --root_password %s --cluster-name %s --reboot_after_installation false", host.getName(), host.getAddress(), host.getRootPassword(), host.getCluster().getName());
	}
	
	public Response deactivate(Host host){
		return deactivate(host.getId());
	}
	
	public Response deactivate(String nameOrId){
		return getShell().send(String.format("action host %s deactivate", nameOrId)).expect("status-state: complete");
	}

	public Response destroy(Host host) {
		return getShell().send(String.format("remove host %s",host.getId())).expect("complete");
	}
	
	public List<Host> list(String options){
		Collection<String> properties = StringUtils.getPropertyKeyValueSets(_list(options).toString());
		ArrayList<Host> hosts = new ArrayList<Host>();
		for(String property :properties){
			hosts.add(Host.fromResponse(property));
		}
		return hosts;
	}
	
	public Response _list(String options){
		return (options == null) ? getShell().send("list hosts") : getShell().send("list hosts" + " " + options);
	}

	public Host update(Host entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
