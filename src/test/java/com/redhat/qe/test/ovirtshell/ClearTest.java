package com.redhat.qe.test.ovirtshell;



import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.ovirt.shell.RhscShellSession;

import dstywho.regexp.RegexMatch;

public class ClearTest extends RhscShellSessionTestBase {


	@Tcms({"250540"})
	@Test
	public void clearTest() {
		Assert.assertTrue("screen was not cleared",new RegexMatch(getShell().sendAndRead("clear").getRaw()).find("\\033\\[J").size() > 0);
	}


}