package com.redhat.qe.test;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.ovirt.shell.RhscShellSession;

public class LoginTest extends SshSessionTestBase{
	@Test
	@Tcms("250537")
	public void test(){
		RhscShellSession rhscSession = RhscShellSession.fromConfiguration(session);
		rhscSession.start();
		rhscSession.connect()
				.expect(">>> connected to (RHSC|OVIRT) manager.*<<<")
				.expect("\\(connected\\)");
	}

}
