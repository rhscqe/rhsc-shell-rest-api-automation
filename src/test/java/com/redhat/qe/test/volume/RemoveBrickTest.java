package com.redhat.qe.test.volume;

import java.util.ArrayList;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.model.Brick;

public class RemoveBrickTest extends SingleVolumeTestBase{
	@Test
	@Tcms("251288")
	public void test(){
		//setup
		Brick brick = BrickFactory.brick(host1);
		getVolumeRepository().addBrick(volume, brick);
		Asserts.assertContains("", getVolumeRepository().listBricks(volume), brick);
		
		
		//test
		Brick brickToRemove = getVolumeRepository().listBricks(volume).get(0);
		getVolumeRepository().removeBrick(volume, brickToRemove);
		
		//verify
		Asserts.assertDoesntContain("", getVolumeRepository().listBricks(volume), brickToRemove);
		
	}

}
