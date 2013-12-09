package com.redhat.qe.repository.rest;

import org.apache.http.client.methods.HttpPost;
import org.calgb.test.performance.BuildPostException;

public class PostRequestFactory {
	
	public HttpPost createPost(String path,String payload){
		HttpPost post;
		try {
			post = new  org.calgb.test.performance.PostRequestFactory().buildPost(path,payload);
		} catch (BuildPostException e) {
			throw new RuntimeException(e);
		}
		return new RequestHelper().appendXmlRequestHeaders(post);
	}

	public HttpPost createPost(String path){
		HttpPost post;
		post = new HttpPost(); 
		return new RequestHelper().appendXmlRequestHeaders(post);
	}

}
