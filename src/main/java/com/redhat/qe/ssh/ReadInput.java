package com.redhat.qe.ssh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public abstract class ReadInput {

	private static final Logger LOG = Logger.getLogger(RhscShellReadInput.class);
	protected StringBuffer buffer;
	protected InputStream inputStream;
	protected Duration timeout;

	public ReadInput(InputStream inputStream, Duration timeout) {
		this.inputStream = inputStream;
		this.buffer = new StringBuffer();
		this.timeout = timeout;
	}

	public ReadInput(InputStream inputStream) {
		this(inputStream, new Duration(TimeUnit.SECONDS, 15));
	}
	
	public Response call() {
		return call(true);
	}
	
	public Response call(boolean logoutput) {
		long starttime = System.currentTimeMillis();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while (!bufferContainsPrompt() && !hasTimedOut(starttime)) {
				if (reader.ready()) {
					buffer.append((char) reader.read());
				}
			}
			if(logoutput)
				LOG.debug("[shell output] " + getBuffer());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new Response(getBuffer(), getBufferWithEscapes());
	}
	
	public Response clear() {
		return call(false);
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

	private boolean hasTimedOut(long startTime) {
		return (System.currentTimeMillis() - startTime) > TimeUnit.MILLISECONDS.convert(timeout.getInterval(), timeout.getUnits());
	}

	private boolean bufferContainsPrompt() {
		boolean result = getPrompt().matcher(buffer.toString()).find();
		LOG.trace(String.format("bufferContainsShell() = %s", result));
		return result;
	}

	abstract Pattern getPrompt(); 
	
	public Response read() {
		return call();
	}

}