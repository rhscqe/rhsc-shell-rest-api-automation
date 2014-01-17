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
import com.redhat.qe.helpers.repository.MigrateStepsRepositoryHelper;
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

public class StartMigrateMultipleBrickTest extends MigrateTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("StartMigrateMultipleBrickTest",4, getHost1(), getHost2());
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
	public void testRestStartedStatus(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		ArrayList<Brick> bricks = brickRepo.list();
		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(0), bricks.get(1));
		Job migrateJob = getJob(migrateAction);

		validateJobAndStepsStarted(migrateJob);
		try{
			waitForMigrateToFinish(migrateJob);
		}finally{
			brickRepo._stopMigrate(bricks.get(0), bricks.get(1));
			Assert.assertTrue(new MigrateStepsRepositoryHelper().waitForMigrateToMatch(getSession(), migrateAction.getJob(), "(?i)finished|failed|aborted").isSuccessful());
		}
	}

	@Tcms("318702")
	@Test
	public void testGlusterCliStatus(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		ArrayList<Brick> bricks = brickRepo.list();
		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(0), bricks.get(1));
		try{
			ExecSshSession host1Session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
			host1Session.start();
			try{
				VolumeStatusOutput volStatus = new VolumeXmlRepository(host1Session).status(volume);
				Assert.assertTrue(volStatus.getTasks().size() == 1);
				Asserts.assertEqualsIgnoreCase("remove brick",volStatus.getTasks().get(0).getType());
				Asserts.assertEqualsIgnoreCase("in progress",volStatus.getTasks().get(0).getStatusStr());
			}finally{
				host1Session.stop();
			}
		}finally{
			brickRepo._stopMigrate(bricks.get(0), bricks.get(1));
			Assert.assertTrue(new MigrateStepsRepositoryHelper().waitForMigrateToMatch(getSession(), migrateAction.getJob(), "(?i)finished|failed|aborted").isSuccessful());
		}
	}

	public static void main(String[] args ){
		System.out.println("ABORTED".matches(("(?i)aborted|finished")));
	}

}
