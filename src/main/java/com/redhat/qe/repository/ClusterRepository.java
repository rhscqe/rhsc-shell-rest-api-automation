package com.redhat.qe.repository;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.ssh.Response;

public class ClusterRepository {
	
	private RhscShell shell;

	public ClusterRepository(RhscShell shell){
		this.shell = shell;
		
	}
	
	public Response create(Cluster cluster){
		return shell.send(createCommand(cluster));
	}
	
	public String createCommand(Cluster cluster){
		return String.format("add cluster --name '%s' --cpu-id 'Intel SandyBridge Family' --gluster_service True --virt_service False --datacenter-name Default",cluster.getName());
	}
}
