package com.redhat.qe.test.rest;

import java.util.ArrayList;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.google.common.base.Function;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.ssh.MountHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.gluster.Task;
import com.redhat.qe.model.gluster.VolumeStatusOutput;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;


public abstract class PopulatedVolumeTestBase extends TwoHostClusterTestBase {
	private static final Logger LOG = Logger.getLogger(PopulatedVolumeTestBase.class);

	protected AbsolutePath mountPoint;
	protected Host mounter;
	protected Volume volume;

	@Before
	public void setupVolume(){
		volume = createVolume();
		mountVolume();
		populateVolume();
	}

	private void populateVolume() {
		new BrickPopulator().createDataForEachBrick(getSession(), getHost1().getCluster(), volume, mounter, mountPoint);
		LOG.info("populating volume");
	}

	private void mountVolume() {
		mountPoint = AbsolutePath.fromDirs("mnt", volume.getName());
		mounter = RhscConfiguration.getConfiguration().getHosts().get(0);
		MountHelper.mountVolume(mounter, mountPoint, volume);
		LOG.info("volume mounted");
	}

	private Volume createVolume() {
		LOG.info("creating volume");
		VolumeRepository volumeRepo = getVolumeRepository();
		volume = volumeRepo.createOrShow(getVolumeToBeCreated());
		volumeRepo._start(volume);
		LOG.info("volume created:" + volume.getName());
		return volume;
	}

	 protected abstract Volume getVolumeToBeCreated() ;

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
			Host host= RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), brick.getHost()); 
			ExecSshSession.fromHost(host).withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
				
				public Response apply(ExecSshSession arg0) {
					return arg0.runCommandAndAssertSuccess("rm -rf " + brick.getDir());
				}
			});
		}
	}
	protected VolumeStatusOutput getGlusterVolumeStatus() {
		ExecSshSession glusterHostSshSession = ExecSshSession.fromHost( RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		glusterHostSshSession.start();
		try{
			return new VolumeXmlRepository( glusterHostSshSession).status(volume);
		}finally{
			glusterHostSshSession.stop();
		}
	}

	/**
	 * @return
	 */
	protected VolumeRepository getVolumeRepository() {
		VolumeRepository volumeRepo = getVolumeRepository(getHost1().getCluster());
		return volumeRepo;
	}
	

}
