package com.redhat.qe.repository.rest;

import org.apache.http.client.methods.HttpRequestBase;

public class RequestHelper {
	
	
	//mutable
	public <T extends HttpRequestBase> T appendXmlRequestHeaders(T request){
		request.addHeader("Content-Type", "application/xml");
		request.addHeader("Accept", "application/xml");
		return request;
	}

}
