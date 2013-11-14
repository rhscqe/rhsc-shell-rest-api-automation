package com.redhat.qe.ssh;

import java.io.InputStream;
import java.util.regex.Pattern;

public class RhscOrOvirtShellInputStreamCollectorFactory implements InputStreamCollectorFactory<InputStreamCollector>{
	public InputStreamCollector create(InputStream stream) {
		return new InputStreamCollector(getPrompt(), stream);
	}
	
	public Pattern getPrompt() {
		return Pattern.compile("(\\[RHSC\\sshell\\s\\(\\w+\\)\\]#\\s)|(OVIRT shell\\s\\(\\w+\\)\\]#)"); 	}
}
