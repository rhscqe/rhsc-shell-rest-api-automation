package com.redhat.qe.test.ovirtshell.volume;



import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.ovirtshell.TwoHostClusterTestBase;

public class VolumeTest extends TwoHostClusterTestBase {

	private Volume actual;
	
	@After 
	public void cleanup(){
		if(actual != null){
			getVolumeRepository().destroy(actual);
		}
		
	}

	@Test
	@Tcms("250994")
	public void distributedVolumeTest() {
		Volume expected = new VolumeFactory().distributed("mydistvolume", host1, host2);
		actual = getVolumeRepository().create(expected);

		assertEquals("replica count",expected.getReplica_count(),actual.getReplica_count());
		assertEquals("status","down",actual.getStatus());
		assertEquals("stripe count",expected.getStripe_count(),actual.getStripe_count());
		assertEquals("transport type","tcp",actual.getTransportType());
	}


	@Test
	@Tcms("250994")
	public void replicateVolumeTest() {
		Volume expected = new VolumeFactory().replicate("myreplicateVol", host1,host2);
		actual = getVolumeRepository().create(expected );
		
		assertEquals("replica count",expected.getReplica_count(),actual.getReplica_count());
		assertEquals("status","down",actual.getStatus());
		assertEquals("stripe count",expected.getStripe_count(),actual.getStripe_count());
		assertEquals("transport type","tcp",actual.getTransportType());
	}

	@Test
	@Tcms("250994")
	public void distributedReplicateVolume() {
		Volume expected = new VolumeFactory().distributedReplicate("mydistreplicateVol", host1,host2);
		actual = getVolumeRepository().create(expected);
		
		assertEquals("replica count",expected.getReplica_count(),actual.getReplica_count());
		assertEquals("status","down",actual.getStatus());
		assertEquals("stripe count",expected.getStripe_count(),actual.getStripe_count());
		assertEquals("transport type","tcp",actual.getTransportType());
		
	}
}
