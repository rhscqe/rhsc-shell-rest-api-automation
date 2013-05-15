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
		this(inputStream, new Duration(TimeUnit.SECONDS, 15));
	}

	public Response call() {
		long starttime = System.currentTimeMillis();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while (!bufferContainsPrompt() && !hasTimedOut(starttime)) {
				if (reader.ready()) {
					// char singleChar = (char) reader.read();
					// if(new
					// RegexMatch(String.valueOf(singleChar)).find("\\w|\\s|:|;|\\.|\\(|\\)|\\[|\\]|-|\\+").size()
					// < 1)
					// buffer.append("["+ (int)singleChar + "]");
					// else
					// buffer.append(singleChar);
					buffer.append((char) reader.read());
				}
			}
			LOG.debug("[shell output] " + getBuffer());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new Response(getBuffer(), getBufferWithEscapes());
	}

	private String stripEscapes(String input) {
		return input.replaceAll("\\033.(\\w+|\\?\\w+){0,1}", "");
	}

	private String getBuffer() {
		return stripEscapes(buffer.toString());

	}
	
	public String getBufferWithEscapes(){
		return buffer.toString();
	}


	private boolean hasTimedOut(long startTime) {
		return (System.currentTimeMillis() - startTime) > TimeUnit.MILLISECONDS.convert(timeout.getInterval(), timeout.getUnits());
	}

	private boolean bufferContainsPrompt() {
		boolean result = RHSC_PROMPT.matcher(buffer.toString()).find();
		LOG.trace(String.format("bufferContainsShell() = %s", result));
		return result;
	}

	public Response read() {
		return call();
	}
}