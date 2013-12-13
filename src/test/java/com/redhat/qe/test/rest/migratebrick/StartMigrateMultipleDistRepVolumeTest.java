package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.repository.rest.BrickRepository;

public class StartMigrateMultipleDistRepVolumeTest extends MigrateTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributedReplicate("startnegativerepcount", host1, host2);
	}
	
	@Override
	protected void populateVolume() {
//		new BrickPopulator().createDataForEachBrick(getSession(), getHost1().getCluster(), volume, mounter, mountPoint);
	}
	
	@Test
	@Tcms({"318702"})
	public void testRestStartedStatus(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		ArrayList<Brick> bricks = brickRepo.list();
		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(bricks.size()-2),bricks.get(bricks.size()-1));
		Job migrateJob = getJob(migrateAction);

		validateJobAndStepsStarted(migrateJob);
	}



}
