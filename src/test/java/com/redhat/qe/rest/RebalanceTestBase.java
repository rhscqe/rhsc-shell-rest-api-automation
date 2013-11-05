package com.redhat.qe.rest;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.MountHelper;
import com.redhat.qe.helpers.Path;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;


public class RebalanceTestBase extends TwoHostClusterTestBase {
	private static final Logger LOG = Logger.getLogger(RebalanceTestBase.class);

	private Path mountPoint;
	private Host mounter;
	protected Volume volume;

	@Before
	public void setupVolume(){
		LOG.info("creating volume");
		VolumeRepository volumeRepo = getVolumeRepository();
		volume = volumeRepo.createOrShow(VolumeFactory.distributed("purplehat", getHost1(), getHost2()));
		volumeRepo._start(volume);
		LOG.info("volume created:" + volume.getName());



		mountPoint = Path.fromDirs("mnt", volume.getName());
		mounter = RhscConfiguration.getConfiguration().getHosts().get(0);
		MountHelper.mountVolume(mounter, mountPoint, volume);
		LOG.info("volume mounted");
		
		new BrickPopulator().createDataForEachBrick(getSession(), getHost1().getCluster(), volume, mounter, mountPoint);
		LOG.info("populating volume");
		
		
		getBrickRepo().create(BrickFactory.brick(getHost2()));
		getBrickRepo().create(BrickFactory.brick(getHost2()));
		LOG.info("2 bricks added");
		LOG.info("end of RebalanceTestBase fixture");
	}

	/**
	 * @return
	 */
	private BrickRepository getBrickRepo() {
		return new BrickRepository(getSession(), getHost1().getCluster(), volume);
	}
	
	@After
	public void afterme(){
		MountHelper.unmount(mounter, mountPoint);
		ArrayList<Brick> bricks = getBrickRepo().list();
		getVolumeRepository().stop(volume);
		getVolumeRepository().destroy(volume);
		cleanUpBrickData(bricks);
	}
	/**
	 * @param bricks
	 */
	private void cleanUpBrickData(ArrayList<Brick> bricks) {
		for(final Brick brick: bricks){
			Host host = brick.getConfiguredHostFromBrickHost(getSession());
			ExecSshSession.fromHost(host).withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
				
				public Response apply(ExecSshSession arg0) {
					return arg0.runCommandAndAssertSuccess("rm -rf " + brick.getDir());
				}
			});
		}
	}
	

	/**
	 * @return
	 */
	private VolumeRepository getVolumeRepository() {
		VolumeRepository volumeRepo = getVolumeRepository(getHost1().getCluster());
		return volumeRepo;
	}
}
