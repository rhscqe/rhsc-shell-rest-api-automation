package com.redhat.qe.ssh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class InputStreamCollector {

	private static final Logger LOG = Logger.getLogger(InputStreamCollector.class);
	protected StringBuffer buffer;
	protected InputStream inputStream;
	protected Duration timeout;
	private Pattern prompt;

	public InputStreamCollector(Pattern prompt, InputStream inputStream, Duration timeout) {
		this.inputStream = inputStream;
		this.buffer = new StringBuffer();
		this.timeout = timeout;
		this.prompt = prompt;
	}

	public InputStreamCollector(Pattern prompt, InputStream inputStream) {
		this(prompt, inputStream, new Duration(TimeUnit.SECONDS, 60));
	}
	
	public IResponse collect() {
		return collect(true);
	}
	
	public IResponse collect(Duration timeout) {
		return collect(this.prompt, true, timeout);
	}

	public IResponse collect(boolean logoutput ) {
		return collect(this.prompt, true, this.timeout);
	}

	public IResponse collect(Pattern prompt, boolean logoutput, Duration timeout) {
		long starttime = System.currentTimeMillis();
		try {
			BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(inputStream, "UTF-8"));
			LOG.info("collecting output");
			while (!bufferContainsPrompt(prompt) && !hasTimedOut(starttime, timeout)) {
				if (reader.ready()) {
					buffer.append((char) reader.read());
				}
			}
			LOG.info("finished collecting output");
			if(logoutput) LOG.debug("[shell output] " + getBuffer());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new Response(getBuffer(), getBufferWithEscapes());
	}
	
	public IResponse clear() {
		return collect(false);
	}

	private String stripEscapes(String input) {
		return input.replaceAll("\\033.(\\w+|\\?\\w+){0,1}", "");
	}

	private String getBuffer() {
		return stripEscapes(buffer.toString());
	
	}

	private String getBufferWithEscapes() {
		return buffer.toString();
	}

	private boolean hasTimedOut(long startTime, Duration timeoutDuration) {
		return (System.currentTimeMillis() - startTime) > TimeUnit.MILLISECONDS.convert(timeoutDuration.getInterval(), timeout.getUnits());
	}

	private boolean bufferContainsPrompt(Pattern prompt) {
		return bufferContainsContent(prompt);
	}

	private boolean bufferContainsContent(Pattern pattern) {
		boolean result = false;
		if (pattern == null)
			result = false;
		else{
			result = pattern.matcher(buffer.toString()).find();
			LOG.trace(String.format("bufferContainsShell() = %s", result));
		}
		return result;
	}

	
	public IResponse read() {
		return collect();
	}

	public IResponse read(String expect) {
		return collect();
	}

	/**
	 * @return the prompt
	 */
	public Pattern getPrompt() {
		return prompt;
	}

}