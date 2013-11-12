package com.redhat.qe.model.gluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.calgb.test.performance.HttpSession;

import com.google.common.base.Predicate;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.jaxb.BrickHostToServerIdXmlAdapter;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.helpers.utils.StringUtils;
import com.redhat.qe.helpers.utils.StringUtils.RepeatingHashMap;
import com.redhat.qe.model.Brick;
import com.redhat.qe.repository.rest.HostRepository;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="brick")
public class Node extends Brick {
	
	private int status;
	private int port;
	private int pid;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	
	
}
