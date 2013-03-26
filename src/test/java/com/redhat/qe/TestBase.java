package com.redhat.qe;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.SshSession;

public class TestBase {

	private static SshSession session;
	private static RhscShell shell;

	@BeforeClass
	public static void before(){
		session = new SshSession(new Credentials("root", "redhat"), "rhsc-qa8");
		session.start();
		shell = new RhscShell(session, "https://localhost:443/api", new Credentials("admin@internal", "redhat"));
		shell.start();
		shell.connect();
	}
	
	@AfterClass
	public static void after(){
		session.stop();
	}
}
