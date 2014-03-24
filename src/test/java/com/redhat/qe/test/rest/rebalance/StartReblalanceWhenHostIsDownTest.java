package com.redhat.qe.test.rest.rebalance;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.testng.Assert;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.VolumeTestBase;

@Ignore
public class StartReblalanceWhenHostIsDownTest extends VolumeTestBase{
	
	
	@Before
	public void startVolume(){
		stopGlusterOnHost1();
		getVolumeRepository().start(volume);
	}

	@After
	public void stopVolume(){
		startGlusterOnHost1();
		if(volume != null)
			getVolumeRepository().stop(volume);
	}
	
	@Tcms("311415")
	@Test
	public void test(){
		ResponseWrapper response = new VolumeRepository(getSession(), volume.getCluster())._rebalance(volume);
		Assert.assertNotEquals(200, response.getCode(), "rebalance succeeded when it shouldn't have");
		response.expect("please check if gluster daemon is operational");
	}

	public void stopGlusterOnHost1() {
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		host1session.start();
		try{
			host1session.runCommandAndAssertSuccess("service glusterd stop");
		}finally{
			host1session.stop();
		}
	}

	public void startGlusterOnHost1() {
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		host1session.start();
		try{
			host1session.runCommandAndAssertSuccess("service glusterd start");
		}finally{
			host1session.stop();
		}
		if(getHostRepository().show(getHost1()).getState().contains("non_operational")){
			getHostRepository().activate(getHost1());
			WaitUtil.waitForHostStatus(getHostRepository(), getHost1(), "up", 20);
		}
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributed("ReblalanceHostDown", getHosts().toArray(new Host[0]));
	}


}
