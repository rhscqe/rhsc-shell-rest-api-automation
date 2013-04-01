package com.redhat.qe.ssh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


import dstywho.regexp.RegexMatch;

public class ReadInput implements Callable<Response> {
	private static final Logger LOG = Logger.getLogger(ReadInput.class);
	public static final Pattern RHSC_PROMPT = Pattern.compile("\\[RHSC\\sshell\\s\\(\\w+\\)\\]#\\s");

	StringBuffer buffer;
	private InputStream inputStream;
	private Duration timeout;

	public ReadInput(InputStream inputStream, Duration timeout) {
		this.inputStream = inputStream;
		this.buffer = new StringBuffer();
		this.timeout = timeout;
	}

	public ReadInput(InputStream inputStream) {
		this(inputStream, new Duration(TimeUnit.SECONDS, 10));
	}

	public Response call(String expectedpattern) {
		long starttime = System.currentTimeMillis();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while (!bufferContains(expectedpattern) && !bufferContainsShell() && !hasTimedOut(starttime)){
				if (reader.ready()){
//					char singleChar = (char) reader.read();
//					if(new RegexMatch(String.valueOf(singleChar)).find("\\w|\\s|:|;|\\.|\\(|\\)|\\[|\\]|-|\\+").size() < 1)
//						buffer.append("["+ (int)singleChar + "]");
//					else
//						buffer.append(singleChar);
					buffer.append((char)reader.read());
				}
			}
			LOG.debug("[shell output] " + getBuffer());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return expectWhenPatternGiven(expectedpattern);
	}
	
	private String stripEscapes(String input){
		return input.replaceAll("\\033.(\\w+|\\?\\w+){0,1}","");
	}
	
	private String getBuffer(){
		return stripEscapes(buffer.toString());
		
	}

	/**
	 * @param expectedpattern
	 * @return
	 */
	private Response expectWhenPatternGiven(String expectedpattern) {
		if(expectedpattern != null){
			return new Response(getBuffer()).expect(expectedpattern);
		}else{
			return new Response(getBuffer());
		}
	}

	private boolean bufferContains(String pattern) {
			return new RegexMatch(getBuffer()).find(pattern).size() > 0;
	}

	public Response call() {
		return call(null);
	}
	
	public Response read() {
		return call(null);
	}

	private boolean hasTimedOut(long startTime) {
		return (System.currentTimeMillis() - startTime) > TimeUnit.MILLISECONDS.convert(timeout.getInterval(), timeout.getUnits());
	}

	private boolean bufferContainsShell() {
		return RHSC_PROMPT.matcher(buffer.toString()).find();
	}

	public Response read(String expectPattern) {
		return call(expectPattern);
	}
}