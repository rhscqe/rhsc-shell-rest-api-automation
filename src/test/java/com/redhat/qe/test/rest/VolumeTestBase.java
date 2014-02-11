package com.redhat.qe.test.rest;

import java.util.ArrayList;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.google.common.base.Function;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.exceptions.UnexpectedReponseWrapperException;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.ssh.MountHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.GeneralAction;
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

import dstywho.timeout.Duration;


public abstract class VolumeTestBase extends TwoHostClusterTestBase {
	private static final Logger LOG = Logger.getLogger(VolumeTestBase.class);

	protected AbsolutePath mountPoint;
	protected Host mounter;
	protected Volume volume;

	@Before
	public void setupVolumeAndStart(){
		volume = createVolume();
	}



	 protected abstract Volume getVolumeToBeCreated() ;

	/**
	 * @return
	 */
	private BrickRepository getBrickRepo() {
		return new BrickRepository(getSession(), getHost1().getCluster(), volume);
	}
	
	@After
	public void destroyVolume(){
		if(volume != null && volume.getId() != null)
			getVolumeRepository().destroy(volume);
	}
	
	
	private Volume createVolume() {
		LOG.info("creating volume");
		VolumeRepository volumeRepo = getVolumeRepository();
		volume = volumeRepo.createOrShow(getVolumeToBeCreated());
		return volume;
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
