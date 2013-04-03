package com.redhat.qe.rest;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.VolumeList;
import com.redhat.qe.repository.rest.SimpleRestRepository;

public class VolumeRepository extends SimpleRestRepository<Volume>{

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
		return ((VolumeList)unmarshal(raw)).getVolumes();
	}

}
