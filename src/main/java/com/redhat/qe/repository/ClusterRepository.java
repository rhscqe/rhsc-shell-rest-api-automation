package com.redhat.qe.repository;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.ssh.Response;

public class ClusterRepository extends Repository {
	

	
	public ClusterRepository(RhscShell shell) {
		super(shell);
	}

	public Cluster create(Cluster cluster){
		return Cluster.fromResponse(getShell().send(createCommand(cluster)).unexpect("error:"));
	}
	
	public Cluster createOrShow(Cluster cluster){
		Cluster result;
		if(show(cluster).getId() != null)
			result = show(cluster);
		else
			result = create(cluster);
		return result;
	}
	
	public String createCommand(Cluster cluster){
		return String.format("add cluster --name '%s' --cpu-id 'Intel SandyBridge Family' --gluster_service True --virt_service False --datacenter-name Default",cluster.getName());
	}
	
	public Cluster show(Cluster cluster){
		return show(cluster.getName());
	}
	
	public Cluster show(String nameOrId){
		return Cluster.fromResponse(getShell().send(String.format("show cluster %s",nameOrId)));
	}
	
	public Response destroy(Cluster cluster){
		return destroy(cluster.getId());
	}
	
	public Response destroy(String nameOrId){
		return getShell().send(String.format("remove cluster %s", nameOrId)).expect("accepted.");
	}
}
