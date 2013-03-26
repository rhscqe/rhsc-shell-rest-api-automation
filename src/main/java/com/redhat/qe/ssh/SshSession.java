package com.redhat.qe.ssh;
import java.io.IOException;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.redhat.qe.exceptions.HostUnableToCloseChannel;
import com.redhat.qe.exceptions.HostUnableToConnectException;
import com.redhat.qe.exceptions.UnableToObtainInputOrOutputStreamFromChannel;


public class SshSession {
	
	private Credentials credentials;
	private String hostname;
	private int port;
	private Channel channel;
	private Session session;

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

	public Shell getShell() {
		Shell shell = null;
		try{
			shell = new Shell(channel.getInputStream(), channel.getOutputStream());
		}catch(IOException e){
			throw new UnableToObtainInputOrOutputStreamFromChannel(e);
		}
		
		return shell;
	}
	
	private void openChannel() throws JSchException {
		channel=session.openChannel("shell");
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
