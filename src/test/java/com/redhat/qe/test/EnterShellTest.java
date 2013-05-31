package com.redhat.qe.test;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class EnterShellTest extends ShellSessionTestBase{
	@Test
	@Tcms("250459")
	public void test(){
		shell.send("rhsc-shell || ovirt-shell ").expect("Welcome to (RHSC|OVIRT) shell");
	}

}
