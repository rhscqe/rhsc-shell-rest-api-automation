package com.redhat.qe.model;
import java.util.HashMap;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.ssh.Response;


public class Cluster extends Model{
	
	
	private String id;
	private String name;
	private String description;
	public static Cluster fromResponse(Response response){
		return fromKeyValue(response.toString());
	}

	public static Cluster fromKeyValue(String keyValue){
		HashMap<String, String> attributes = StringUtils.keyAttributeToHash(keyValue);
		Cluster cluster = new Cluster();
		cluster.setId(attributes.get("id"));
		cluster.setName(attributes.get("name"));
		cluster.setDescription(attributes.get("description"));
		return cluster;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object o){
		return (o instanceof Cluster) 
				&& (getId()==null || ((Cluster)o).getId().equals(getId()))
				&& (getName() == null || ((Cluster)o).getName().equals(getName()))
				&& (getDescription() == null || ((Cluster)o).getDescription().equals(getDescription()));
	}
	
	


}
