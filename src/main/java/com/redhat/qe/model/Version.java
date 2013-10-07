package com.redhat.qe.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="version")
public class Version {
	@XmlAttribute(name="major")
	private int major;
	@XmlAttribute(name="minor")
	private int minor;
	/**
	 * @return the major
	 */
	public int getMajor() {
		return major;
	}
	/**
	 * @param major the major to set
	 */
	public void setMajor(int major) {
		this.major = major;
	}
	/**
	 * @return the minor
	 */
	public int getMinor() {
		return minor;
	}
	/**
	 * @param minor the minor to set
	 */
	public void setMinor(int minor) {
		this.minor = minor;
	}

}
