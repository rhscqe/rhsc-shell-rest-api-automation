package com.redhat.qe.rest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;

public class VolumeTest extends TwoHostClusterTestBase {
	private Volume actual;

	@Test
	public void testDistributedVol() {
		Volume expected = VolumeFactory.distributed("mydistvolume", host1,
				host2);
		actual = getVolumeRepository().createOrShow(expected);

		assertEquals("replica count", expected.getReplica_count(),
				actual.getReplica_count());
		assertEquals("status", "down", actual.getStatus());
		assertEquals("stripe count", expected.getStripe_count(),
				actual.getStripe_count());

	}

	@After
	public void cleanupVolumeTest() {
		if (actual != null)
			getVolumeRepository().delete(actual);
	}

}
