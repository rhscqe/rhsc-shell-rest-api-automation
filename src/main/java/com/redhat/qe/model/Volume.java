package com.redhat.qe.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.ssh.Response;

public class Volume extends Model{
	private String id;
	private Cluster cluster;
	private String name;
	private String type;
	private String status;
	private int stripe_count =0 ;
	private int replica_count =0;
	private List<Brick> bricks = new ArrayList<Brick>();
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the bricks
	 */
	public List<Brick> getBricks() {
		return bricks;
	}
	/**
	 * @param bricks the bricks to set
	 */
	public void setBricks(List<Brick> bricks) {
		this.bricks = bricks;
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
	
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the stripe_count
	 */
	public int getStripe_count() {
		return stripe_count;
	}
	/**
	 * @param stripe_count the stripe_count to set
	 */
	public void setStripe_count(int stripe_count) {
		this.stripe_count = stripe_count;
	}
	/**
	 * @return the replica_count
	 */
	public int getReplica_count() {
		return replica_count;
	}
	/**
	 * @param replica_count the replica_count to set
	 */
	public void setReplicaCount(int replica_count) {
		this.replica_count = replica_count;
	}
	public static Volume fromResponse(Response response) {
		HashMap<String, String> attr = StringUtils.keyAttributeToHash(response.toString());
		Volume volume = new Volume();
		volume.setId(attr.get("id"));
		volume.setName(attr.get("name"));
		volume.setType(attr.get("volume_type"));
		volume.setStatus(attr.get("status-state"));
		volume.setReplicaCount(Integer.parseInt(attr.get("replica_count")));
		volume.setStripe_count((Integer.parseInt(attr.get("stripe_count"))));
		
		Cluster rcluster = new Cluster();
		rcluster.setId(attr.get("cluster-id"));
		volume.setCluster(rcluster);
		return volume;
	}
	
	

}
