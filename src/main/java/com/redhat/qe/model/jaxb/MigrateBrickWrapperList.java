package com.redhat.qe.model.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bricks")
public class MigrateBrickWrapperList {
	
	@XmlElement(name = "replica_count")
	private int replicaCount;

	@XmlElement(name = "brick")
	ArrayList<MigrateBrickWrapper> bricks;

	public ArrayList<MigrateBrickWrapper> getBrickWrappers() {
		return bricks;
	}

	public void setBricks(ArrayList<MigrateBrickWrapper> arrayList) {
		this.bricks= arrayList;
	}

	/**
	 * @return the replicaCount
	 */
	public int getReplicaCount() {
		return replicaCount;
	}

	/**
	 * @param replicaCount the replicaCount to set
	 */
	public void setReplicaCount(int replicaCount) {
		this.replicaCount = replicaCount;
	}

	/**
	 * @return the bricks
	 */
	public ArrayList<MigrateBrickWrapper> getBricks() {
		return bricks;
	}
	
}
