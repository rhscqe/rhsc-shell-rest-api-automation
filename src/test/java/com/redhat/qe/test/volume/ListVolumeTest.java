package com.redhat.qe.test.volume;

import java.util.ArrayList;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.model.Volume;

public class ListVolumeTest extends ThreeVolumeTestBase{


	@Test
	@Tcms("251001")
	public void test(){
		ArrayList<Volume> volumes = getVolumeRepository().list(volume1.getCluster());
		Asserts.assertContains("volume1", volumes, volume1);
		Asserts.assertContains("volume2", volumes, volume2);
		Asserts.assertContains("volume3", volumes, volume3);
		
	}

}