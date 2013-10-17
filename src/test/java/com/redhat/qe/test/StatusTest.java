package com.redhat.qe.test;


import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class StatusTest extends RhscShellSessionTestBase {


	@Tcms({"250544"})
	@Test
	public void statusTest() {
		getShell().send("status").expect("last command status:\\s*0.*(OK)");
	}


}