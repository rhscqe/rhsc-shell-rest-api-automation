package com.redhat.qe.test;

import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.helpers.HostCleanup;
import com.redhat.qe.helpers.cleanup.CleanupTool;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.rhscshell.ClusterRepository;
import com.redhat.qe.repository.rhscshell.VolumeRepository;
import com.redhat.qe.ssh.Response;

import dstywho.timeout.Duration;

public class TwoHostClusterTestBase extends RhscShellSessionTestBase {

	protected Host host1;
	protected Host host2;
	protected Cluster cluster;

	@Before
	public void setup() {
		cluster = new ClusterRepository(getShell()).createOrShow(RhscConfiguration.getConfiguration().getCluster());
		Iterator<Host> hosts = RhscConfiguration.getConfiguration().getHosts().iterator();
	
		host1 = getHostRepository().createOrShow(hosts.next());
		Assert.assertTrue(String.format("host did not come up; host is in ", host1.toJson()),
				WaitUtil.waitForHostStatus(getHostRepository(), host1, "up", 400));
		host2 = getHostRepository().createOrShow(hosts.next());
		Assert.assertTrue(String.format("host did not come up; host is in ", host2.toJson()),
				WaitUtil.waitForHostStatus(getHostRepository(), host2, "up", 400));
		;
	}

	@After
	public void teardown() {
		new CleanupTool().cleanup(RhscConfiguration.getConfiguration());
	}

	/**
	 * @param hostRepository
	 */
	private void destroyHost(Host host) {
		HostCleanup.destroyHost(host, getHostRepository());
	}

	public TwoHostClusterTestBase() {
		super();
	}
	
	protected VolumeRepository getVolumeRepository() {
		return getVolumeRepository(cluster);
	}
	

}