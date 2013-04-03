package com.redhat.qe.model;

import java.util.HashMap;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.ssh.Response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.redhat.qe.ssh.Response;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="host")
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
	
	@XmlAttribute
	private String id;
	private String address;
	
	@XmlElement(name="root_password")
	private String rootPassword;
	private Cluster cluster;
	private Status status;
	
	@XmlElement(name="reboot_after_installation")
	private boolean reboot = false;
	
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
		if(getStatus() == null){
			status = new Status();
		}
		return getStatus().getState();
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		if(getStatus() == null){
			status = new Status();
		}
		getStatus().setState(state);
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	
	public String getIdOrName(){
		return (getId() == null) ? getName() : getId();
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
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @return the reboot
	 */
	public boolean isReboot() {
		return reboot;
	}
	/**
	 * @param reboot the reboot to set
	 */
	public void setReboot(boolean reboot) {
		this.reboot = reboot;
	}
	
	public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(name).
            toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;

        Host rhs = (Host) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(name, rhs.name).
            isEquals();
    }
	
	
	
	
}
