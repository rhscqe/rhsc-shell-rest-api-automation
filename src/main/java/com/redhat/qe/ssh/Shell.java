package com.redhat.qe.ssh;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


public class Shell {
	private static final Logger LOG = Logger.getLogger(Shell.class);
	protected InputStream fromShell;
	protected PrintStream toShell;
	private Class<? extends ReadInput> inputReader;

	public Shell(InputStream fromShell, OutputStream toShell, Class<? extends ReadInput> inputReader){
		this.fromShell = fromShell;
		this.toShell =  new PrintStream(toShell);  // printStream for convenience
		this.inputReader = inputReader;
	}	
	
	public IResponse clear(){
		return new ReadInputFactory().getReadInput(inputReader, fromShell, new Duration(TimeUnit.SECONDS, 1)).clear(); 
	}

	public IResponse send(String command) {
		__send(command);
		return read(); 
	}

	/**
	 * @return
	 */
	public IResponse read() {
		return inputReader().read();
	}
	
	public boolean waitForPrompt(){
		return inputReader().read().contains(inputReader().getPrompt().toString());
	}

	/**
	 * @return
	 */
	private ReadInput inputReader() {
		return new ReadInputFactory().getReadInput(inputReader, fromShell);
	}
	

	private void __send(String command) {
		LOG.debug("sending command: " + command );
		toShell.println(command); toShell.flush();
	}

//
//	public InputStream getInputStream(){
//		return fromShell;
//	}
//	public OutputStream getOutputStream(){
//		return toShell;
//	}
//	
	

}
