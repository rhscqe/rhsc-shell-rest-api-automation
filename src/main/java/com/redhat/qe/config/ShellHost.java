package com.redhat.qe.config;

import com.redhat.qe.ssh.Credentials;

public class ShellHost {
	private String hostname;
	private Credentials credentials;
	private int port;
	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * @param hostname
	 * @param credentials
	 */
	public ShellHost(String hostname, Credentials credentials, int port ) {
		super();
		this.port = port;
		this.hostname = hostname;
		this.credentials = credentials;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
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
