package com.redhat.qe.test.ovirtshell;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.ssh.BashShell;

public class ExitTest extends RhscShellSessionTestBase{
	@Test
	@Tcms("250536")
	public void test(){
		BashShell.fromSsh(session).send("exit").expect("#");
	}


}
