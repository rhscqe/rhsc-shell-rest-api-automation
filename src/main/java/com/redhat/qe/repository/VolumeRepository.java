package com.redhat.qe.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.ssh.Response;

public class VolumeRepository extends Repository<Volume>{
	
	public VolumeRepository(RhscShellSession shell) {
		super(shell);
	}

	public Volume create(Volume volume){
		String command = String.format("add glustervolume --cluster-identifier %s --name %s --volume_type %s --stripe_count %s --replica_count %s %s"
				, volume.getCluster().getId(), volume.getName(), volume.getType(), volume.getStripe_count(), volume.getReplica_count(), bricksOptionsOnCreate(volume.getBricks()));
		return Volume.fromResponse(getShell().send(command).unexpect("error:"));
	}
	
	public Volume show(Volume volume){
		return Volume.fromResponse(getShell().send(String.format("show glustervolume %s --cluster-identifier %s", volume.getId(), volume.getCluster().getId())).unexpect("error"));
	}
	
	
	public Volume showOrCreate(Volume volume){
		//TODO
		return null;
	}
	
	private String bricksOptionsOnCreate(List<Brick> bricks){
		StringBuffer result = new StringBuffer(); 
		for(Brick brick : bricks){
			result.append(String.format("--bricks-brick '%s'",brick.toString()));
		}
		return result.toString();
	}

	public Response destroy(Volume volume) {
		return getShell().send(String.format("remove glustervolume %s --cluster-identifier %s", volume.getId(),volume.getCluster().getId())).expect("complete");	
	}

	public Volume update(Volume entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Response start(Volume entity){
		return getShell().send(String.format("action glustervolume %s start --cluster-identifier %s", entity.getId(), entity.getCluster().getId()))		
				.expect("complete");
	}
	
	public Response setOption(Volume entity, GlusterOptionValue optionValue ){
		return getShell().send(String.format("action glustervolume %s setoption --cluster-identifier %s %s", entity.getId(), entity.getCluster().getId(), optionValue))		
				.expect("complete");
	}
	
	public Response stop(Volume entity){
		return getShell().send(String.format("action glustervolume %s stop --cluster-identifier %s", entity.getId(), entity.getCluster().getId()))		
				.expect("complete");
	}

	public boolean isExist(Volume entity) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Response _list(Cluster cluster,String options){
		String formatedOptions = (options == null) ? "" : options;
		String cmd = String.format("list glustervolumes --cluster-identifier %s %s", cluster.getId(), formatedOptions);
		return getShell().send(cmd);
	}
	
	public ArrayList<Volume> list(Cluster cluster){
		return list(cluster, null);
	}
	
	public ArrayList<Volume> list(Cluster cluster, String options){
		Response response = _list(cluster, options).unexpect("error");
		Collection<String> volumesProperties = StringUtils.getPropertyKeyValueSets(response.toString());
		ArrayList<Volume> result = new ArrayList<Volume>();
		for(String volumeProperties : volumesProperties){
			result.add(Volume.fromResponse(volumeProperties));
		}
		return result;
	}

}
 