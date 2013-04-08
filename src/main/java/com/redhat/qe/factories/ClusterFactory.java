package com.redhat.qe.factories;

import com.redhat.qe.model.Cluster;

public class ClusterFactory {
	
	public static Cluster cluster(String name){
		Cluster result = new Cluster();
		result.setName(name);
		return result;
	}

}
