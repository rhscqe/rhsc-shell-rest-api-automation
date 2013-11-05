package com.redhat.qe.ssh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public abstract class InputStreamCollector {

	private static final Logger LOG = Logger.getLogger(InputStreamCollector.class);
	protected StringBuffer buffer;
	protected InputStream inputStream;
	protected Duration timeout;

	public InputStreamCollector(InputStream inputStream, Duration timeout) {
		this.inputStream = inputStream;
		this.buffer = new StringBuffer();
		this.timeout = timeout;
	}

	public InputStreamCollector(InputStream inputStream) {
		this(inputStream, new Duration(TimeUnit.SECONDS, 60));
	}
	
	
	public IResponse collectUntilExpectedTextIsPresent(boolean isLogOutput, String...expectedText) {
		long starttime = System.currentTimeMillis();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			LOG.debug("collecting output");
			while (!bufferContainsText(expectedText) && !hasTimedOut(starttime)) {
				if (reader.ready()) {
					buffer.append((char) reader.read());
				}
			}
			if(isLogOutput)
			LOG.debug("finished collecting output");
			LOG.debug("[shell output] " + getBuffer());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new Response(getBuffer(), getBufferWithEscapes());
	}
	
	private boolean bufferContainsText(String[] expectedTexts) {
		boolean result = false;
		for(String text : expectedTexts){
			result = result && bufferContainsText(text);
		}
		return result;

	}

	private boolean bufferContainsText(String expectedText) {
		boolean result = getPrompt().matcher(buffer.toString()).find();
		LOG.trace(String.format("bufferContainsShell() = %s", result));
		return result;
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

	public abstract Pattern getPrompt(); 
	

}