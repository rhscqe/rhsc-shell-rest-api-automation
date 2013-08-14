package com.redhat.qe.ssh;

import org.apache.log4j.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.exceptions.HostUnableToConnectException;

public class SshSession {

	private static final Logger LOG = Logger.getLogger(SshSession.class);
	protected Credentials credentials;
	protected String hostname;
	protected int port;
	protected Session session;


	public SshSession(Credentials credentials, String hostname ) {
		this(credentials, hostname, 22);
	}
	
	public SshSession(Credentials credentials, String hostname, int port) {
		this.credentials = credentials;
		this.hostname = hostname;
		this.port = port;
	}

	public void start() {
		//ugly try to start the ssh session twice
		try {
			startSession();
		} catch (JSchException e) {
			try {
				startSession();
			} catch (JSchException e2) {
				throw new HostUnableToConnectException(e2);
			}
		}
	}

	public void stop() {
		stopSession();
	}

	private Session startSession() throws JSchException {
		JSch jsch = new JSch();
		LOG.debug(String.format("connecting to %s@%s:%s", credentials.getUsername(),hostname,port));
		session = jsch.getSession(credentials.getUsername(), hostname, port);
		session.setPassword(credentials.getPassword());
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		LOG.debug("connected");
		return session;
	}

	public void stopSession() {
		session.disconnect();
		LOG.debug("disconnected");
	}

}