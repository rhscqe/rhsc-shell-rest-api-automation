package com.redhat.qe.test.ovirtshell.host;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.test.ovirtshell.RhscShellSessionTestBase;
import com.redhat.qe.test.ovirtshell.TwoHostClusterTestBase;

public class ForceRemovalTest extends RhscShellSessionTestBase{
	
	private Host host;
	
	@Before
	public void setup(){
		host = RhscConfiguration.getConfiguration().getHosts().iterator().next();
		getClusterRepository().createOrShow(host.getCluster());
	}
	
	@Tcms(value = { "261779" })	
	@Test
	public void test(){
		host = getHostRepository().createOrShow(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host,"up", 400));
		
		getHostRepository().deactivate(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host,"maintenance", 400));
		
		getHostRepository().destroy(host, "--force true");	}

}
