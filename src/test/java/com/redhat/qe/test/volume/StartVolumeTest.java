package com.redhat.qe.test.volume;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class StartVolumeTest extends SingleVolumeTestBase{


	@Test
	@Tcms("250995")
	public void test() {
		getVolumeRepository().start(volume);
		getVolumeRepository().stop(volume);
	}


}
