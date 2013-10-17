package com.redhat.qe.test;


import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class PingTest extends RhscShellSessionTestBase {


	@Tcms({"250541"})
	@Test
	public void pingTest() {
		getShell().send("ping").expect("(?i)success:.*could be reached OK.");
	}


}