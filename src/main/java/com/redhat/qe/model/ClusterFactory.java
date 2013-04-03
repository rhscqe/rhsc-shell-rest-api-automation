package com.redhat.qe.model;

public class ClusterFactory {

	public static Cluster cluster(String name) {
		return cluster(name, null);
	}

	public static Cluster cluster(String name, Datacenter datacenter) {
		Cluster result = new Cluster();
		result.setName(name);
		result.setDatacenter(datacenter);
		result.setDescription("cluster created by automation");

		Cpu cpu = new Cpu();
		cpu.setId("Intel SandyBridge Family");
		result.setCpu(cpu);

		Version version = new Version();
		version.setMajor(3);
		version.setMinor(2);
		result.setVersion(version);

		return result;
	}

}
