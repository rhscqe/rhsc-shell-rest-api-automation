package com.redhat.qe.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.redhat.qe.helpers.ListUtil;
import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.helpers.StringUtils.RepeatingHashMap;
import com.redhat.qe.repository.GlusterOption;
import com.redhat.qe.repository.GlusterOptionValue;
import com.redhat.qe.ssh.Response;

public class Volume extends Model{
	private String id;
	private Cluster cluster;
	private String name;
	private String type;
	private String status;
	private int stripe_count =0 ;
	private int replica_count =0;
	private String transportType;
	private HashMap<GlusterOption, GlusterOptionValue> volumeOptions;
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
	
	
	/**
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}
	/**
	 * @param transportType the transportType to set
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	

	/**
	 * @return the volumeOptions
	 */
	public HashMap<GlusterOption, GlusterOptionValue> getVolumeOptions() {
		return volumeOptions;
	}
	/**
	 * @param myvolumeOptions the volumeOptions to set
	 */
	public void setVolumeOptions(HashMap<GlusterOption, GlusterOptionValue> myvolumeOptions) {
		this.volumeOptions = myvolumeOptions;
	}
	public static Volume fromResponse(Response response) {
		return fromResponse(response.toString());
	}
	
	public static Volume fromResponse(String response) {
		HashMap<String, String> attr = StringUtils.keyAttributeToHash(response);
		HashMap<GlusterOption, GlusterOptionValue> myvolumeOptions = parseVolumeOptions(response);
		Volume volume = fromAttrs(attr, myvolumeOptions);
		return volume;
	}
	/**
	 * @param response
	 * @return
	 */
	private static HashMap<GlusterOption, GlusterOptionValue> parseVolumeOptions(String response) {
		HashMap<GlusterOption, GlusterOptionValue> result = null;
		RepeatingHashMap<String, String> collectedAttributes= StringUtils.repeatingKeyAttributeToHash(response);
		if(collectedAttributes.keys().contains("options-option-name") && collectedAttributes.keys().contains("options-option-value")){
			HashMap<String, String> volumeOptions = ListUtil.joinHashMap(collectedAttributes.get("options-option-name"), collectedAttributes.get("options-option-value"));
			result = GlusterOptionValue.fromHashMap(volumeOptions);
		}
		return result;
		
		
	}
	/**
	 * @param attr
	 * @param myvolumeOptions 
	 * @return
	 */
	public static Volume fromAttrs(HashMap<String, String> attr, HashMap<GlusterOption, GlusterOptionValue> myvolumeOptions) {
		Volume volume = new Volume();
		setFields(attr, volume);
		
		
		if(myvolumeOptions != null){
			volume.setVolumeOptions(myvolumeOptions);
		}
		
		
		setClusterIfAvailable(attr, volume);
		return volume;
	}
	/**
	 * @param attr
	 * @param volume
	 */
	private static void setFields(HashMap<String, String> attr, Volume volume) {
		volume.setId(attr.get("id"));
		volume.setName(attr.get("name"));
		volume.setType(attr.get("volume_type"));
		volume.setStatus(attr.get("status-state"));
		String rawReplicaCount = attr.get("replica_count");
		if(rawReplicaCount != null && rawReplicaCount.matches("\\d+"))
			volume.setReplicaCount(Integer.parseInt(rawReplicaCount));
		String rawStripeCount = attr.get("stripe_count");
		if(rawStripeCount != null && rawStripeCount.matches("\\d+"))
			volume.setStripe_count((Integer.parseInt(rawStripeCount)));
		volume.setTransportType(attr.get("transport_types-transport_type"));
	}
	/**
	 * @param attr
	 * @param volume
	 */
	private static void setClusterIfAvailable(HashMap<String, String> attr, Volume volume) {
		if(attr.get("cluster-id")!= null){
			Cluster rcluster = new Cluster();
			rcluster.setId(attr.get("cluster-id"));
			volume.setCluster(rcluster);
		}
	}
	
	@Override
	public boolean equals(Object o){
		return (o instanceof Volume) 
				&& (getId()==null || ((Volume)o).getId().equals(getId()))
				&& (getName() == null || ((Volume)o).getName().equals(getName()))
				&& ( ((Volume)o).getCluster() == null 
						|| ((Volume)o).getCluster().getId() == null 
						|| getCluster() == null 
						|| getCluster().getId() == null 
						|| ((Volume)o).getCluster().getId().equals(getCluster().getId()));
	}
	
	
	

}
