package com.redhat.qe.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "host_nics")
public class NicList {

	@XmlElement(name = "host_nic")
	ArrayList<Nic> nics;

	public ArrayList<Nic> getnics() {
		return nics;
	}

}
