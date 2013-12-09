package com.redhat.qe.test.ovirtshell.volume;



import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.ssh.IResponse;

public class StartStopVolumeTest extends SingleVolumeTestBase{


	@Test
	@Tcms({"250995","251000"})
	public void test() {
		getVolumeRepository().start(volume);
		getVolumeRepository().stop(volume);
	}
	
	@Test
	public void startWhenAlreadyStarted() {
		getVolumeRepository().start(volume);
		try{
			IResponse response = getVolumeRepository()._start(volume);
			response.expect("already started");

		}finally{
			getVolumeRepository().stop(volume);
		}
	}


}
