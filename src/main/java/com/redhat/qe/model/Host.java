package com.redhat.qe.model;

import java.util.HashMap;

import com.redhat.qe.ssh.Response;

public class Host {
	
	public static Host fromResponse(Response response){
		HashMap<String, String> attributes = StringUtils.keyAttributeToHash(response.toString());
		Host result = new Host();
		result.setAddress(attributes.get("address"));
		result.setId(attributes.get("id"));
		result.setName(attributes.get("name"));
		result.setState(attributes.get("status-state"));
		return result;
	}
	private String name;
	private String id;
	private String address;
	private String rootPassword;
	private Cluster cluster;
	private String state;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the cluster
	 */
	public Cluster getCluster() {
		return cluster;
	}
	/**
	 * @param cluster the cluster to set
	 */
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	/**
	 * @return the rootPassword
	 */
	public String getRootPassword() {
		return rootPassword;
	}
	/**
	 * @param rootPassword the rootPassword to set
	 */
	public void setRootPassword(String rootPassword) {
		this.rootPassword = rootPassword;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
