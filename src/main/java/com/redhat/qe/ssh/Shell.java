package com.redhat.qe.ssh;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Shell {
	private InputStream fromShell;
	private PrintStream toShell;

	public Shell(InputStream fromShell, OutputStream toShell){
		this.fromShell = fromShell;
		this.toShell =  new PrintStream(toShell);  // printStream for convenience
	}
	
	public Response send(String command){
		return send(command,null);
	}
	
	public Response send(String command, String expectPattern){
		__send(command);
		return new ReadInput(fromShell).read(expectPattern);
	}
	
	private void __send(String command){
		toShell.println(command); toShell.flush();
	}

}
