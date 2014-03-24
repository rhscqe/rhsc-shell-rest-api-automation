package com.redhat.qe.test.ovirtshell.host;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;

public class LoopCreateVolumeRestTest extends com.redhat.qe.test.rest.TwoHostClusterTestBase{
	
	private Volume volume;

	@Before
	public void setupHosts() {
	}
	@Before
	public void setupTwoHosts() {
	}

	@After
	public void cleanupRhsc() {
	}
	
	@Test
	public void setupThis(){
		for(int i = 0 ; i< 100; i ++){
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx" + i);
			super.setupHosts();
			super.setupTwoHosts();
			volume = new VolumeFactory().distributed("mydistvolume", host1, host2);
			volume = getVolumeRepository(getHost1().getCluster()).create(volume);
			getVolumeRepository(getHost1().getCluster()).destroy(volume);
			super.cleanupRhsc();
		}
	}
	
}
