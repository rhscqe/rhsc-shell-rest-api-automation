package com.redhat.qe.helpers;

import org.calgb.test.performance.HttpSession;
import org.calgb.test.performance.HttpSession.HttpProtocol;
import org.calgb.test.performance.UseSslException;

import com.redhat.qe.config.RestApi;

public class HttpSessionFactory {
	
	public HttpSession createHttpSession(RestApi restApi){
		HttpSession session;
		try {
			session = new HttpSession(restApi.getHostname(),443, HttpProtocol.HTTPS);
		} catch (UseSslException e) {
			throw new RuntimeException(e);
		}
		session.useBasicAuthentication(restApi.getCredentials().getUsername(), restApi.getCredentials().getPassword());
		return session;
		
	}

}
