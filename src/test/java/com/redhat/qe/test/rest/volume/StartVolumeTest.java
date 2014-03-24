package com.redhat.qe.test.rest.volume;

import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.rest.VolumeTestBase;

public class StartVolumeTest extends VolumeTestBase {

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributed("volumeToBeStarted", getHost1(), getHost2());
	}
	
	@Test
	public void test(){
		getVolumeRepository().start(volume);
		getVolumeRepository().show(volume);
		getVolumeRepository()._stop(volume).expectSimilarCode(200);
		getVolumeRepository().show(volume);
	}

}
