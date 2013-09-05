package com.redhat.qe.test;

import org.junit.Test;


public class InvalidInputTest extends OpenShellSessionTestBase{
	@Test
	public void test(){
		rhscSession.send("whatever").expect("Unknown syntax: whatever");
	}

}
