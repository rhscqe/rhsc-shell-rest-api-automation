package com.redhat.qe.test.host;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.factories.HostFactory;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.repository.HostRepository;
import com.redhat.qe.test.OpenShellSessionTestBase;

public class HostTest extends OpenShellSessionTestBase{
	
	private Host host;
	
	@Before
	public void setup(){
		host = RhscConfiguration.getConfiguration().getHosts().iterator().next();
		getClusterRepository().createOrShow(host.getCluster());
	}
	
	@Tcms(value = { "174413" })	
	@Test
	public void test(){
		host = getHostRepository().createOrShow(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host,"up", 400));
		
		getHostRepository().deactivate(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host,"maintenance", 400));
		
		getHostRepository().destroy(host);
	}

}
