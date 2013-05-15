package com.redhat.qe.test;


import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class PingTest extends OpenShellSessionTestBase {


	@Tcms({"250541"})
	@Test
	public void pingTest() {
		shell.send("ping").expect("(?i)success: oVirt manager could be reached OK.");
	}


}