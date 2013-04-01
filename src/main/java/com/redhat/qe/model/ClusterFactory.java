package com.redhat.qe.model;

public class ClusterFactory {
	
	public static Cluster cluster(String name){
		Cluster result = new Cluster();
		result.setName(name);
		return result;
	}

}
