package com.redhat.qe.repository.rest.clibrokers;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.IVolumeRepositoryExtended;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.IResponse;

public class VolumeRepositoryBroker implements IVolumeRepositoryExtended{
	
	private HttpSession session;

	public VolumeRepositoryBroker(HttpSession session){
		this.session = session;
	}
	
	public ArrayList<com.redhat.qe.model.Volume> list(Cluster cluster) {
		return getVolumeRepository(cluster).list();
	}

	private VolumeRepository getVolumeRepository(Cluster cluster) {
		return new VolumeRepository(session, cluster);
	}

	public IResponse _stop(Volume volume) {
		return getVolumeRepository(volume.getCluster())._stop(volume);
	}

	public IResponse destroy(Volume volume) {
		return getVolumeRepository(volume.getCluster()).delete(volume);
	}

	public Volume create(Volume volume) {
		return getVolumeRepository(volume.getCluster()).create(volume);
	}

}
