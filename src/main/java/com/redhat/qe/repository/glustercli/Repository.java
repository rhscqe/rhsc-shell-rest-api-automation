package com.redhat.qe.repository.glustercli;

import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

import dstywho.timeout.Timeout;

public class Repository {
	
	private static final int RETRY_ATTEMPTS = 5;
	public Response runCommandMultipleAttempts(String command){
		Response response = null; 
		for(int i=0; i< RETRY_ATTEMPTS ; i ++){
			response = getShell().runCommand(command);
			if(response.getStdout().contains("try again"))
				Timeout.TIMEOUT_FIVE_SECONDS.sleep();
			else
				return response;
		}
		return response;
	}
	
	private ExecSshSession shell;

	/**
	 * @param shell
	 */
	public Repository(ExecSshSession shell) {
		super();
		this.shell = shell;
	}

	/**
	 * @return the shell
	 */
	public ExecSshSession getShell() {
		return shell;
	}

	/**
	 * @param shell the shell to set
	 */
	public void setShell(ExecSshSession shell) {
		this.shell = shell;
	}
	
	

}
