package com.redhat.qe.ssh;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Shell {

	protected InputStream fromShell;
	protected PrintStream toShell;
	private Class<? extends ReadInput> inputReader;

	public Shell(InputStream fromShell, OutputStream toShell, Class<? extends ReadInput> inputReader){
		this.fromShell = fromShell;
		this.toShell =  new PrintStream(toShell);  // printStream for convenience
		this.inputReader = inputReader;
	}	

	public Response send(String command) {
		__send(command);
		return new ReadInputFactory().getReadInput(inputReader, fromShell).read(); 
	}

	private void __send(String command) {
		toShell.println(command); toShell.flush();
	}

}