package com.redhat.qe.ssh;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class RhscShellReadInput extends ReadInput implements Callable<Response>{
	public static final Pattern RHSC_PROMPT = Pattern.compile("(\\[RHSC\\sshell\\s\\(\\w+\\)\\]#\\s)|(OVIRT shell\\s\\(\\w+\\)\\]#)");

	public RhscShellReadInput(InputStream inputStream, Duration timeout) {
		super(inputStream, timeout);
	}


	public RhscShellReadInput(InputStream inputStream) {
		super(inputStream);
	}

	/**
	 * @return
	 */
	public Pattern getPrompt() {
		return RHSC_PROMPT;
	}
}
