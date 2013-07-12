package com.redhat.qe.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.jclouds.compute.domain.ExecChannel;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.exceptions.ChannelClosedUnexpectedlyException;
import com.redhat.qe.exceptions.ChannelFailedToOpenException;
import com.redhat.qe.exceptions.HostUnableToCloseChannel;

import dstywho.functional.Closure2;

public class ExecSshSession extends SshSession {

	public static ExecSshSession fromConfiguration(Configuration config) {
		return new ExecSshSession(config.getShellHost().getCredentials(), config.getShellHost().getHostname(), config.getShellHost().getPort());
	}

	public ExecSshSession(Credentials credentials, String hostname) {
		super(credentials, hostname, 22);
	}

	public ExecSshSession(Credentials credentials, String hostname, int port) {
		super(credentials, hostname, port);
	}


	public static class Response {
		int exitCode;
		String stderr;
		String stdout;

		/**
		 * @param exitCode
		 * @param stderr
		 * @param stdout
		 */
		public Response(int exitCode, String stderr, String stdout) {
			super();
			this.exitCode = exitCode;
			this.stderr = stderr;
			this.stdout = stdout;
		}

		/**
		 * @return the exitCode
		 */
		public int getExitCode() {
			return exitCode;
		}

		/**
		 * @param exitCode
		 *            the exitCode to set
		 */
		public void setExitCode(int exitCode) {
			this.exitCode = exitCode;
		}

		/**
		 * @return the stderr
		 */
		public String getStderr() {
			return stderr;
		}

		/**
		 * @param stderr
		 *            the stderr to set
		 */
		public void setStderr(String stderr) {
			this.stderr = stderr;
		}

		/**
		 * @return the stdout
		 */
		public String getStdout() {
			return stdout;
		}

		/**
		 * @param stdout
		 *            the stdout to set
		 */
		public void setStdout(String stdout) {
			this.stdout = stdout;
		}

	}

	public Response runCommand(String command) {
		ChannelExec channel = createChannel();
		
		 InputStream stdout = getInputStream(channel );
		InputStream stderr = getErrStream(channel);
	
		channel.setCommand(command);
		connectChannel(channel);
		waitUntilCommanFinishes(channel);
		String stdErr = getOutput(stderr);
		String stdOut = getOutput(stdout);
		int exitStatus = channel.getExitStatus();
		Response response = new Response(exitStatus, stdErr, stdOut);
		channel.disconnect();
		return response;
	}

	/**
	 * @param channel
	 * @return
	 */
	private InputStream getErrStream(ChannelExec channel) {
		InputStream stderr = null;
		 try {
			 stderr = channel.getErrStream();
		 } catch (IOException e) {
			 throw new RuntimeException(e);
		 }
		return stderr;
	}

	/**
	 * @param channel
	 * @param stdout
	 * @return
	 */
	private InputStream getInputStream(ChannelExec channel) {
		InputStream stdout = null;
		try {
			stdout = channel.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return stdout;
	}

	private void connectChannel(ChannelExec channel) {
		try {
			channel.connect();
		} catch (JSchException e) {
			throw new RuntimeException("can not start channel", e);
		}
		
	}

	/**
	 * 
	 */
	private void waitUntilCommanFinishes(ChannelExec channel) {
		while(true){
			if(channel.isEOF()){
				break;
			}
		}
	}



	public String getOutput(InputStream in) {
		try {
			if (in.available() > 0) {
				return IOUtils.toString(in);
			}
		} catch (IOException e) {
			throw new RuntimeException("unable to obtain stream");
		}
		return "";
	}

	/**
	 * @return 
	 * @return
	 * @return
	 */

	private ChannelExec createChannel() {
		try {
			return (ChannelExec) session.openChannel("exec");
		} catch (JSchException e) {
			throw new ChannelFailedToOpenException();
		}
	}

}
