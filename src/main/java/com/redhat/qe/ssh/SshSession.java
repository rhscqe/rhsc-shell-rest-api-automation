package com.redhat.qe.ssh;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.exceptions.ChannelClosedUnexpectedlyException;
import com.redhat.qe.exceptions.ChannelFailedToOpenException;
import com.redhat.qe.exceptions.HostUnableToCloseChannel;
import com.redhat.qe.exceptions.HostUnableToConnectException;
import com.redhat.qe.exceptions.UnableToObtainInputOrOutputStreamFromChannel;

public class SshSession {
	private static final Logger LOG = Logger.getLogger(SshSession.class);

	private Credentials credentials;
	private String hostname;
	private int port;
	private ChannelShell channel;
	private Session session;

	public static SshSession fromConfiguration(Configuration config) {
		return new SshSession(config.getShellHost().getCredentials(), config.getShellHost().getHostname(), config.getShellHost().getPort());
	}

	public SshSession(Credentials credentials, String hostname, int port) {
		super();
		this.credentials = credentials;
		this.hostname = hostname;
		this.port = port;
	}

	public SshSession(Credentials credentials, String hostname) {
		this(credentials, hostname, 22);
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

	/**
	 * 
	 */
	public void stopChannel() {
		try {
			channel.disconnect();
		} catch (Exception e) {
			throw new HostUnableToCloseChannel(e);
		}
		channel = null;
		
	}


	/**
	 * @return
	 */
	public ChannelShell getChannel() {
		if (channel == null) {
			throw new ChannelClosedUnexpectedlyException();
		}
		return channel;
	}

	public void openChannel() {
		try {
			channel = (ChannelShell) session.openChannel("shell");

			getChannel().setPtySize(200, 5000, 200, 5000);
			getChannel().connect();
		} catch (JSchException e) {
			throw new ChannelFailedToOpenException();
		}
		if (getChannel() == null) {
			throw new ChannelFailedToOpenException();
		}
	}


	private Session startSession() throws JSchException {
		JSch jsch = new JSch();
		LOG.debug(String.format("connecting to %s@%s:%s", credentials.getUsername(),hostname,port));
		session = jsch.getSession(credentials.getUsername(), hostname, port);
		session.setPassword(credentials.getPassword());
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		return session;
	}

	public void stopSession() {
		session.disconnect();
	}

}
