package com.redhat.qe.rest;

import org.apache.http.client.methods.HttpGet;
import org.calgb.test.performance.HttpSession;
import org.calgb.test.performance.HttpSession.HttpProtocol;
import org.calgb.test.performance.ProcessResponseBodyException;
import org.calgb.test.performance.RequestException;
import org.calgb.test.performance.UseSslException;
import org.junit.Test;

public class RsdlTest {
	
	@Test
	public void test() throws UseSslException, ProcessResponseBodyException, RequestException{
		HttpSession session = new HttpSession("rhsc-qa9", 443, HttpProtocol.HTTPS);
		session.useBasicAuthentication("admin@internal", "redhat");
		session.sendTransaction(new HttpGet("/api?rsdl"));
		
	}

}
