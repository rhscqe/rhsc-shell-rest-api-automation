package com.redhat.qe.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.redhat.qe.helpers.jaxb.BrickHostToServerIdXmlAdapter;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="action")
public class Action {
	private Status status;
	private Fault fault;
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Fault getFault() {
		return fault;
	}
	public void setFault(Fault fault) {
		this.fault = fault;
	}
	
	
}
