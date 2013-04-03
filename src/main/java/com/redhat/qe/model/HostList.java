package com.redhat.qe.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "hosts")
public class HostList {

	@XmlElement(name = "host")
	ArrayList<Host> hosts;

	public ArrayList<Host> getHosts() {
		return hosts;
	}

}