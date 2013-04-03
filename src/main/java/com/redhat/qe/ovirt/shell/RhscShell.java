package com.redhat.qe.ovirt.shell;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.Response;
import com.redhat.qe.ssh.SshSession;

public class RhscShell {
	private String url;
	private Credentials credentials;
	private SshSession ssh;
	
	public static  RhscShell fromConfiguration(SshSession session, Configuration config){
		return new RhscShell(session, config.getRestApi().getUrl(),config.getRestApi().getCredentials());
	}
	public RhscShell(SshSession ssh, String url, Credentials credentials){
		this.ssh = ssh;
		this.url = url;
		this.credentials= credentials;
	}
	
	public void start(){
		send("rhsc-shell").expect("Welcome");
	}
	
	public void stop(){
		send("exit");
	}

	public void connect() {
		String command = String.format("connect --url '%s' --user '%s' --password '%s' -I", url, credentials.getUsername(), credentials.getPassword());
		ssh.getShell().send(command).expect("connected to \\w+ manager");
	}
	
	public Response send(String command ){
		return ssh.getShell().send(command);
	}

}
