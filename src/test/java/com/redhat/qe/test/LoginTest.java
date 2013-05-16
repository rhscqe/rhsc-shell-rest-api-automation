package com.redhat.qe.test;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class LoginTest extends ShellSessionTestBase{
	@Test
	@Tcms("250537")
	public void test(){
		shell.start();
		shell.connect()
				.expect(">>> connected to RHSC manager.*<<<")
				.expect("\\(connected\\)");
	}

}
