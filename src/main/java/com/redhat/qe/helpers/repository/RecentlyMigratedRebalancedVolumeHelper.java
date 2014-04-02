package com.redhat.qe.helpers.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import junit.framework.Assert;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.ssh.MountedVolumeHelper;
import com.redhat.qe.helpers.ssh.RebalanceProcessHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;

public class RecentlyMigratedRebalancedVolumeHelper {
	private static final Logger LOG = Logger.getLogger(RecentlyMigratedRebalancedVolumeHelper.class);
	
	private void printGlusterVolStatus(Host mounter) {
		ExecSshSession sshsession = ExecSshSession.fromHost(mounter);
		sshsession.start();
		try{
			LOG.error(  new com.redhat.qe.repository.glustercli.VolumeRepository(sshsession).info());
		}finally{
		 sshsession.stop();	
		}
	}
	
	public void stopVolume(VolumeRepository repo, Volume volume, List<Host> hosts ){
		new RebalanceProcessHelper().waitForRebalanceProcessToTerminate(hosts);
		if(repo.show(volume).getStatus().equalsIgnoreCase("up"))
			Assert.assertTrue(new VolumeRepositoryHelper().stopVolume(repo, volume).getResult().isSuccessful());
	}
	
	public void destroyVolume(VolumeRepository repo, Volume volume, List<Host> hosts ){
		ArrayList<Brick> bricks = new BrickRepository(repo.getSession(), repo.getCluster(), volume).list();
		repo.destroy(volume);
		new BrickDirectoryDestroyer().destroyBrickDirectories(repo, hosts, bricks);
	}
}
