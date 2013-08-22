package com.redhat.qe.ovirt.shell;

import java.io.IOException;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.exceptions.UnableToObtainInputOrOutputStreamFromChannel;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.IResponse;
import com.redhat.qe.ssh.RhscShell;
import com.redhat.qe.ssh.Shell;
import com.redhat.qe.ssh.ChannelSshSession;

public class RhscShellSession {
	private String url;
	private Credentials credentials;
	private Shell shell;
	
	public static  RhscShellSession fromConfiguration(ChannelSshSession ssh, Configuration config){
		RhscShell _shell;
		try {
			_shell = new RhscShell(ssh.getChannel().getInputStream(), ssh.getChannel().getOutputStream());
		} catch (IOException e) {
			throw new UnableToObtainInputOrOutputStreamFromChannel(e);
		}
		return fromConfiguration(_shell, config);
	}
	
	public static  RhscShellSession fromConfiguration(Shell shell, Configuration config){
		return new RhscShellSession(shell, config.getRestApi().getUrl(),config.getRestApi().getCredentials());
	}
	public RhscShellSession(Shell shell, String url, Credentials credentials){
		this.shell = shell;
		this.url = url;
		this.credentials= credentials;
	}
	
	public void start(){
		send("rhsc-shell || ovirt-shell").expect("Welcome");
	}
	
	public void stop(){
		send("exit");
	}

	public IResponse connect() {
		String command = String.format("connect --url '%s' --user '%s' --password '%s' -I", url, credentials.getUsername(), credentials.getPassword());
		return shell.send(command).expect("connected to \\w+ manager");
	}
	
	public IResponse send(String command ){
		return shell.send(command);
	}
	
	public Shell getShell(){
		return shell;	
	}

	


}
