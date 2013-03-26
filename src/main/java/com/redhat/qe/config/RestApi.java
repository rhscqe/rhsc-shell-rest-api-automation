package com.redhat.qe.config;

import com.redhat.qe.ssh.Credentials;


public class RestApi {
	private String url;
	private Credentials credentials;
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @param url
	 * @param credentials
	 */
	public RestApi(String url, Credentials credentials) {
		super();
		this.url = url;
		this.credentials = credentials;
	}
	/**
	 * @return the credentials
	 */
	public Credentials getCredentials() {
		return credentials;
	}
	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	

}
