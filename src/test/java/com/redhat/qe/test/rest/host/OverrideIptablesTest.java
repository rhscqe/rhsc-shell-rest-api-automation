package com.redhat.qe.test.rest.host;

import org.junit.After;
import org.junit.Test;

import junit.framework.Assert;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.cleanup.RestCleanupTool;
import com.redhat.qe.helpers.repository.HostHelper;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.rest.HostRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.ClusterTestBase;

import dstywho.regexp.RegexMatch;

public class OverrideIptablesTest extends ClusterTestBase {
	public static class Socket {
		private int port;
		private String protocol;

		/**
		 * @param port
		 * @param protocol
		 */
		public Socket(int port, String protocol) {
			super();
			this.port = port;
			this.protocol = protocol;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

	}

	private Socket[] sockets = new Socket[] { new Socket(2049, "tcp"),
			new Socket(38465, "tcp"), new Socket(38466, "tcp"),
			new Socket(38468, "tcp"), new Socket(38469, "tcp"),
			new Socket(24007, "tcp"), new Socket(22, "tcp"),
			new Socket(111, "tcp"), new Socket(22, "tcp"),
			new Socket(8080, "tcp"),  new Socket(111, "tcp"),
			new Socket(111, "udp") };

	@Test
	@Tcms("326708")
	public void test() {
		Host host = RhscConfiguration.getConfiguration().getHosts().get(0);
		host.setOverideIptables(true);
		host = new HostRepository(getSession()).create(host);
		new HostHelper().createAndWaitForUp(getHostRepository(), host);
		Assert.assertTrue("host came up",
				WaitUtil.waitForHostStatus(getHostRepository(), host, "up", 30));

		ExecSshSession hostSsh = ExecSshSession.fromHost(RhscConfiguration
				.getConfiguredHostFromBrickHost(getSession(), host));
		hostSsh.start();
		try {
			String iptablerules = hostSsh.runCommand("service iptables status")
					.getStdout();
			for (Socket socket : sockets) {
				int matches = new RegexMatch(iptablerules).find(
						String.format("%s.*%s", socket.getProtocol(),
								socket.getPort())).size();
				Assert.assertTrue("could not find " + socket.getProtocol()
						+ ":" + socket.getPort(), matches > 0);
			}

		} finally {
			hostSsh.stop();
		}

	}

	@After
	public void cleanup() {
		new RestCleanupTool().cleanup(RhscConfiguration.getConfiguration());
	}

	@Override
	public Cluster getClusterToBeCreated() {
		return RhscConfiguration.getConfiguration().getCluster();
	}

}
