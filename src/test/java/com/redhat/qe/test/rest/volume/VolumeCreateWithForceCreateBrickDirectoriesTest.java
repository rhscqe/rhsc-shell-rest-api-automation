package com.redhat.qe.test.rest.volume;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

public class VolumeCreateWithForceCreateBrickDirectoriesTest extends TwoHostClusterTestBase{

	@Test
	@Ignore
	public void testRootPartitionCreateWithoutForce(){
		 Volume volume = createVolume();
		 ResponseWrapper response = getVolumeRepository(getHost1().getCluster())._create(volume);
		 response.expectSimilarCode(400);
		
	}
	
	@Test
	public void test(){
		 Volume volume = createVolume();
		 getVolumeRepository(getHost1().getCluster()).createWithForceCreationOfBrickDirectories(volume);
	}

	/**
	 * @return
	 */
	private Volume createVolume() {
		Volume volume = VolumeFactory.distributed("VolumeCreateForceCreateBrickDir", getHost1(),getHost2());
		 ArrayList<Brick> bricks = new ArrayList<Brick>();
		 bricks.add( new BrickFactory(AbsolutePath.fromDirs("tmp")).brick(getHost1()));
		 volume.setBricks(bricks);
		return volume;
	}

}
