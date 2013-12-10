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
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.model.WaitUtil.WaitResult;
import com.redhat.qe.model.gluster.Task;
import com.redhat.qe.model.gluster.VolumeStatusOutput;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

import dstywho.functional.Predicate;
import dstywho.timeout.Duration;


public abstract class PopulatedVolumeTestBase extends VolumeTestBase {
	private static final Logger LOG = Logger.getLogger(PopulatedVolumeTestBase.class);

	protected AbsolutePath mountPoint;
	protected Host mounter;

	
	@Before public void mountAndPopulate(){
		if(getVolumeRepository().show(volume).getStatus().equals("down"))
			getVolumeRepository().start(volume);
		mountVolume();
		populateVolume();
	}

	protected void populateVolume() {
		LOG.info("populating volume");
		new BrickPopulator().createDataForEachBrick(getSession(), getHost1().getCluster(), volume, mounter, mountPoint);
		LOG.info("populated volume");
	}

	private void mountVolume() {
		LOG.info("mounting volume");
		mountPoint = AbsolutePath.fromDirs("mnt", volume.getName());
		mounter = RhscConfiguration.getConfiguration().getHosts().get(0);
		MountHelper.mountVolume(mounter, mountPoint, volume);
		LOG.info("volume mounted");
	}


	 protected abstract Volume getVolumeToBeCreated() ;

	/**
	 * @return
	 */
	private BrickRepository getBrickRepo() {
		return new BrickRepository(getSession(), getHost1().getCluster(), volume);
	}
	
	private WaitResult stopVolume(){
		return WaitUtil.waitUntil(new Predicate() {

			@Override
			public Boolean act() {
				return getVolumeRepository()._stop(volume).isCodeSimilar(200);
			}

		}, 3);
	}
	
	@Override
	@After
	public void destroyVolume(){
		MountHelper.unmount(mounter, mountPoint);
		ArrayList<Brick> bricks = getBrickRepo().list();
		printGlusterVolStatusFromANode();
		if(getVolumeRepository().show(volume).getStatus().equalsIgnoreCase("up"))
			Assert.assertTrue("volume could not be stopped" ,stopVolume().isSuccessful());
		getVolumeRepository().destroy(volume);
		cleanUpBrickData(bricks);
	}

	private void printGlusterVolStatusFromANode() {
		ExecSshSession sshsession = ExecSshSession.fromHost(getHost1ToBeCreated());
		sshsession.start();
		try{
			LOG.error( sshsession.runCommand("gluster vol status").getStdout());
		}finally{
		 sshsession.stop();	
		}
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

	

}
