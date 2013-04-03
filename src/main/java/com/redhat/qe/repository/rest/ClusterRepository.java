package com.redhat.qe.repository.rest;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.ClusterList;

public class ClusterRepository extends SimpleRestRepository<Cluster>{

	private static final String API_CLUSTERS = "/api/clusters";

	public ClusterRepository(HttpSession session) {
		super(session);
	}

	@Override
	public ArrayList<Cluster> deserializeCollectionXmlToList(String raw) {
		ClusterList clusterList = (ClusterList) unmarshal(raw);
		return clusterList.getClusters();
	}

	@Override
	public String getCollectionPath() {
		return API_CLUSTERS;
	}

}
