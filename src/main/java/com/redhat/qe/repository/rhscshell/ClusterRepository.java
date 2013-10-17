package com.redhat.qe.repository.rhscshell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.IClusterRepository;
import com.redhat.qe.ssh.IResponse;

public class ClusterRepository extends Repository<Cluster>implements IClusterRepository {
	

	
	public ClusterRepository(RhscShellSession shell) {
		super(shell);
	}

	public Cluster create(Cluster cluster){
		return Cluster.fromResponse(getShell().send(createCommand(cluster)).unexpect("error:"));
	}
	
	public Cluster createOrShow(Cluster cluster){
		Cluster result;
		if(isExist(cluster))
			result = show(cluster);
		else
			result = create(cluster);
		return result;
	}

	public boolean isExist(Cluster cluster) {
		return _show(cluster).getId() != null;
	}
	
	public String createCommand(Cluster cluster){
		return String.format("add cluster --name '%s' --description '%s' --cpu-id 'Intel SandyBridge Family' --gluster_service True --virt_service False --datacenter-name Default --version-major %s --version-minor %s",
				cluster.getName(),cluster.getDescription(),cluster.getMajorVersion(), cluster.getMinorVersion());
	}
	
	public Cluster show(Cluster cluster){
		return show(cluster.getName());
	}
	
	public Cluster show(String nameOrId){
		return Cluster.fromResponse(_show(nameOrId).unexpect("error:"));
	}
	
	private Cluster _show(Cluster cluster){
		return Cluster.fromResponse(_show(cluster.getName()));
	}
	
	private IResponse _show(String nameorId){
		return getShell().send(String.format("show cluster %s",nameorId));
	}
	
	public IResponse destroy(Cluster cluster){
		return destroy(cluster.getId());
	}
	
	public IResponse _destroy(Cluster cluster){
		return _destroy(cluster.getId());
	}
	
	public IResponse destroy(String nameOrId){
		return _destroy(nameOrId).expect("complete");
	}
	
	public IResponse _destroy(String nameOrId){
		 return getShell().send(String.format("remove cluster %s", nameOrId));
	}

	public Cluster update(Cluster entity) {
		return Cluster.fromResponse(_update(entity).unexpect("error:"));
	}
	
	private IResponse _update( Cluster entity) {
		return getShell().send(String.format("update cluster %s --name %s", entity.getId(), entity.getName()));
	}
	
	public List<Cluster> list(String options){
		Collection<String> clusterDefinitions = StringUtils.getPropertyKeyValueSets(_list(options).toString());
		List<Cluster> clusters = new ArrayList<Cluster>();
		for(String clusterDefinition :clusterDefinitions){
			clusters.add(Cluster.fromKeyValue(clusterDefinition));
		}
		return clusters;
	}
	
	public List<Cluster> list(){
		return list(null);
	}
	
	public IResponse _list(String options){
		String command = "list clusters";
		return ( options == null) ? getShell().send(command) :	getShell().send(command +" " + options);
	}

	
	
}
