package com.redhat.qe.ssh;
import com.redhat.qe.exceptions.UnexpectedReponseException;

import dstywho.regexp.RegexMatch;


public class Response {
	
	private String body;
	private String raw;

	public Response(String body, String raw) {
		this.body = body;
		this.raw = raw;
	}
	
	public Response(String body) {
		this.body = body;
	}
	
	public boolean contains(String regex){
		return new RegexMatch(body).find(regex).size() > 0;
	}
	
	public Response expect(String regex){
		if(!contains(regex))
			throw new UnexpectedReponseException(String.format("Response did not contain pattern %s. Response: %s", regex, body), this);
		return this;
	}
	public Response unexpect(String regex){
		if(contains(regex))
			throw new UnexpectedReponseException(String.format("Response contained pattern %s. Response: %s", regex, body),this);
		return this;
	}
	
	public String getRaw() {
		return raw;
	}

	public String toString(){
		return body;
	}


}
