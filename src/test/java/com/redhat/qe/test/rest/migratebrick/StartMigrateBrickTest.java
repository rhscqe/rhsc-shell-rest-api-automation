package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;

import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.test.rest.PopulatedVolumeTestBase;

public class StartMigrateBrickTest extends PopulatedVolumeTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("startbrickmigrate", getHost1(), getHost2());
	}
	
	@Test
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = brickRepo.list();

		brickRepo._migrate(bricks.get(0));
	}

}
