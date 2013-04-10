package com.redhat.qe.test.volume;


import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.HostInClusterTestBase;

public class VolumeTest extends HostInClusterTestBase {

	@Test
	public void distributedVolumeTest() {
		Volume volume = VolumeFactory.distributed("mydistvolume", host1, host2);
		volume = getVolumeRepository().create(volume);
		getVolumeRepository().destroy(volume);

	}


	@Test
	public void replicateVolumeTest() {
		Volume volume = VolumeFactory.replicate("myreplicateVol", host1,host2);
		volume = getVolumeRepository().create(volume);
		getVolumeRepository().destroy(volume);
	}

}
