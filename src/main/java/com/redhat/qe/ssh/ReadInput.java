package com.redhat.qe.ssh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class ReadInput implements Callable<String> {
	public static final Pattern RHSC_PROMPT = Pattern.compile("\\[RHSC\\sshell\\s\\(\\w+\\)\\]#\\s");
	
	StringBuffer buffer;
	private InputStream inputStream;
	private Duration timeout; 		
	public ReadInput(InputStream inputStream,Duration timeout){
		this.inputStream = inputStream;
		this.buffer = new StringBuffer();
		this.timeout = timeout;
	}
	public ReadInput(InputStream inputStream){
		this(inputStream,new Duration(TimeUnit.SECONDS, 6));
	}

	public String call() {
		long starttime = System.currentTimeMillis();
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while(true && !bufferContainsShell() && !hasTimedOut(starttime))
			if(reader.ready())			
				buffer.append((char)reader.read());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return buffer.toString();
	}
	
	public String read(){
		return call();
	}
	
	private boolean hasTimedOut(long startTime){
		return (System.currentTimeMillis() - startTime) > TimeUnit.MILLISECONDS.convert(timeout.getInterval(),timeout.getUnits() );
	}
	
	private boolean bufferContainsShell(){
		return false && RHSC_PROMPT.matcher(buffer.toString()).find();
	}
}