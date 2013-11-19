package com.redhat.qe.ssh;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.exceptions.ChannelFailedToOpenException;
import com.redhat.qe.model.Host;

import dstywho.timeout.Timeout;

public class ExecSshSession extends SshSession {
	private static final int SESSION_TIMEOUT = 3020000;
	private static final Logger LOG = Logger.getLogger(ExecSshSession.class);
	private static final int MAX_ATTEMPTS = 15;

	public static ExecSshSession fromHost(Host host){
		return new ExecSshSession(new Credentials("root",
				host.getRootPassword()), host.getAddress());
		
	}

	public static ExecSshSession fromSession(SshSession existingSession){
		return new ExecSshSession( existingSession.credentials, existingSession.hostname);
	}

	public Response withSession(Function<ExecSshSession, Response> toBeExecuted){
		start();
		Response result = null;
		try{
			result = toBeExecuted.apply(this);
		}finally{
			stop();
		}
		return result;
	}
	
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
		
		public boolean isSuccessful(){
			return getExitCode() == 0;
		}

		public Response expectSuccessful(){
			Assert.assertEquals(0, getExitCode());
			return this;
		}

	}


	public Response runCommand(String... commands) {
		String command = Joiner.on(" ").join(commands);
		
		ChannelExec channel = createChannel();
		
		 InputStream stdout = getInputStream(channel );
		InputStream stderr = getErrStream(channel);
		
	
		LOG.debug("running command---------:" + command);
		LOG.debug("command:" + command);
		channel.setCommand(command);
		connectChannel(channel);
		waitUntilCommanFinishes(channel, stdout);
		String stdErr = getOutput(stderr);
		String stdOut = getOutput(stdout);
		int exitStatus = channel.getExitStatus();
		Response response = new Response(exitStatus, stdErr, stdOut);
		channel.disconnect();
		LOG.debug("exit status:" + exitStatus);
		LOG.debug("stderr:" + stdErr);
		LOG.debug("stdout:" + stdOut);
		return response;
	}
	
	public Response runCommandAndAssertSuccess(String... commands){
		Response result = runCommand(commands);
		result.expectSuccessful();
		return result;
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
			channel.getSession().setTimeout(SESSION_TIMEOUT);
			channel.connect();
		} catch (JSchException e) {
			throw new RuntimeException("can not start channel", e);
		}
		
	}

	/**
	 * @param stdout 
	 * 
	 */
	private void waitUntilCommanFinishes(ChannelExec channel, InputStream stdout) {
		int attempt = 0;
		while(true){
			if(channel.isEOF() || attempt > MAX_ATTEMPTS){
				break;
			}else{
				
				Timeout.TIMEOUT_ONE_SECOND.sleep();
				attempt++;
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
