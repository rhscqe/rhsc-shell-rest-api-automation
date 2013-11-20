package com.redhat.qe.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="step")
public class Step extends Model{
	
	@XmlAttribute
	private String id;
	private String type;
	private int number;
	private String description;
	private Status status;
	
	@XmlElement(name="parent_step")
	private Step parentStep;
	
	@XmlElement(name = "start_time")
	private Date startTime;

	@XmlElement(name = "end_time")
	private Date endTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getDescription() {
		return description;
	}
	/**
	 * @return the parentStep
	 */
	public Step getParentStep() {
		return parentStep;
	}
	/**
	 * @param parentStep the parentStep to set
	 */
	public void setParentStep(Step parentStep) {
		this.parentStep = parentStep;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	


}
