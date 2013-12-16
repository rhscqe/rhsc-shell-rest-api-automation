package com.redhat.qe.test.rest.volume;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.IVolumeRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.SimpleRestRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

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

	@Test
	public void testDistributedStripeInvalidNumBricks() {
		Volume expected = VolumeFactory.distributedStripe("mydistvolume", 4, 4, host1,
				host2);
		ResponseWrapper response = getVolumeRepository()._create(expected);
		response.expectSimilarCode(400);
		
	}

	@After
	public void cleanupVolumeTest() {
		if (actual != null)
			getVolumeRepository().destroy(actual);
	}

	private VolumeRepository getVolumeRepository() {
		return getVolumeRepository(getHost1().getCluster());
	}




}
