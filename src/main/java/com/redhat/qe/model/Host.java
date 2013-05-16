package com.redhat.qe.model;

import java.util.HashMap;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.ssh.Response;

public class Host extends Model{
	
	public static Host fromResponse(String raw){
		HashMap<String, String> attributes = StringUtils.keyAttributeToHash(raw);
		Host result = new Host();
		result.setAddress(attributes.get("address"));
		result.setId(attributes.get("id"));
		result.setName(attributes.get("name"));
		result.setState(attributes.get("status-state"));
		
		Cluster resultCluster = new Cluster();
		resultCluster.setId(attributes.get("cluster-id"));
		result.setCluster(resultCluster);
		
		return result;
	}
	
	public static Host fromResponse(Response response){
		return fromResponse(response.toString());
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
	
	@Override
	public boolean equals(Object o){
		return (o instanceof Host) 
				&& (getId()==null || ((Host)o).getId().equals(getId()))
				&& (getName() == null || ((Host)o).getName().equals(getName()));
	}
	
	
	
}
