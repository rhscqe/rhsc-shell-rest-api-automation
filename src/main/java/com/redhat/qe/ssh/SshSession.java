package com.redhat.qe.ssh;
import java.io.IOException;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.exceptions.HostUnableToCloseChannel;
import com.redhat.qe.exceptions.HostUnableToConnectException;
import com.redhat.qe.exceptions.UnableToObtainInputOrOutputStreamFromChannel;


public class SshSession {
	
	private Credentials credentials;
	private String hostname;
	private int port;
	private ChannelShell channel;
	private Session session;
	
	public static SshSession fromConfiguration(Configuration config){
		return new SshSession(config.getShellHost().getCredentials(), config.getShellHost().getHostname(), config.getShellHost().getPort());
	}

	public SshSession(Credentials credentials, String hostname, int port) {
		super();
		this.credentials = credentials;
		this.hostname = hostname;
		this.port = port;
	}
	public SshSession(Credentials credentials, String hostname) {
		this(credentials,hostname, 22);
	}
	public void start() {
		try{
			startSession();
			openChannel();
		}catch(JSchException e){
			throw new HostUnableToConnectException(e);
		}
	}
	
	public void stop() {
		try {
			closeChannel();
		} catch (JSchException e) {
			throw new HostUnableToCloseChannel(e);
		}
		stopSession();
	}

		Shell shell = null;

	public Shell getShell() {
		try {
			shell = new Shell(channel.getInputStream(), channel.getOutputStream());
		} catch (IOException e) {
			throw new UnableToObtainInputOrOutputStreamFromChannel(e);
		}

		return shell;
	}
	
	private void openChannel() throws JSchException {
		channel=(ChannelShell) session.openChannel("shell");
		channel.setPtySize(200, 5000, 200, 5000);
		channel.connect();
	}
	private void closeChannel() throws JSchException {
		channel.disconnect();
		channel=null;
	}

	private Session startSession() throws JSchException {
		JSch jsch = new JSch();  
		session = jsch.getSession(credentials.getUsername(),hostname ,22);
		session.setPassword(credentials.getPassword());
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		return session;
	}
	
	public void stopSession(){
		session.disconnect();
	}
	
}
