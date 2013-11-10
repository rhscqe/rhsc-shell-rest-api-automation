package com.redhat.qe.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="gluster_hook")
public class Hook extends Model {
	
	private String name;
	@XmlAttribute
	private String id;
	private Cluster cluster;
	@XmlElement(name="gluster_command")
	private String glusterCommand;
	private String stage;
	private String contentType;
	private String checksum;
	private String content;
	private Status status;
	private String conflictStatus;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Cluster getCluster() {
		return cluster;
	}
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	public String getGlusterCommand() {
		return glusterCommand;
	}
	public void setGlusterCommand(String glusterCommand) {
		this.glusterCommand = glusterCommand;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getConflictStatus() {
		return conflictStatus;
	}
	public void setConflictStatus(String conflictStatus) {
		this.conflictStatus = conflictStatus;
	} 

	
	
}
