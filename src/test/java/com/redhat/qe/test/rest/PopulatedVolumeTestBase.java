package com.redhat.qe.test.rest;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.google.common.base.Function;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.helpers.MountedVolume;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.repository.RecentlyMigratedRebalancedVolumeHelper;
import com.redhat.qe.helpers.ssh.MountHelper;
import com.redhat.qe.helpers.ssh.MountedVolumeHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;


public abstract class PopulatedVolumeTestBase extends VolumeTestBase {
	private static final Logger LOG = Logger.getLogger(PopulatedVolumeTestBase.class);

	private MountedVolume mountedVolume;

	
	@Before 
	public void mountAndPopulate(){
		if(getVolumeRepository().show(volume).getStatus().equals("down"))
			getVolumeRepository().start(volume);
		mountVolume();
		populateVolume();
	}

	protected void cleanVolumeUp(){
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				return session.runCommandAndAssertSuccess("rm -rf " + mountPoint.add("*").toString() );
			}
			
		});
			
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
		mountedVolume = MountHelper.mountVolume(mounter, mountPoint, volume);
		LOG.info("volume mounted");
	}


	 protected abstract Volume getVolumeToBeCreated() ;

	
	@Override
	@After
	public void destroyVolume(){
		try{
			if(volume != null && volume.getId() != null){
				new MountedVolumeHelper().cleanupMountedVolume(getVolumeRepository(), mountedVolume);
				MountHelper.unmount(mounter, mountPoint);
				printGlusterVolStatusFromANode();
			
				new RecentlyMigratedRebalancedVolumeHelper().stopVolume(getVolumeRepository(), volume, RhscConfiguration.getConfiguration().getHosts());
				new RecentlyMigratedRebalancedVolumeHelper().destroyVolume(getVolumeRepository(), volume,  RhscConfiguration.getConfiguration().getHosts());
			}
		}finally{
			cleanUpAllBrickData();
		}
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

	private void cleanUpAllBrickData() {
		for(final Host host : RhscConfiguration.getConfiguration().getHosts()){
			ExecSshSession.fromHost(host).withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
				public Response apply(ExecSshSession arg0) {
					return arg0.runCommandAndAssertSuccess("rm -rf " + new BrickFactory().getBaseDir().add("*"));
				}
			});
		}
	}

	

}
