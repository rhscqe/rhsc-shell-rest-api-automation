package com.redhat.qe.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.repository.HostRepository;
import com.redhat.qe.repository.VolumeRepository;
import com.redhat.qe.ssh.SshSession;

public class TestBase {

	protected static SshSession session;
	protected static RhscShell shell;
	private static HostRepository hostRepository;
	private static VolumeRepository volumeRepository;
	private static ClusterRepository clusterRepository;

	/**
	 * @return the shell
	 */
	public static RhscShell getShell() {
		return shell;
	}

	@BeforeClass
	public synchronized static void before() {
		Configuration config = RhscConfiguration.getConfiguration();
		session = SshSession.fromConfiguration(config);
		session.start();
		session.openChannel();
		shell = RhscShell.fromConfiguration(session, config);
		shell.start();
		shell.connect();
	}

	@AfterClass
	public synchronized static void after() {
		if (session != null){
			session.stopChannel();
			session.stop();
		}
		clearState();
	}

	public synchronized static void clearState() {
		if (shell != null)
			shell = null;
		if (session != null)
			session = null;
		if (hostRepository != null)
			hostRepository = null;
		if (clusterRepository != null)
			clusterRepository = null;
		if (volumeRepository != null)
			volumeRepository = null;
	}

	public synchronized static HostRepository getHostRepository() {
		if (hostRepository == null) {
			hostRepository = new HostRepository(getShell());
		}
		return hostRepository;
	}

	public synchronized static VolumeRepository getVolumeRepository() {
		if (volumeRepository == null) {
			volumeRepository = new VolumeRepository(getShell());
		}
		return volumeRepository;
	}

	public synchronized static ClusterRepository getClusterRepository() {
		if (clusterRepository == null) {
			clusterRepository = new ClusterRepository(getShell());
		}
		return clusterRepository;

	}
}
