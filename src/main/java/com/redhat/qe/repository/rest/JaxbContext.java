package com.redhat.qe.repository.rest;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

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
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.VolumeList;

public class JaxbContext {
	//singleton
	private static JAXBContext context;

	public static JAXBContext getContext() throws JAXBException {
		if (context == null) {
			context = JAXBContext.newInstance(Cluster.class, Datacenter.class,
					DatacenterList.class, ClusterList.class,
					Host.class, HostList.class,
					Volume.class, Brick.class, VolumeList.class, GlusterHookList.class, Hook.class, Job.class, JobList.class);
		}
		return context;

	}

}
