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
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.repository.JobRepoHelper;
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

public class MigrateAfterMigrateCompleteTest extends MigrateTestBase {

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("migrateAfterMigration", 4, getHost1(),
				getHost2());
	}
	
	@Override
	protected void populateVolume() {
		//dont populate
	}

	@Test
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		ArrayList<Brick> bricks = brickRepo.list();
		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(0), bricks.get(1));
		Job migrateJob = getJob(migrateAction);

		validateJobAndStepsStarted(migrateJob);
//TODO		Assert.assertTrue(new JobRepoHelper().waitUntilJobFinished(getJobRepository(), migrateJob).isSuccessful());
		
		ResponseWrapper secondMigrationResponse = brickRepo._migrate(bricks.get(2));
		secondMigrationResponse.expectSimilarCode(400);
		secondMigrationResponse.expect("task is in progress on the volume");
	}


}
