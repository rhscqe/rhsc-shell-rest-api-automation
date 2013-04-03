package com.redhat.qe.config;

import com.redhat.qe.ssh.Credentials;


public class RestApi {
	private String hostname;
	private Credentials credentials;
	/**
	 * @return the url
	 */
	public String getUrl() {
		return String.format("https://%s/api", hostname);
	}
	/**
	 * @param url
	 * @param credentials
	 */
	public RestApi(String host, Credentials credentials) {
		this.hostname= host;
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
	/**
	 * @return the host
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * @param host the host to set
	 */
	public void setHostname(String host) {
		this.hostname = host;
	}
	
	
	

}
