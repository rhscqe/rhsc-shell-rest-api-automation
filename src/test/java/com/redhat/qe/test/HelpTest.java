package com.redhat.qe.test;


import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class HelpTest extends RhscShellSessionTestBase {


	@Tcms({"250461"})
	@Test
	public void helpTest() {
		getShell().send("help").unexpect("(?i)rhev");
	}


}