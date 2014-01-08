package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;

import org.junit.Assert;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.repository.StepsRepositoryHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.model.WaitUtil.WaitResult;
import com.redhat.qe.model.gluster.VolumeStatusOutput;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.PopulatedVolumeTestBase;

import dstywho.functional.Predicate;

public abstract class MigrateTestBase extends PopulatedVolumeTestBase {
	/**
	 * @param migrateAction
	 * @param migrateJob
	 * @param executingStep
	 */
	protected void validateJobAndStepStopped(MigrateBrickAction migrateAction,
			Job migrateJob, Step executingStep) {
		Asserts.assertEqualsIgnoreCase("failed", getJob(migrateAction)
				.getStatus().getState());
		Asserts.assertEqualsIgnoreCase("aborted",
				getMigrateStep(migrateJob, executingStep).getStatus()
						.getState());
	}

	/**
	 * @param migrateJob
	 * @return
	 */
	protected Step validateJobAndStepsStarted(Job migrateJob) {
		Asserts.assertEqualsIgnoreCase("started", migrateJob.getStatus() .getState());
		Step executingStep = getExecutingStep(migrateJob);
		Asserts.assertEqualsIgnoreCase("started", executingStep.getStatus() .getState());
		ArrayList<Step> children = new StepsRepositoryHelper().getChildren( getStepRepo(migrateJob), executingStep); 
		Assert.assertTrue(children.size() == 1);
		Asserts.assertEqualsIgnoreCase("started", getMigrateStep(migrateJob, executingStep).getStatus() .getState());
		return executingStep;
	}

	private Step getExecutingStep(Job migrateJob) {
		return new StepsRepositoryHelper() .getExecutingStep(getStepRepo(migrateJob));
	}

	protected Step getMigrateStep(Job migrateJob) {
		return getMigrateStep(migrateJob, getExecutingStep(migrateJob)); 
	}

	protected Step getMigrateStep(Job migrateJob, Step executingStep) {
		return new StepsRepositoryHelper().getChildren(getStepRepo(migrateJob),
				executingStep).get(0);
	}

	/**
	 * @param migrateJob
	 * @return
	 */
	protected StepRepository getStepRepo(Job migrateJob) {
		return new StepRepository(getSession(), migrateJob);
	}

	/**
	 * @param migrateAction
	 * @return
	 */
	protected Job getJob(MigrateBrickAction migrateAction) {
		return getJobRepository().show(migrateAction.getJob());
	}

	/**
	 * @return
	 */
	protected JobRepository getJobRepository() {
		return new JobRepository(getSession());
	}

	/**
	 * 
	 */
	protected void ensureNoGlusterVolumeTasks() {
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
	 * @param migrateJob
	 */
	protected void waitForMigrateToFinish(Job migrateJob) {
		Step removeBrickStep = removeBrickStep(migrateJob);
		WaitResult waitForMigrationStepFinish = new StepsRepositoryHelper().waitUntilStepStatus(new StepRepository(getSession(), migrateJob), removeBrickStep, "finished");
		Assert.assertTrue(waitForMigrationStepFinish.isSuccessful());
	}

	private Step removeBrickStep(Job migrateJob) {
		Step executingStep = new StepsRepositoryHelper().getExecutingStep(new StepRepository(getSession(), migrateJob));
		Step removeBrickStep = new StepsRepositoryHelper().getChildren(new StepRepository(getSession(), migrateJob), executingStep).get(0);
		return removeBrickStep;
	}

	protected void deleteAllDataFromVolume() {
		ExecSshSession mountedSession = ExecSshSession.fromHost(mounter);
		mountedSession.start();
		try{
			mountedSession.runCommandAndAssertSuccess("rm -rf " + mountPoint.add("*").toString());
			Assert.assertTrue(mountedSession.runCommandAndAssertSuccess("ls " + mountPoint.toString()).getStdout().isEmpty());
		}finally{
			mountedSession.stop();
		}
		
	}

	/**
	 * @return
	 */
	protected BrickRepository getBrickRepo() {
		return new BrickRepository(getSession(), volume.getCluster(), volume);
	}
	
	protected void startMigrationAndWaitTilFinish(ArrayList<Brick> bricks) {
		MigrateBrickAction migrateAction = getBrickRepo().migrate(bricks.get(0), bricks.get(1));

		Job migrateJob = getJob(migrateAction);
		waitForMigrateToFinish(migrateJob);
	}
}
