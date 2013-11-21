package com.redhat.qe.model.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.redhat.qe.repository.rest.Marshallable;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="action")
public class HookResolutionAction implements Marshallable {
	
	@XmlElement(name="resolution_type")
	private String resolutionType;
	
	@XmlElement(name="host.id")
	public String hostId;
	
	private HookResolutionActionHost host;

	public String getResolutionType() {
		return resolutionType;
	}

	public void setResolutionType(String resolutionType) {
		this.resolutionType = resolutionType;
	}

	public HookResolutionActionHost getHost() {
		return host;
	}

	public void setHost(HookResolutionActionHost host) {
		this.host = host;
	}


}
