package com.redhat.qe.repository.rest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.redhat.qe.model.GeneralAction;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.ClusterList;
import com.redhat.qe.model.Datacenter;
import com.redhat.qe.model.DatacenterList;
import com.redhat.qe.model.GlusterHookList;
import com.redhat.qe.model.Hook;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.HostList;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.JobList;
import com.redhat.qe.model.Nic;
import com.redhat.qe.model.NicList;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.StepList;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.VolumeList;
import com.redhat.qe.model.jaxb.HookResolutionAction;
import com.redhat.qe.model.jaxb.HookResolutionActionHost;

public class JaxbContext {
	//singleton
	private static JAXBContext context;

	public static JAXBContext getContext() throws JAXBException {
		if (context == null) {
			context = JAXBContext.newInstance(Cluster.class, Datacenter.class,
					DatacenterList.class, ClusterList.class,
					Host.class, HostList.class,
					Volume.class, 
					Brick.class, 
					VolumeList.class, GlusterHookList.class, 
					Hook.class, Job.class, JobList.class,
					Step.class, StepList.class, GeneralAction.class, Nic.class, NicList.class);
		}
		return context;

	}

}
