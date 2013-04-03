package com.redhat.qe.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "gluster_volumes")
public class VolumeList {

	@XmlElement(name = "gluster_volume")
	ArrayList<Volume> volumes;

	public ArrayList<Volume> getVolumes() {
		return volumes;
	}

}