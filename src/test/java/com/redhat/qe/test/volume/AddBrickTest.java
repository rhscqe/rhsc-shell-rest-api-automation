package com.redhat.qe.test.volume;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.model.Brick;

public class AddBrickTest extends SingleVolumeTestBase{
	@Test
	@Tcms("251287")
	public void test(){
		Brick brick = BrickFactory.brick(host1);
		getVolumeRepository().addBrick(volume, brick);
		
		Asserts.assertContains("", getVolumeRepository().listBricks(volume), brick);
		
	}

}
