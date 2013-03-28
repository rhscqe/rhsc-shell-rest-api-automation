package com.redhat.qe;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.SshSession;

public class TestBase {

	protected static SshSession session;
	protected static RhscShell shell;

	/**
	 * @return the shell
	 */
	public static RhscShell getShell() {
		return shell;
	}

	@BeforeClass
	public static void before(){
		Configuration config = Configuration.getConfiguration();
		session = SshSession.fromConfiguration(config);
		session.start();
		shell = RhscShell.fromConfiguration(session, config);
		shell.start();
		shell.connect();
	}
	
	@AfterClass
	public static void after(){
		if(session != null)
			session.stop();
	}
}
