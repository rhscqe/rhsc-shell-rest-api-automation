package com.redhat.qe.test.volume;

import org.junit.After;
import org.junit.Before;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class ThreeVolumeTestBase extends TwoHostClusterTestBase {

	Volume volume1;
	Volume volume2;
	Volume volume3;

	public ThreeVolumeTestBase() {
		super();
	}

	@Before
	public void setupthis() {
		volume1 = VolumeFactory.distributed("mydistvolume1", host1, host2);
		volume1 = getVolumeRepository().create(volume1);
		
		volume2 = VolumeFactory.distributed("mydistvolume2", host1, host2);
		volume2 = getVolumeRepository().create(volume2);
		
		volume3 = VolumeFactory.distributed("mydistvolume3", host1, host2);
		volume3 = getVolumeRepository().create(volume3);
	}

	@After
	public void cleanup() {
		if(volume1 != null){
			getVolumeRepository().destroy(volume1);
		}
		if(volume2 != null){
			getVolumeRepository().destroy(volume2);
		}
		if(volume3 != null){
			getVolumeRepository().destroy(volume3);
		}
		
	}

}