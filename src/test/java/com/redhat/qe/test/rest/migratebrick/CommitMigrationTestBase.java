package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
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

public class CommitMigrationTestBase extends MigrateTestBase {

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributed("startbriciikmigrate", 4, getHost1(),
				getHost2());
	}

	@Test
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		final ArrayList<Brick> bricks = brickRepo.list();
		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(0), bricks.get(1));
		Job migrateJob = getJob(migrateAction);

		validateJobAndStepsStarted(migrateJob);
//		Assert.assertTrue(new JobRepoHelper().waitUntilJobFinished(getJobRepository(), migrateJob).isSuccessful());
		
		brickRepo.delete(bricks.get(0));

		ArrayList<Brick> bricksAfterDeletion = brickRepo.list();
		Assert.assertTrue(Collections2.filter(bricksAfterDeletion, new Predicate<Brick>() {

			public boolean apply(Brick paramT) {
				// TODO Auto-generated method stub
				return paramT.getDir().equals(bricks.get(0).getDir());
			}
		}).size() == 0);

	}

}
