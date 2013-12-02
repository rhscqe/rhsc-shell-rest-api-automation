package com.redhat.qe.model.jaxb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.redhat.qe.model.Brick;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bricks")
public class DeletionBrickWrapperList {
	

	@XmlElement(name = "brick")
	ArrayList<DeletionBrickWrapper> bricks  = new ArrayList<DeletionBrickWrapper>();

	public ArrayList<DeletionBrickWrapper> getBrickWrappers() {
		return bricks;
	}

	public void setBricks(ArrayList<DeletionBrickWrapper> arrayList) {
		this.bricks= arrayList;
	}

	public static DeletionBrickWrapperList fromBricks(Brick... bricks) {
		DeletionBrickWrapperList result = new DeletionBrickWrapperList();
		for(Brick brick : bricks){
			DeletionBrickWrapper wrapper = new DeletionBrickWrapper(brick);
			result.bricks.add(wrapper);
		}
		return result;
	}
	
	

}
