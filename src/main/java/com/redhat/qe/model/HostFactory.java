package com.redhat.qe.model;

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
