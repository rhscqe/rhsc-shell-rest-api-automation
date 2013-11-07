package com.redhat.qe.helpers.rebalance;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.rest.HttpSessionFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.ClusterRepository;
import com.redhat.qe.repository.rest.HostRepository;
import com.redhat.qe.repository.rest.VolumeRepository;

public class Whatever {
	public static void main(String[] args){
			HttpSession session = new HttpSessionFactory().createHttpSession(RhscConfiguration.getConfiguration().getRestApi());
			ArrayList<Host> hosts = new HostRepository(session).list();
			Host[] _hosts = hosts.toArray(new Host[hosts.size()]);
			Volume volume = VolumeFactory.distributed("blah", _hosts);
			new VolumeRepository(session, new ClusterRepository(session).show(hosts.get(0).getCluster())).create(volume);
	}

}
