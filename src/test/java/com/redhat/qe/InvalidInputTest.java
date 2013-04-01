package com.redhat.qe;

import org.junit.Test;

public class InvalidInputTest extends TestBase{
	@Test
	public void test(){
		shell.send("whatever").expect("Unknown syntax: whatever");
		
	}

}
