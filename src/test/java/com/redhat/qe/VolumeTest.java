package com.redhat.qe;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.BrickFactory;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.ClusterFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.HostFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.repository.HostRepository;
import com.redhat.qe.repository.VolumeRepository;

public class VolumeTest extends TestBase {

	private static Host host1;
	private static Host host2;

	@BeforeClass
	public static void setup() {
		new ClusterRepository(getShell()).createOrShow(Configuration.getConfiguration().getCluster());
		Iterator<Host> hosts = Configuration.getConfiguration().getHosts().iterator();

		HostRepository hostRepository = new HostRepository(getShell());
		host1 =	hostRepository.createOrShow(hosts.next());
		Assert.assertTrue(WaitUtil.waitForHostStatus(hostRepository, host1,"up", 400));
		host2 =	hostRepository.createOrShow(hosts.next());
		Assert.assertTrue(WaitUtil.waitForHostStatus(hostRepository, host2,"up", 400));
	}
	
	@AfterClass
	public static void teardown(){
//		HostRepository hostRepository = new HostRepository(getShell());
//		destroyHost(hostRepository,host1);
//		destroyHost(hostRepository,host2);
	}

	/**
	 * @param hostRepository
	 */
	private static void destroyHost(HostRepository hostRepository, Host host) {
		hostRepository.deactivate(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(hostRepository, host,"maintenance", 400));
		hostRepository.destroy(host);
	}

	@Test
	public void distributedVolumeTest() {
		Volume volume = new Volume();
		volume.setName("myVol");
		volume.setType("distribute");
		volume.setCluster(host1.getCluster());

		ArrayList<Brick> bricks = new ArrayList<Brick>();
		bricks.add(BrickFactory.brick(host1));
		bricks.add(BrickFactory.brick(host1));
		bricks.add(BrickFactory.brick(host1));
		bricks.add(BrickFactory.brick(host2));
		bricks.add(BrickFactory.brick(host2));
		bricks.add(BrickFactory.brick(host2));
		volume.setBricks(bricks);
		VolumeRepository repo = new VolumeRepository(getShell());
		volume = repo.create(volume);
		repo.destroy(volume);

	}

	@Test
	public void replicateVolumeTest() {
		Volume volume = new Volume();
		volume.setName("myVol");
		volume.setType("replicate");
		volume.setReplicaCount(8);
		volume.setCluster(host1.getCluster());
		for (int _ : new int[4])
			volume.getBricks().add(BrickFactory.brick(host1));
		for (int _ : new int[4])
			volume.getBricks().add(BrickFactory.brick(host2));
		VolumeRepository repo = new VolumeRepository(getShell());
		volume = repo.create(volume);
		repo.destroy(volume);

	}

}
