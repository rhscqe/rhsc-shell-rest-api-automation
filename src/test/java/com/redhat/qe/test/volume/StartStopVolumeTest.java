package com.redhat.qe.test.volume;



import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class StartStopVolumeTest extends SingleVolumeTestBase{


	@Test
	@Tcms({"250995","251000"})
	public void test() {
		getVolumeRepository().start(volume);
		getVolumeRepository().stop(volume);
	}


}
