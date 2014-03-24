package com.redhat.qe.test.rest.volume;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.test.rest.VolumeTestBase;


public class CollectionRemoveBrickTest extends VolumeTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributed("collectionRemoveBrickTest", getHost1(), getHost2());
	}
	
	@Test
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = brickRepo.list();
		int expectedBrickSize = bricks.size() -1;
		brickRepo.delete(bricks.get(0));
		Assert.assertEquals(expectedBrickSize, brickRepo.list().size());
	}

}
