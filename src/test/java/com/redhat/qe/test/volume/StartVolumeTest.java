package com.redhat.qe.test.volume;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class StartVolumeTest extends TwoHostClusterTestBase {

	
	private Volume volume;

	@Before
	public void setupthis(){
		volume = VolumeFactory.distributed("mydistvolume", host1, host2);
		volume= getVolumeRepository().create(volume);
		
	}
	
	@After 
	public void cleanup(){
		if(volume != null){
			getVolumeRepository().destroy(volume);
			
		}
		
	}

	@Test
	@Tcms("250995")
	public void test() {
		getVolumeRepository().start(volume);
		getVolumeRepository().stop(volume);
	}


}
