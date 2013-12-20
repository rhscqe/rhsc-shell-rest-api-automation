package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.repository.JobRepoHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.model.jaxb.DeletionBrickWrapperList;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.SshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;
import com.redhat.qe.test.rest.self.StepRepositoryTest;

import dstywho.functional.Predicate;
import dstywho.timeout.Timeout;

public class CommitPartialMigrationTest extends MigrateTestBase{


	@Test
	@Tcms("330101")
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		ArrayList<Brick> bricks = brickRepo.list();
		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(0), bricks.get(1));
		Job migrateJob = getJob(migrateAction);

//		Assert.assertTrue("job finished",new JobRepoHelper().waitUntilJobFinished(getJobRepository(), migrateJob).isSuccessful());
		waitForMigrateToFinish(migrateJob);
		
		ResponseWrapper response = brickRepo._collectionDelete(DeletionBrickWrapperList.fromBricks(bricks.get(0)));
		response.expectSimilarCode(400);
		response.expect("bricks does not match with the bricks used while starting the action");
																										
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("CommitPartialMigrationTest", 4, getHost1(), getHost2());
	}
}
