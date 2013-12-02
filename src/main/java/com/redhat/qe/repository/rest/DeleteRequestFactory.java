package com.redhat.qe.repository.rest;

import org.calgb.test.performance.BuildPostException;
import org.calgb.test.performance.HttpDeleteWithBody;

public class DeleteRequestFactory {
	
	public HttpDeleteWithBody create(String path,String payload){
		HttpDeleteWithBody post;
		try {
			post = new  org.calgb.test.performance.DeleteWithBodyFactory().create(path,payload);
		} catch (BuildPostException e) {
			throw new RuntimeException(e);
		}
		addHeaders(post);
		return post;
	}
	/**
	 * @param post
	 */
	private void addHeaders(HttpDeleteWithBody post) {
		post.addHeader("Content-Type", "application/xml");
		post.addHeader("Accept", "application/xml");
	}

}
