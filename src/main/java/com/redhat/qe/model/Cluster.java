package com.redhat.qe.model;
import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.ssh.Response;


public class Cluster {
	
	
	private RhscShell shell;
	private String name;
	


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Response create(){
		return shell.send(createCommand());
	}
	
	public String createCommand(){
		return String.format("add cluster --name '%s' --cpu-id 'Intel SandyBridge Family' --gluster_service True --virt_service False --datacenter-name Default",name);
	}

}
