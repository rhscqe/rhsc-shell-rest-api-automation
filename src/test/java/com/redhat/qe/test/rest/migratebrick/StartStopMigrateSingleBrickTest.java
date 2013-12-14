package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.repository.StepsRepositoryHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.test.rest.PopulatedVolumeTestBase;

public class StartStopMigrateSingleBrickTest extends MigrateTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("startbrickmigrate",4, getHost1(), getHost2());
	}
	
//	@Override
//	public void cleanupRhsc(){
//		//TODO don't clean up
//	}
//	
//	@Override
//	public void destroyVolume(){
//		//TodO dont' do that
//	}
//	
	
	@Tcms({"321097","318701"})
	@Test
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = brickRepo.list();

		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(0));
		Job migrateJob = getJob(migrateAction);
		validateJobAndStepsStarted(migrateJob);


		brickRepo.stopMigrate(bricks.get(0));
		Asserts.assertEqualsIgnoreCase("failed", getJob(migrateAction).getStatus().getState());
		Asserts.assertEqualsIgnoreCase("aborted", getMigrateStep(migrateJob).getStatus().getState());
		
		ensureNoGlusterVolumeTasks();

	}


/**
 * @param migrateJob
 * @param executingStep
 * @return
 */
protected Step getMigrateStep(Job migrateJob) {
	Step executingStep = new StepsRepositoryHelper().getExecutingStep(getStepRepo(migrateJob));
	return new StepsRepositoryHelper().getChildren(getStepRepo(migrateJob), executingStep).get(0);
}


}
