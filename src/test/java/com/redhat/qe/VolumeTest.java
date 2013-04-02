package com.redhat.qe;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
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

	private Host host1;
	private Host host2;

	@Before
	public void setup() {
		Cluster cluster = Cluster.fromResponse(new ClusterRepository(getShell()).createOrShow(Configuration.getConfiguration().getCluster()));
		
		Iterator<Host> hosts = Configuration.getConfiguration().getHosts().iterator();

		HostRepository hostRepository = new HostRepository(getShell());
		host1 =	hostRepository.createOrShow(hosts.next());
		host2 =	hostRepository.createOrShow(hosts.next());
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
