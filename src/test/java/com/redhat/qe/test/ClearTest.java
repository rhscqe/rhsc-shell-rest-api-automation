package com.redhat.qe.test;



import org.calgb.test.performance.html.RegexMatch;
import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;

public class ClearTest extends TestBase {


	@Tcms({"250540"})
	@Test
	public void clearTest() {
		Assert.assertTrue("screen was not cleared",new RegexMatch(shell.send("clear").getRaw()).find("\\033\\[J").size() > 0);
	}


}