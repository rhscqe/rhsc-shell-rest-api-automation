package com.redhat.qe.repository;

import java.util.List;

import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.ssh.Response;

public class VolumeRepository extends Repository{
	
	public VolumeRepository(RhscShell shell) {
		super(shell);
	}

	public Volume create(Volume volume){
		String command = String.format("add glustervolume --cluster-identifier %s --name %s --volume_type %s --stripe_count %s --replica_count %s %s"
				, volume.getCluster().getId(), volume.getName(), volume.getType(), volume.getStripe_count(), volume.getReplica_count(), bricksOptionsOnCreate(volume.getBricks()));
		return Volume.fromResponse(getShell().send(command).unexpect("error:"));
	}
	
	public Volume show(Volume volume){
		return Volume.fromResponse(getShell().send(String.format("show volume %s", volume.getName())));
	}
	
	private String bricksOptionsOnCreate(List<Brick> bricks){
		StringBuffer result = new StringBuffer(); 
		for(Brick brick : bricks){
			result.append(String.format("--bricks-brick '%s'",brick.toString()));
		}
		return result.toString();
	}

	public Response destroy(Volume volume) {
		return getShell().send(String.format("remove glustervolume %s --cluster-identifier %s", volume.getId(),volume.getCluster().getId())).expect("accepted");	
	}

}
