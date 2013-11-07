package com.redhat.qe.test.ovirtshell.volume;



import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.model.Volume;

public class ShowVolumeTest extends SingleVolumeTestBase {

	
	@Test
	@Tcms({"250997", "251243", "251317"})
	public void test() {
		Volume actual = getVolumeRepository().show(volume);
		Volume expected = volume;
		
		assertEquals("id",expected.getId(),actual.getId());
		assertEquals("name",expected.getName(),actual.getName());
		assertEquals("cluster id",expected.getCluster().getId(),actual.getCluster().getId());
		assertEquals("replica count",expected.getReplica_count(),actual.getReplica_count());
		assertEquals("status","down",actual.getStatus());
		assertEquals("stripe count",expected.getStripe_count(),actual.getStripe_count());
		assertEquals("transport type","tcp",actual.getTransportType());
	}


}
