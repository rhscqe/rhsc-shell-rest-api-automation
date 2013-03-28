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
		this(inputStream, new Duration(TimeUnit.SECONDS, 6));
	}

	public Response call(String expectedpattern) {
		long starttime = System.currentTimeMillis();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while (!bufferContains(expectedpattern) && !bufferContainsShell() && !hasTimedOut(starttime))
				if (reader.ready())
					buffer.append((char) reader.read());
			LOG.debug("[shell output] " + buffer.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return expectWhenPatternGiven(expectedpattern);
	}

	/**
	 * @param expectedpattern
	 * @return
	 */
	private Response expectWhenPatternGiven(String expectedpattern) {
		if(expectedpattern != null){
			return new Response(buffer.toString()).expect(expectedpattern);
		}else{
			return new Response(buffer.toString());
		}
	}

	private boolean bufferContains(String pattern) {
			return new RegexMatch(buffer.toString()).find(pattern).size() > 0;
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
		return false && RHSC_PROMPT.matcher(buffer.toString()).find();
	}

	public Response read(String expectPattern) {
		return call(expectPattern);
	}
}