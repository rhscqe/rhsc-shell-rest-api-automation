package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

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
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.SshSession;
import com.redhat.qe.test.rest.self.StepRepositoryTest;

import dstywho.functional.Predicate;

public class MigrateEndtoEndTest extends MigrateTestBase{
	private String md5sum;

	@Before
	public void storeVolumeCheckSums(){
		md5sum = getCheckSum();
	}
	
	public String getCheckSum(){
		ExecSshSession host1session = ExecSshSession.fromHost(mounter);
		host1session.start();
		try{
			return host1session.runCommandAndAssertSuccess("md5sum " + mountPoint.add("*").toString()).getStdout();
		}finally{
			host1session.stop();
		}
	}

	@Test
	@Tcms({"325559","325561"})
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		ArrayList<Brick> bricks = brickRepo.list();
		MigrateBrickAction migrateAction = brickRepo.migrate(bricks.get(0), bricks.get(1));
		Job migrateJob = getJob(migrateAction);

//		Assert.assertTrue("job finished",new JobRepoHelper().waitUntilJobFinished(getJobRepository(), migrateJob).isSuccessful());
		waitForMigrateToFinish(migrateJob);
		
		brickRepo.collectionDelete(DeletionBrickWrapperList.fromBricks(bricks.get(0),bricks.get(1)));
		
		Assert.assertEquals(md5sum,getCheckSum());
		
		getVolumeRepository().rebalance(volume);
		getVolumeRepository()._stopRebalance(volume);
		
		deleteAllDataFromVolume();
		
																										
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("migrateendtoend", 4, getHost1(), getHost2());
	}
}
