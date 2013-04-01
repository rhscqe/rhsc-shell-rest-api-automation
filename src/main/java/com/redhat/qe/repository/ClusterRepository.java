package com.redhat.qe.repository;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.ssh.Response;

public class ClusterRepository extends Repository {
	

	
	public ClusterRepository(RhscShell shell) {
		super(shell);
	}

	public Response create(Cluster cluster){
		return getShell().send(createCommand(cluster));
	}
	
	public Response createOrShow(Cluster cluster){
		if(Cluster.fromResponse(show(cluster)).getId() != null)
			return show(cluster);
		else
			return create(cluster);
	}
	
	public String createCommand(Cluster cluster){
		return String.format("add cluster --name '%s' --cpu-id 'Intel SandyBridge Family' --gluster_service True --virt_service False --datacenter-name Default",cluster.getName());
	}
	
	public Response show(Cluster cluster){
		return show(cluster.getName());
	}
	
	public Response show(String nameOrId){
		return getShell().send(String.format("show cluster %s",nameOrId));
	}
	
	public Response destroy(Cluster cluster){
		return destroy(cluster.getId());
	}
	
	public Response destroy(String nameOrId){
		return getShell().send(String.format("remove cluster %s", nameOrId)).expect("accepted.");
	}
}
