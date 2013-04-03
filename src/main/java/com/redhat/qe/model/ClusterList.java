package com.redhat.qe.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "clusters")
public class ClusterList {

	@XmlElement(name = "cluster")
	ArrayList<Cluster> clusters;

	public ArrayList<Cluster> getClusters() {
		return clusters;
	}

}
