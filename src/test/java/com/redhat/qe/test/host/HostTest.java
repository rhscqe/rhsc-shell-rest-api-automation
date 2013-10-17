package com.redhat.qe.test.host;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.test.RhscShellSessionTestBase;

public class HostTest extends RhscShellSessionTestBase{
	
	private Host host;
	
	@Before
	public void setup(){
		host = RhscConfiguration.getConfiguration().getHosts().iterator().next();
		getClusterRepository().createOrShow(host.getCluster());
	}
	
	@Tcms(value = { "174413","250987","250988","250990", "250991" })	
	@Test
	public void test(){
		host = getHostRepository().createOrShow(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host,"up", 400));
		
		getHostRepository().deactivate(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host,"maintenance", 400));
		
		getHostRepository().destroy(host);
	}

}
