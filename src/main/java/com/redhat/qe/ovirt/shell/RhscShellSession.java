package com.redhat.qe.ovirt.shell;

import java.io.IOException;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.exceptions.UnableToObtainInputOrOutputStreamFromChannel;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.IResponse;
import com.redhat.qe.ssh.InputStreamCollector;
import com.redhat.qe.ssh.RhscOrOvirtShell;
import com.redhat.qe.ssh.RhscOrOvirtShell;
import com.redhat.qe.ssh.Shell;
import com.redhat.qe.ssh.ChannelSshSession;

public class RhscShellSession {
	private String url;
	private Credentials credentials;
	private RhscOrOvirtShell shell;
	
	public static RhscShellSession fromConfiguration(ChannelSshSession ssh){
		return fromConfiguration(ssh, RhscConfiguration.getConfiguration());
	}
	
	public static  RhscShellSession fromConfiguration(ChannelSshSession ssh, Configuration config){
		RhscOrOvirtShell _shell;
		try {
			_shell = new RhscOrOvirtShell(ssh.getChannel().getInputStream(), ssh.getChannel().getOutputStream());
		} catch (IOException e) {
			throw new UnableToObtainInputOrOutputStreamFromChannel(e);
		}
		return fromConfiguration(_shell, config);
	}
	
	public static  RhscShellSession fromConfiguration(RhscOrOvirtShell shell, Configuration config){
		return new RhscShellSession(shell, config.getRestApi().getUrl(),config.getRestApi().getCredentials());
	}
	public RhscShellSession(RhscOrOvirtShell shell, String url, Credentials credentials){
		this.shell = shell;
		this.url = url;
		this.credentials= credentials;
	}
	
	public void start(){
		sendAndCollect("rhsc-shell || ovirt-shell").expect("Welcome");
	}
	
	public void stop(){
		sendAndCollect("exit");
	}

	public IResponse connect() {
		String command = String.format("connect --url '%s' --user '%s' --password '%s' -I", url, credentials.getUsername(), credentials.getPassword());
		return shell.send(command).read().expect("connected to \\w+ manager");
	}
	public IResponse sendAndCollect(String command){
		return shell.send(command).read();
	}
	
	public InputStreamCollector send(String command ){
		return shell.send(command);
	}
	
	public Shell getShell(){
		return shell;	
	}

	


}
