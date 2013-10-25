package com.redhat.qe.repository.rest;

import java.util.ArrayList;
import java.util.List;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.VolumeList;
import com.redhat.qe.repository.IVolumeRepository;
import com.redhat.qe.ssh.IResponse;

public class VolumeRepository extends SimpleRestRepository<Volume> implements IVolumeRepository{

	private Cluster cluster;

	public VolumeRepository(HttpSession session, Cluster cluster) {
		super(session);
		this.cluster = cluster;
	}

	@Override
	public String getCollectionPath() {
		return String.format("/api/clusters/%s/glustervolumes", cluster.getId());
	}

	@Override
	protected ArrayList<Volume> deserializeCollectionXmlToList(String raw) {
		ArrayList<Volume> result = ((VolumeList)unmarshal(raw)).getVolumes();
		return ( result == null ) ? new ArrayList<Volume>() : result;
	}
	
	public ResponseWrapper start(Volume volume){
		return customAction(volume, getCollectionPath(), "start");
	}

	public ResponseWrapper stop(Volume volume){
		return customAction(volume, getCollectionPath(), "stop");
	}

	public ResponseWrapper _stop(Volume volume){
		return _customAction(volume, getCollectionPath(), "stop");
	}
	public ResponseWrapper _start(Volume volume){
		return _customAction(volume, getCollectionPath(), "start");
	}




}
