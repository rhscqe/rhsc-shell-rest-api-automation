package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.rebalance.PopulateEachBrickStrategy;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.repository.rest.BrickRepository;

@Ignore
//Reducing replica cound disallowed in glusterfs 3.4.0.55rhs
public class MigrateReduceRepCountByOneTest extends MigrateTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributedReplicate("startnegativerepcount", host1, host2);
	}
	
	@Override
	protected void populateVolume() {
//		new BrickPopulator().createDataForEachBrick(getSession(), getHost1().getCluster(), volume, mounter, mountPoint);
	}
	
	@Test
	@Tcms({"325635"})
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		final ArrayList<Brick> bricks = brickRepo.list();
		ArrayList<Brick> replicateSubVolume = new ArrayList<Brick>(){{
			add(bricks.get(0));
			add(bricks.get(2));
			add(bricks.get(4));
		}};
		MigrateBrickAction migrateAction = MigrateBrickAction.create(getSession(), replicateSubVolume.toArray(new Brick[0]));
	    migrateAction.getBrickList().setReplicaCount(1);

		MigrateBrickAction migrateActionResponse = brickRepo.migrate(migrateAction);
		Job migrateJob = getJob(migrateActionResponse);

		validateJobAndStepsStarted(migrateJob);
	}



}
