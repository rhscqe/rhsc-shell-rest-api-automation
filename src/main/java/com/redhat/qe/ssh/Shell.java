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
		__send(command);
		return new Response(new ReadInput(fromShell).read());
		
	}
	
	private void __send(String command){
		toShell.println(command); toShell.flush();
	}

}
