package com.redhat.qe.rest;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;

public class AddHostTest extends ClusterTestBase{
	
	@Test
	public void test(){
		Host host = RhscConfiguration.getConfiguration().getHosts().iterator().next();
		host = getHostRepository().createOrShow(host);
		WaitUtil.waitForHostStatus(getHostRepository(), host, "up", 30);
		
		getHostRepository().deactivate(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host,"maintenance", 400));
		getHostRepository().destroy(host);
		
	}



}
