package com.redhat.qe.ssh;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


public class Shell {
	private static final Logger LOG = Logger.getLogger(Shell.class);
	protected InputStream fromShell;
	protected PrintStream toShell;

	public Shell(InputStream fromShell, OutputStream toShell){
		this.fromShell = fromShell;
		this.toShell =  new PrintStream(toShell);  // printStream for convenience
	}	
	
	public IResponse clear(){
		return new InputStreamCollector(null,fromShell).clear();
	}


	public <T extends InputStreamCollector> T send(String command, InputStreamCollectorFactory<T> factory ) {
		__send(command);
		return createReader(factory);
	}
	
	public <T extends InputStreamCollector> T createReader(InputStreamCollectorFactory<T> factory){
		return factory.create(fromShell);
	}
	

	private void __send(String command) {
		LOG.debug("sending command: " + command );
		toShell.println(command); toShell.flush();
	}

	public <T extends InputStreamCollector> boolean waitForPrompt(InputStreamCollectorFactory<T> factory ){
		T input = createReader(factory);
		return input.getPrompt().matcher(input.read().getRaw()).find();
	}

	

}
