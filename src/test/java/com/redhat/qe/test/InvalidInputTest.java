package com.redhat.qe.test;

import org.junit.Test;


public class InvalidInputTest extends TestBase{
	@Test
	public void test(){
		shell.send("whatever").expect("Unknown syntax: whatever");
	}

}
