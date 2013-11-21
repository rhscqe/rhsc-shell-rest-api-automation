package com.redhat.qe.model.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.redhat.qe.model.Host;
import com.redhat.qe.repository.rest.Marshallable;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="host")
public class HookResolutionActionHost implements Marshallable {
	
	
	private String id;
	private String name;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void populateFromHost(Host host){
		this.id =host.getId();
		this.name = host.getName();
	}
	
	



}
