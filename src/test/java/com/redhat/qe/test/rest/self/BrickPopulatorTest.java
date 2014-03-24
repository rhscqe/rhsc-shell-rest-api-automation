package com.redhat.qe.test.rest.self;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.config.ConfiguredHosts;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.MountedVolume;
import com.redhat.qe.helpers.rebalance.PopulateEachBrickStrategy;
import com.redhat.qe.helpers.ssh.MountHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;
import com.twitter.mustache.TwitterObjectHandler;

public class BrickPopulatorTest extends TwoHostClusterTestBase{
	private Volume volume;
	private AbsolutePath mountPoint;
	private Host mounter;
	private MountedVolume mountedVolume;

	@Before
	public void beforeme(){
		VolumeRepository volumeRepo = getVolumeRepository();
		volume = volumeRepo.createOrShow(new VolumeFactory().distributed("red", getHost1(), getHost2()));
		volumeRepo._start(volume);

		mountPoint = AbsolutePath.fromDirs("mnt", volume.getName());
		mounter = RhscConfiguration.getConfiguration().getHosts().get(0);
		mountedVolume = MountHelper.mountVolume(mounter, mountPoint, volume);
		
		
	}
	
	@After
	public void afterme(){
		mountPoint.add("*");
		ExecSshSession.fromHost(mounter).withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				return session.runCommandAndAssertSuccess("rm -rf " + mountPoint.add("*").toString());
			}
		});
		MountHelper.unmount(mounter, mountPoint);
		getVolumeRepository().stop(volume);
		getVolumeRepository().destroy(volume);
	}
	
	/**
	 * @return
	 */
	private VolumeRepository getVolumeRepository() {
		VolumeRepository volumeRepo = getVolumeRepository(getHost1().getCluster());
		return volumeRepo;
	}

	@Test
	public void test(){
		ArrayList<Brick> bricks = new BrickRepository(getSession(), getHost1().getCluster(),volume).list();
		new PopulateEachBrickStrategy(bricks, new ConfiguredHosts(RhscConfiguration.getConfiguration().getHosts()), mountedVolume).populate();;
	}

}
