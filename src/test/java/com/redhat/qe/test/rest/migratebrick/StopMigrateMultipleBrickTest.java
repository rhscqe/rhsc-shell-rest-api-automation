package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.repository.StepsRepositoryHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.gluster.VolumeStatusOutput;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;
import com.redhat.qe.test.rest.PopulatedVolumeTestBase;

public class StopMigrateMultipleBrickTest extends PopulatedVolumeTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("startbrickmigrate",4, getHost1(), getHost2());
	}
//	
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
	
	@Test
	@Tcms({"318702"})
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = brickRepo.list();

		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(0), bricks.get(1));
		Job migrateJob = getJob(migrateAction);

		Step executingStep = validateJobAndStepsStarted(migrateJob);


		brickRepo.stopMigrate(bricks.get(0));
		validateJobAndStepStopped(migrateAction, migrateJob, executingStep);
		
		
		ExecSshSession host1Session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		host1Session.start();
		try{
			 VolumeStatusOutput volStatus = new VolumeXmlRepository(host1Session).status(volume);
			 Assert.assertEquals(0,volStatus.getTasks().size());
		}finally{
			host1Session.stop();
		}
	}

	/**
	 * @param migrateAction
	 * @param migrateJob
	 * @param executingStep
	 */
	private void validateJobAndStepStopped(MigrateBrickAction migrateAction,
			Job migrateJob, Step executingStep) {
		Asserts.assertEqualsIgnoreCase("failed", getJob(migrateAction).getStatus().getState());
		Asserts.assertEqualsIgnoreCase("aborted", getMigrateStep(migrateJob, executingStep).getStatus().getState());
	}

	/**
	 * @param migrateJob
	 * @return
	 */
	private Step validateJobAndStepsStarted(Job migrateJob) {
		Asserts.assertEqualsIgnoreCase("started",migrateJob.getStatus().getState());
		Step executingStep = new StepsRepositoryHelper().getExecutingStep(getStepRepo(migrateJob));
		Asserts.assertEqualsIgnoreCase("started", executingStep.getStatus().getState() );
		ArrayList<Step> children = new StepsRepositoryHelper().getChildren(getStepRepo(migrateJob), executingStep);
		Assert.assertTrue(children.size() == 1);
		Asserts.assertEqualsIgnoreCase("started", getMigrateStep(migrateJob, executingStep).getStatus().getState());
		return executingStep;
	}

/**
 * @param migrateJob
 * @param executingStep
 * @return
 */
private Step getMigrateStep(Job migrateJob, Step executingStep) {
	return new StepsRepositoryHelper().getChildren(getStepRepo(migrateJob), executingStep).get(0);
}

/**
 * @param migrateJob
 * @return
 */
private StepRepository getStepRepo(Job migrateJob) {
	return new StepRepository(getSession(), migrateJob);
}

/**
 * @param migrateAction
 * @return
 */
private Job getJob(MigrateBrickAction migrateAction) {
	return new JobRepository(getSession()).show(migrateAction.getJob());
}

}
