package com.redhat.qe.test.rest.rebalance;

import java.util.ArrayList;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.ssh.RebalanceProcessHelper;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.test.rest.migratebrick.MigrateTestBase;

public class StartRebalanceWhileBrickMigrationTest extends MigrateTestBase {

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("rebalwhilemigration", 4,getHost1(), getHost2());
	}
	
	@Test
	@Tcms("318690")
	public void teststart(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = brickRepo.list();
		brickRepo.migrate(bricks.get(0), bricks.get(1));
		try{
			ResponseWrapper rebalanceResponse = getVolumeRepository()._rebalance(volume);
			rebalanceResponse.expect("task is in progress");
		}finally{
			brickRepo._stopMigrate(bricks.get(0), bricks.get(1));
		}
	}
	
	@Test
	public void teststop(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = brickRepo.list();
		brickRepo.migrate(bricks.get(0), bricks.get(1));
		try{
			ResponseWrapper rebalanceResponse = getVolumeRepository()._stopRebalance(volume);
			rebalanceResponse.expect("(task is in progress)|(Rebalance is not running)");
		}finally{
			brickRepo._stopMigrate(bricks.get(0), bricks.get(1));
		}
	}
}
