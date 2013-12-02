package com.redhat.qe.model.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.redhat.qe.model.Brick;


@XmlAccessorType( XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="brick")
public class DeletionBrickWrapper {
	public DeletionBrickWrapper(){
		super();
	}
	
	/**
	 * @param brick
	 */
	public DeletionBrickWrapper(Brick brick) {
		super();
		this.brick = brick;
	}

	private Brick brick;

	@XmlElement(name="name")
	public String getName(){
		if(brick.getHost().getAddress() != null)
			return brick.getName();
		else
			return null;
	}

	@XmlAttribute(name="id")
	public String getId(){
		return brick.getId();
	}



}
