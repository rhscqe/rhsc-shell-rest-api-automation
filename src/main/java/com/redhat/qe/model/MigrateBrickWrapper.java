package com.redhat.qe.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType( XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="brick")
public class MigrateBrickWrapper {
	public MigrateBrickWrapper(){
		super();
	}
	
	/**
	 * @param brick
	 */
	public MigrateBrickWrapper(Brick brick) {
		super();
		this.brick = brick;
	}

	private Brick brick;

	@XmlElement(name="name")
	public String getName(){
		return brick.getName();
	}



}
