package com.redhat.qe.factories;

import com.redhat.qe.model.Cluster;

public class ClusterFactory {
	
	public static Cluster cluster(String name){
		return cluster(name, null);
	}
	
	public static Cluster cluster(String name, String description){
		Cluster result = new Cluster();
		result.setName(name);
		result.setDescription(description);
		result.setMajorVersion("3");
		result.setMinorVersion("2");
		return result;
	}

}
