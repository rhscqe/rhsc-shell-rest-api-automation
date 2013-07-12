package com.redhat.qe.repository.glustercli;

import com.redhat.qe.ssh.ExecSshSession;

public class Repository {
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
