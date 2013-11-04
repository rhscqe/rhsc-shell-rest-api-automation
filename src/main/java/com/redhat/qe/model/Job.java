package com.redhat.qe.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="gluster_volume")
public class Job extends Model{
	@XmlAttribute
	private String id;
	private String description;
	private Status status;
	
	@XmlElement(name = "start_time")
	private String startTime;

	@XmlElement(name = "end_time")
	private String endtTime;

	@XmlElement(name = "latest_update")
	private String latestUdate;

	@Override
	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndtTime() {
		return endtTime;
	}

	public void setEndtTime(String endtTime) {
		this.endtTime = endtTime;
	}

	public String getLatestUdate() {
		return latestUdate;
	}

	public void setLatestUdate(String latestUdate) {
		this.latestUdate = latestUdate;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
