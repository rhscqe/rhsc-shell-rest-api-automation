package com.redhat.qe.test.ovirtshell;


import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class StatusTest extends RhscShellSessionTestBase {


	@Tcms({"250544"})
	@Test
	public void statusTest() {
		getShell().sendAndCollect("status").expect("last command status:\\s*0.*(OK)");
	}


}