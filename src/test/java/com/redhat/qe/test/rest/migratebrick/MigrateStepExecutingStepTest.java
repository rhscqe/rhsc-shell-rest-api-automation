package com.redhat.qe.test.rest.migratebrick;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.repository.rest.BrickRepository;

public class MigrateStepExecutingStepTest extends MigrateTestBase {

	@Override
	protected void populateVolume() {
//		new BrickPopulator().createDataForEachBrick(getSession(), getHost1().getCluster(), volume, mounter, mountPoint);
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("MigrateStepExecutingStepTest", 4, getHost1(),
				getHost2());
	}

	@Test
	@Tcms("327433")
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		final ArrayList<Brick> bricks = brickRepo.list();
		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(0), bricks.get(1));
		Job migrateJob = getJob(migrateAction);

		validateJobAndStepsStarted(migrateJob);
//		Assert.assertTrue(new JobRepoHelper().waitUntilJobFinished(getJobRepository(), migrateJob).isSuccessful());
		
		
		Step step = getMigrateStep(migrateJob);
		getStepRepo(migrateJob).show(step);
		step.getType() ; 

		
		assertEquals("removing_bricks", step.getType());
		assertTrue(step.getDescription().contains("Removing Bricks"));
		Asserts.assertEqualsIgnoreCase("true", step.getExternal());
		Asserts.assertEqualsIgnoreCase("", step.getExternalType());

	}

}
