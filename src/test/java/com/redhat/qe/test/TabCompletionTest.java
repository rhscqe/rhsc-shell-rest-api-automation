package com.redhat.qe.test;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.redhat.qe.annoations.Tcms;

public class TabCompletionTest extends OpenShellSessionTestBase{
	@Test
	@Tcms("250460")
	public void test(){
		String[] commands = new String[]{"EOF", "connect", "exit", "help"};
		rhscSession.send("\t\t").expect(Joiner.on("(\n|\r|.)*").join(commands));
	}

}
