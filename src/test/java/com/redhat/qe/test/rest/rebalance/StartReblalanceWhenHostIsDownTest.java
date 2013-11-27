package com.redhat.qe.test.rest.rebalance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.VolumeTestBase;

public class StartReblalanceWhenHostIsDownTest extends VolumeTestBase{
	
	
	@Tcms("311415")
	@Test
	public void test(){
		ResponseWrapper response = new VolumeRepository(getSession(), volume.getCluster())._rebalance(volume);
		Assert.assertNotEquals(200, response.getCode(), "rebalance succeeded when it shouldn't have");
		response.expect("check.*daemon.*operational");
	}

	@Before
	public void stopGlusterOnHost1() {
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		host1session.start();
		try{
			host1session.runCommandAndAssertSuccess("service glusterd stop");
		}finally{
			host1session.stop();
		}
	}

	@After 
	public void startGlusterOnHost1() {
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		host1session.start();
		try{
			host1session.runCommandAndAssertSuccess("service glusterd start");
		}finally{
			host1session.stop();
		}
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("rebalancehostdowntest", getHosts().toArray(new Host[0]));
	}


}
