package com.redhat.qe.test;

import junit.framework.Assert;

import org.apache.commons.io.output.NullOutputStream;
import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.rhscshell.ClusterRepository;
import com.redhat.qe.repository.rhscshell.HostRepository;
import com.redhat.qe.repository.rhscshell.VolumeRepository;
import com.redhat.qe.ssh.BashShell;

public class RhscShellSessionTestBase extends SshSessionTestBase{

	private HostRepository hostRepository;
	private VolumeRepository volumeRepository;
	private ClusterRepository clusterRepository;
	private RhscShellSession rhscSession;

	/**
	 * @return the shell
	 */
	public RhscShellSession getShell() {
		return rhscSession;
	}

	@Before
	public void beforeSession() {
		rhscSession = RhscShellSession.fromConfiguration(session, RhscConfiguration.getConfiguration());
		Assert.assertTrue("bash prompt available",BashShell.fromShell(rhscSession.getShell()).waitForPrompt());
		rhscSession.start();
		rhscSession.connect();
	}

	public void clearState() {
		if (rhscSession != null)
			rhscSession = null;
		if (session != null)
			session = null;
		if (hostRepository != null)
			hostRepository = null;
		if (clusterRepository != null)
			clusterRepository = null;
		if (volumeRepository != null)
			volumeRepository = null;
	}

	public HostRepository getHostRepository() {
		if (hostRepository == null) {
			hostRepository = new HostRepository(getShell());
		}
		return hostRepository;
	}

	public VolumeRepository getVolumeRepository(Cluster cluster) {
		if (volumeRepository == null) {
			volumeRepository = new VolumeRepository(getShell(),cluster);
		}
		return volumeRepository;
	}

	public ClusterRepository getClusterRepository() {
		if (clusterRepository == null) {
			clusterRepository = new ClusterRepository(getShell());
		}
		return clusterRepository;

	}
}
