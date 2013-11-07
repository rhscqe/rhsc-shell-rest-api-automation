package com.redhat.qe.test.rest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.IVolumeRepository;
import com.redhat.qe.repository.rest.SimpleRestRepository;
import com.redhat.qe.repository.rest.VolumeRepository;

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
			getVolumeRepository().destroy(actual);
	}

	private IVolumeRepository getVolumeRepository() {
		return getVolumeRepository(getHost1().getCluster());
	}




}
