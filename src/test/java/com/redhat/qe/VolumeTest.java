package com.redhat.qe;

import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.ClusterRepository;

public class VolumeTest extends TestBase {

	private static Host host1;
	private static Host host2;

	@BeforeClass
	public static void setup() {
		new ClusterRepository(getShell()).createOrShow(Configuration.getConfiguration().getCluster());
		Iterator<Host> hosts = Configuration.getConfiguration().getHosts().iterator();

		host1 = getHostRepository().createOrShow(hosts.next());
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host1, "up", 400));
		host2 = getHostRepository().createOrShow(hosts.next());
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host2, "up", 400));
	}

	@AfterClass
	public static void teardown() {
		destroyHost( host1);
		destroyHost( host2);
	}

	/**
	 * @param hostRepository
	 */
	private static void destroyHost(Host host) {
		if (host != null) {
			getHostRepository().deactivate(host);
			Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host, "maintenance", 400));
			getHostRepository().destroy(host);
		}
	}

	@Test
	public void distributedVolumeTest() {
		Volume volume = VolumeFactory.distributed("mydistvolume", host1, host2);
		volume = getVolumeRepository().create(volume);
		getVolumeRepository().destroy(volume);

	}


	@Test
	public void replicateVolumeTest() {
		Volume volume = VolumeFactory.replicate("myreplicateVol", host1,host2);
		volume = getVolumeRepository().create(volume);
		getVolumeRepository().destroy(volume);
	}

}
