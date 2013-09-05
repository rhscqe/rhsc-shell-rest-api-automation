package com.redhat.qe.test;


import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class HelpTest extends OpenShellSessionTestBase {


	@Tcms({"250461"})
	@Test
	public void helpTest() {
		rhscSession.send("help").unexpect("(?i)rhev");
	}


}