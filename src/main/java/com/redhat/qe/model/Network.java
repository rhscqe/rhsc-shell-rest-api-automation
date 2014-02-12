package com.redhat.qe.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="network")
public class Network extends Model{
	
	@XmlAttribute
	String href;
	
	
	@XmlAttribute
	String id;

	@Override
	public String getId() {
		return id;
	}

}
