package com.redhat.qe.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "steps")
public class StepList {

	@XmlElement(name = "step")
	ArrayList<Step> steps;

	public ArrayList<Step> getSteps() {
		return steps;
	}

}