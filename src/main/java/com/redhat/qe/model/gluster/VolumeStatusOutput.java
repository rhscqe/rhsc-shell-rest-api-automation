package com.redhat.qe.model.gluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.jaxb.MyMarshaller;
import com.redhat.qe.helpers.utils.ListUtil;
import com.redhat.qe.helpers.utils.StringUtils;
import com.redhat.qe.helpers.utils.StringUtils.RepeatingHashMap;
import com.redhat.qe.model.Job;
import com.redhat.qe.repository.rest.JaxbContext;
import com.redhat.qe.repository.rhscshell.GlusterOption;
import com.redhat.qe.repository.rhscshell.GlusterOptionValue;
import com.redhat.qe.ssh.IResponse;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="gluster_volume")
public class VolumeStatusOutput {
	
	private String name;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Task> tasks = new ArrayList<Task>();






	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	
	

}
