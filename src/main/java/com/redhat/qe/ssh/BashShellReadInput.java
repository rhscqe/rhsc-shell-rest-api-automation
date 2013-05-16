package com.redhat.qe.ssh;

import java.io.InputStream;
import java.util.regex.Pattern;


public class BashShellReadInput  extends ReadInput {

	public BashShellReadInput(InputStream inputStream) {
		super(inputStream);
	}

	/**
	 * @param inputStream
	 * @param timeout
	 */
	public BashShellReadInput(InputStream inputStream, Duration timeout) {
		super(inputStream, timeout);
	}

	@Override
	Pattern getPrompt() {
		return Pattern.compile("@.*#");
	}


}
