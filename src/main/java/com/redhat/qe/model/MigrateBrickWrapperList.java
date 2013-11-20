package com.redhat.qe.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bricks")
public class MigrateBrickWrapperList {

	@XmlElement(name = "brick")
	ArrayList<MigrateBrickWrapper> bricks;

	public ArrayList<MigrateBrickWrapper> getBrickWrappers() {
		return bricks;
	}

	public void setBricks(ArrayList<MigrateBrickWrapper> arrayList) {
		this.bricks= arrayList;
	}

}
