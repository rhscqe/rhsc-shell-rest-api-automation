package com.redhat.qe.test.ovirtshell.volume;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.model.Brick;

public class RemoveLastBrickTest extends SingleVolumeTestBase{
	@Test
	@Tcms("251288")
	public void test(){
		
		//test
		Brick brickToRemove = getVolumeRepository().listBricks(volume).get(0);
		getVolumeRepository()._removeBrick(volume, brickToRemove).expect("[Cc]an not remove all the bricks from a [vV]olume");
		
	}

	@Before
	public void removeEveryBrickExceptLast() {
		ArrayList<Brick> bricks = getVolumeRepository().listBricks(volume);
		for (int i =0 ; i < bricks.size() -1 ; i ++){
			getVolumeRepository().removeBrick(volume,  bricks.get(i));
		}
	}

}
