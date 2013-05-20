package com.redhat.qe.test.volume;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.model.Brick;

public class ShowBrickTest extends SingleVolumeTestBase{
	
	@Test
	@Tcms("251289")
	public void test(){
		//setup
		Brick brick = BrickFactory.brick(host1);
		getVolumeRepository().addBrick(volume, brick);
		Asserts.assertContains("", getVolumeRepository().listBricks(volume), brick);
		
		Brick actual = getVolumeRepository().showBrick(volume, brick);
		
		Assert.assertEquals(brick.getDir(), actual.getDir());
		Assert.assertEquals(brick.getName(), actual.getName());
	}

}
