package com.redhat.qe.model;
import java.util.HashMap;

import com.redhat.qe.repository.StringUtils;
import com.redhat.qe.ssh.Response;


public class Cluster extends Model{
	
	
	private String id;
	private String name;
	public static Cluster fromResponse(Response response){
		HashMap<String, String> attributes = StringUtils.keyAttributeToHash(response.toString());
		Cluster cluster = new Cluster();
		cluster.setId(attributes.get("id"));
		cluster.setName(attributes.get("name"));
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


}
