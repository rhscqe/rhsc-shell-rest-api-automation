package com.redhat.qe.test;



import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

import dstywho.regexp.RegexMatch;

public class ClearTest extends OpenShellSessionTestBase {


	@Tcms({"250540"})
	@Test
	public void clearTest() {
		Assert.assertTrue("screen was not cleared",new RegexMatch(rhscSession.send("clear").getRaw()).find("\\033\\[J").size() > 0);
	}


}