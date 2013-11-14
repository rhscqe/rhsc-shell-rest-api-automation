package com.redhat.qe.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import com.redhat.qe.exceptions.UnableToObtainInputOrOutputStreamFromChannel;

public class RhscOrOvirtShell extends Shell {
	
	public static RhscOrOvirtShell fromSsh(ChannelSshSession ssh){
		RhscOrOvirtShell shell = null;
		try {
			shell = new RhscOrOvirtShell(ssh.getChannel().getInputStream(), ssh.getChannel().getOutputStream());
		} catch (IOException e) {
			throw new UnableToObtainInputOrOutputStreamFromChannel(e);
		}
		return shell;
	}
	
	public static RhscOrOvirtShell fromShell(Shell shell){
		return new RhscOrOvirtShell(shell.fromShell, shell.toShell);
	}

	public RhscOrOvirtShell(InputStream fromShell, OutputStream toShell) {
		super(fromShell, toShell );
	}

	public InputStreamCollector send(String command) {
		return send(command, new RhscOrOvirtShellInputStreamCollectorFactory());
	}

	public boolean waitForPrompt() {
		return waitForPrompt(new RhscOrOvirtShellInputStreamCollectorFactory());
	}
	


}
