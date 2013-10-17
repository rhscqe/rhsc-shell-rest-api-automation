package com.redhat.qe.test.volume;

import org.junit.After;
import org.junit.Before;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rhscshell.VolumeRepository;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class SingleVolumeTestBase extends TwoHostClusterTestBase {

	protected Volume volume;

	public SingleVolumeTestBase() {
		super();
	}

	@Before
	public void setupthis() {
		volume = VolumeFactory.distributed("mydistvolume", host1, host2);
		volume= getVolumeRepository().create(volume);
		
	}


	@After
	public void cleanup() {
		if(volume != null){
			getVolumeRepository().destroy(volume);
			
		}
		
	}

}