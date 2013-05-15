package com.redhat.qe.test;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class LoginTest extends ShellSessionTestBase{
	@Test
	@Tcms("250459")
	public void test(){
		shell.send("rhsc-shell").expect("Welcome to RHSC shell(.|\n|\r)*disconnected");
	}

}
