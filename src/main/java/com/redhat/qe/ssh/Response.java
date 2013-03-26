package com.redhat.qe.ssh;
import com.redhat.qe.exceptions.UnexpectedReponseException;

import dstywho.regexp.RegexMatch;


public class Response {
	
	private String body;

	public Response(String body) {
		this.body = body;
	}
	
	public boolean contains(String regex){
		return new RegexMatch(body).find(regex).size() > 0;
	}
	
	public Response expect(String regex){
		if(!contains(regex))
			throw new UnexpectedReponseException(String.format("Response did not contain pattern %s. Response: %s", regex, body));
		return this;
	}
	
	public String toString(){
		return body;
	}

}
