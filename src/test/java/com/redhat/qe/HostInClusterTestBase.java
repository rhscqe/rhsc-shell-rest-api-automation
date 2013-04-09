package com.redhat.qe;

import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.ClusterRepository;

public class HostInClusterTestBase extends TestBase {

	protected static Host host1;
	protected static Host host2;

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

	public HostInClusterTestBase() {
		super();
	}

}