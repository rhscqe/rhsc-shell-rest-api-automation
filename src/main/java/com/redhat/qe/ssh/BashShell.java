package com.redhat.qe.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import com.redhat.qe.exceptions.UnableToObtainInputOrOutputStreamFromChannel;

public class BashShell extends Shell {
	
	public static BashShell fromSsh(ChannelSshSession ssh){
		BashShell shell = null;
		try {
			shell = new BashShell(ssh.getChannel().getInputStream(), ssh.getChannel().getOutputStream());
		} catch (IOException e) {
			throw new UnableToObtainInputOrOutputStreamFromChannel(e);
		}
		return shell;
	}
	
	public static BashShell fromShell(Shell shell){
		return new BashShell(shell.fromShell, shell.toShell);
	}

	public BashShell(InputStream fromShell, OutputStream toShell) {
		super(fromShell, toShell, BashShellReadInput.class);
	}


}
