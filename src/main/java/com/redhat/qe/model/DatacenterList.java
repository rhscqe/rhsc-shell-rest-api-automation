package com.redhat.qe.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "data_centers")
public class DatacenterList {

	@XmlElement(name = "data_center")
	ArrayList<Datacenter> datacenters;

	/**
	 * @return the datacenters
	 */
	public ArrayList<Datacenter> getDatacenters() {
		return datacenters;
	}

	/**
	 * @param datacenters
	 *            the datacenters to set
	 */
	public void setDatacenters(ArrayList<Datacenter> datacenters) {
		this.datacenters = datacenters;
	}

}