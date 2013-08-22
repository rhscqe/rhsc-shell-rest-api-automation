package com.redhat.qe.ssh;
import com.redhat.qe.exceptions.UnexpectedReponseException;

import dstywho.regexp.RegexMatch;


public class Response implements  IResponse{
	
	private String body;
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	private String raw;

	public Response(String body, String raw) {
		this.body = body;
		this.raw = raw;
	}
	
	public Response(String body) {
		this.body = body;
	}
	
	/* (non-Javadoc)
	 * @see com.redhat.qe.ssh.IRepository#contains(java.lang.String)
	 */
	public boolean contains(String regex){
		return new RegexMatch(body).find(regex).size() > 0;
	}
	
	/* (non-Javadoc)
	 * @see com.redhat.qe.ssh.IRepository#expect(java.lang.String)
	 */
	public IResponse expect(String regex){
		if(!contains(regex))
			throw new UnexpectedReponseException(String.format("Response did not contain pattern %s. Response: %s", regex, body), this);
		return this;
	}
	/* (non-Javadoc)
	 * @see com.redhat.qe.ssh.IRepository#unexpect(java.lang.String)
	 */
	public IResponse unexpect(String regex){
		if(contains(regex))
			throw new UnexpectedReponseException(String.format("Response contained pattern %s. Response: %s", regex, body),this);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.redhat.qe.ssh.IRepository#getRaw()
	 */
	public String getRaw() {
		return raw;
	}

	/* (non-Javadoc)
	 * @see com.redhat.qe.ssh.IRepository#toString()
	 */
	public String toString(){
		return body;
	}


}
