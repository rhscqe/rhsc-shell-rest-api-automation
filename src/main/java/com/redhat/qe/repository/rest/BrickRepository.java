package com.redhat.qe.repository.rest;

import java.util.ArrayList;
import java.util.List;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Brick;
import com.redhat.qe.model.BrickList;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.VolumeList;

public class BrickRepository extends SimpleRestRepository<Brick>{
	
	private Volume volume;
	private Cluster cluster;

	public BrickRepository(HttpSession session, Cluster cluster, Volume volume ){
		super(session);
		this.cluster = cluster;
		this.volume = volume;
	}

	@Override
	public String getCollectionPath() {
		return String.format("/api/clusters/%s/glustervolumes/%s/bricks", cluster.getId(), volume.getId());
	}

	@Override
	protected ArrayList<Brick> deserializeCollectionXmlToList(String raw) {
		ArrayList<Brick> result = ((BrickList)unmarshal(raw)).getBricks();
		return ( result == null ) ? new ArrayList<Brick>() : result;
	}

}
