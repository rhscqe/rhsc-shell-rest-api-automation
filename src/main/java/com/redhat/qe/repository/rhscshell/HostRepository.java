package com.redhat.qe.repository.rhscshell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.redhat.qe.helpers.utils.StringUtils;
import com.redhat.qe.model.Host;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.IHostRepository;
import com.redhat.qe.repository.rest.IHostRepositoryExtended;
import com.redhat.qe.ssh.IResponse;

public class HostRepository extends Repository<Host> implements IHostRepository, IHostRepositoryExtended{

	public HostRepository(RhscShellSession shell) {
		super(shell);
	}
	
	public Host create(Host host){
		return Host.fromResponse(getShell().sendAndRead(createCommand(host)).unexpect("error:"));
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
	
	private IResponse _show(String nameOrId){
		return getShell().sendAndRead(String.format("show host \"%s\"", nameOrId));
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
	
	public IResponse deactivate(Host host){
		return deactivate(host.getId());
	}
	
	public IResponse activate(Host host){
		return activate(host.getId());
	}
	
	public IResponse deactivate(String nameOrId){
		return _deactivate(nameOrId).expect("status-state: complete");
	}

	/**
	 * @param nameOrId
	 * @return
	 */
	private IResponse _deactivate(String nameOrId) {
		return getShell().sendAndRead(String.format("action host %s deactivate", nameOrId));
	}
	
	public IResponse _deactivate(Host host) {
		return _deactivate(host.getId());
	}
	
	public IResponse activate(String nameOrId){
		return getShell().sendAndRead(String.format("action host %s activate", nameOrId)).expect("status-state: complete");
	}

	public IResponse destroy(Host host) {
		return destroy(host,null);
	}
	
	public IResponse destroy(Host host, String options) {
		return _destroy(host,options).expect("complete");
	}

	/**
	 * @param command
	 * @return
	 */
	public IResponse _destroy(Host host, String options) {
		StringBuilder command = destroyCommand(host, options);
		return getShell().sendAndRead(command.toString());
	}
	
	public IResponse _destroy(Host host){
		return _destroy(host,null);
	}

	/**
	 * @param host
	 * @param options
	 * @return
	 */
	private StringBuilder destroyCommand(Host host, String options) {
		StringBuilder command = new StringBuilder();
		command.append(String.format("remove host %s",host.getId()));
		if(options != null) command.append(" " + options);
		return command;
	}
	
	public List<Host> list(String options){
		Collection<String> properties = StringUtils.getPropertyKeyValueSets(_list(options).toString());
		ArrayList<Host> hosts = new ArrayList<Host>();
		for(String property :properties){
			hosts.add(Host.fromResponse(property));
		}
		return hosts;
	}
	
	public IResponse _list(String options){
		return (options == null) ? getShell().sendAndRead("list hosts") : getShell().sendAndRead("list hosts" + " " + options);
	}
	

	public Host update(Host entity) {
		_update(entity);
		return show(entity);
	}

	/**
	 * @param entity
	 * @return
	 */
	private IResponse _update(Host entity) {
		StringBuilder command = new StringBuilder();
		command.append(String.format("update host %s", entity.getIdOrName()));
		if(entity.getName() != null) command.append("--name " + entity.getName());
		return getShell().sendAndRead(command.toString()).unexpect("error");
	}

	public List<Host> listAll() {
		return list("--show-all");
	}

	public List<Host> list() {
		return listAll();
	}


}
