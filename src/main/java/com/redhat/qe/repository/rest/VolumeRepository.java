package com.redhat.qe.repository.rest;

import java.util.ArrayList;
import java.util.List;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Action;
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

	public ResponseWrapper _rebalance(Volume volume){
		return _customAction(volume, getCollectionPath(), "rebalance");
	}


	public Action rebalance(Volume volume){
		ResponseWrapper response = _rebalance(volume);
		response.expectCode(200);
		return (Action) unmarshal(response.getBody());
	}

	public ResponseWrapper _stopRebalance(Volume volume){
		return _customAction(volume, getCollectionPath(), "stoprebalance");
	}

	public ResponseWrapper stopRebalance(Volume volume){
		return customAction(volume, getCollectionPath(), "stoprebalance");
	}

	public ResponseWrapper resetAllOptions(Volume volume){
		ResponseWrapper response = _resetAllOptions(volume); 
		response.expectCode(200);
		return response;
	}
	public ResponseWrapper _resetAllOptions(Volume volume){
		return _customAction(volume, getCollectionPath(), "resetalloptions");
	}

	
	




}
