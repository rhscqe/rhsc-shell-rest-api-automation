package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.jaxb.DeletionBrickWrapperList;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class RemoveBrickDuringMigrationTest extends MigrateTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("RemoveBrickDuringMigrationTest",4, getHost1(), getHost2());
	}
	
	@Override
	protected void populateVolume() {
		new BrickPopulator(FileSize.megaBytes(500)).createDataForEachBrick(getSession(), getHost1().getCluster(), volume, mounter, mountPoint);
	}	

	@Test
	@Tcms({"318700"})
	@Ignore //test case disabled
	public void test(){
		BrickRepository brickRepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		
		ArrayList<Brick> bricks = brickRepo.list();
		
		ArrayList<Brick> bricksToMigrate = new ArrayList<Brick>(bricks);
		bricksToMigrate.remove(bricks.get(3));
		bricksToMigrate.remove(bricks.get(2));
		MigrateBrickAction migrateAction = brickRepo.migrate(bricksToMigrate.toArray(new Brick[0]));

		try{
		
			ArrayList<Brick> bricksToDelete = new ArrayList<Brick>(bricks);
			bricksToDelete.remove(bricks.get(3));
			bricksToDelete.remove(bricks.get(2));
			getGlusterVolumeStatus2();
			ResponseWrapper response = brickRepo._collectionDelete(DeletionBrickWrapperList.fromBricks(bricksToDelete.toArray(new Brick[0])));
			try{
				response.expectSimilarCode(400);
			}finally{
				getGlusterVolumeStatus2();
			}
		}finally{
			brickRepo.stopMigrate(bricksToMigrate.toArray(new Brick[0]));
		}

	}

	private void getGlusterVolumeStatus2() {
		ExecSshSession hostsession = ExecSshSession.fromHost(getHost1ToBeCreated());
		hostsession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				return session.runCommandAndAssertSuccess("gluster vol status");
			}
		});
	}


}
