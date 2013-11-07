package com.redhat.qe.test.ovirtshell;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.jcraft.jsch.ChannelShell;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.rhscshell.ClusterRepository;
import com.redhat.qe.repository.rhscshell.HostRepository;
import com.redhat.qe.repository.rhscshell.VolumeRepository;
import com.redhat.qe.ssh.ChannelSshSession;

public class SshSessionTestBase {

	protected ChannelSshSession session;


	@Before
	public void before() {
		Configuration config = RhscConfiguration.getConfiguration();
		session = ChannelSshSession.fromConfiguration(config);
		session.start();
		session.openChannel();
		ChannelShell channel = session.getChannel();
	}

	@After
	public void after() {
		if (session != null){
			session.stopChannel();
			session.stop();
		}
	}

}
