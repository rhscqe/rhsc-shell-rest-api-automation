package com.redhat.qe.repository.rest;

import org.apache.http.client.methods.HttpGet;

public class HttpGetRequestFactory {
	
	public HttpGet create(String path){
		HttpGet request = new HttpGet(path);
		return new RequestHelper().appendXmlRequestHeaders(request);
	}

}
