package com.redhat.qe.ovirt.shell;

import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.Response;
import com.redhat.qe.ssh.SshSession;

public class RhscShell {
	private String url;
	private Credentials credentials;
	private SshSession ssh;
	
	public RhscShell(SshSession ssh, String url, Credentials credentials){
		this.ssh = ssh;
		this.url = url;
		this.credentials= credentials;
	}
	
	public void start(){
		send("rhsc-shell").expect("shell");
	}
	
	public void stop(){
		
	}

	public void connect() {
		String command = String.format("connect --url '%s' --user '%s' --password '%s' -I", url, credentials.getUsername(), credentials.getPassword());
		Response response = ssh.getShell().send(command);
		response.expect("connected to \\w+ manager");
	}
	
	public Response send(String command){
		return ssh.getShell().send(command);
	}

}
