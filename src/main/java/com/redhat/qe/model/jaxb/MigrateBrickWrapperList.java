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
	

	@XmlElement(name = "brick")
	ArrayList<MigrateBrickWrapper> bricks;

	public ArrayList<MigrateBrickWrapper> getBrickWrappers() {
		return bricks;
	}

	public void setBricks(ArrayList<MigrateBrickWrapper> arrayList) {
		this.bricks= arrayList;
	}

	
	

}
