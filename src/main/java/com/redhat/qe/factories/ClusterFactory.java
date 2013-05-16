package com.redhat.qe.factories;

import com.redhat.qe.model.Cluster;

public class ClusterFactory {
	
	public static Cluster cluster(String name){
		Cluster result = new Cluster();
		result.setName(name);
		return result;
	}
	
	public static Cluster cluster(String name, String description){
		Cluster result = new Cluster();
		result.setName(name);
		result.setDescription(description);
		return result;
	}

}
