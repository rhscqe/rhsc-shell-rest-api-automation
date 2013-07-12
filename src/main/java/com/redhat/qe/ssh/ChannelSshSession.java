package com.redhat.qe.ssh;

import java.io.IOException;


import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.exceptions.ChannelClosedUnexpectedlyException;
import com.redhat.qe.exceptions.ChannelFailedToOpenException;
import com.redhat.qe.exceptions.HostUnableToCloseChannel;
import com.redhat.qe.exceptions.UnableToObtainInputOrOutputStreamFromChannel;

public class ChannelSshSession extends SshSession {
	private ChannelShell channel;
	
	public static ChannelSshSession fromConfiguration(Configuration config) {
		return new ChannelSshSession(config.getShellHost().getCredentials(), config.getShellHost().getHostname(), config.getShellHost().getPort());
	}
	
	public ChannelSshSession(Credentials credentials, String hostname, int port) {
		super(credentials,hostname,port);
	}

	public ChannelSshSession(Credentials credentials, String hostname) {
		super(credentials, hostname, 22);
	}

	/**
	 * 
	 */
	public void stopChannel() {
		try {
			if(channel != null)
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

}
