package com.redhat.qe.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.jcraft.jsch.ChannelShell;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.repository.HostRepository;
import com.redhat.qe.repository.VolumeRepository;
import com.redhat.qe.ssh.SshSession;

public class ShellSessionTestBase {

	protected SshSession session;
	protected RhscShellSession shell;


	@Before
	public void before() {
		Configuration config = RhscConfiguration.getConfiguration();
		session = SshSession.fromConfiguration(config);
		session.start();
		session.openChannel();
		ChannelShell channel = session.getChannel();
		shell = RhscShellSession.fromConfiguration(session, config);
	}

	@After
	public void after() {
		if (session != null){
			session.stopChannel();
			session.stop();
		}
	}

}
