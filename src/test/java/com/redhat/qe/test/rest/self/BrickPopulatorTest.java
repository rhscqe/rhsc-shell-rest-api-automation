package com.redhat.qe.test.rest.self;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.ssh.MountHelper;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;
import com.twitter.mustache.TwitterObjectHandler;

public class BrickPopulatorTest extends TwoHostClusterTestBase{
	private Volume volume;
	private Path mountPoint;
	private Host mounter;

	@Before
	public void beforeme(){
		VolumeRepository volumeRepo = getVolumeRepository();
		volume = volumeRepo.createOrShow(VolumeFactory.distributed("red", getHost1(), getHost2()));
		volumeRepo._start(volume);

		mountPoint = Path.fromDirs("mnt", volume.getName());
		mounter = RhscConfiguration.getConfiguration().getHosts().get(0);
		MountHelper.mountVolume(mounter, mountPoint, volume);
		
		
	}
	
	@After
	public void afterme(){
		mountPoint.addDir("*");
		ExecSshSession.fromHost(mounter).withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				return session.runCommandAndAssertSuccess("rm -rf " + mountPoint.addDir("*").toString());
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
		new BrickPopulator().createDataForEachBrick(getSession(), getHost1().getCluster() ,volume, mounter, mountPoint);
	}

}
