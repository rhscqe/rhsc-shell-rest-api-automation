package com.redhat.qe.factories;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;

public class HostFactory {
	public static Host create(String name, String address, String rootPassword,Cluster cluster){
		Host result = new Host();
		result.setName(name);
		result.setAddress(address);
		result.setRootPassword(rootPassword);
		result.setCluster(cluster);
		return result;
	}

}
