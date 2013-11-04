package com.redhat.qe.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "glusterhooks")
public class GlusterHookList {
	@XmlElement(name = "gluster_hook")
	ArrayList<Hook> hooks;

	public ArrayList<Hook> getHooks() {
		return hooks;
	}

	public void setHooks(ArrayList<Hook> hooks) {
		this.hooks = hooks;
	}
}