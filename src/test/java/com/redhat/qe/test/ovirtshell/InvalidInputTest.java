package com.redhat.qe.test.ovirtshell;

import org.junit.Test;


public class InvalidInputTest extends RhscShellSessionTestBase{
	@Test
	public void test(){
		getShell().sendAndCollect("whatever").expect("Unknown syntax: whatever");
	}

}
