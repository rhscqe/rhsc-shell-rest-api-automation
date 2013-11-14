package com.redhat.qe.ssh;

import java.io.InputStream;
import java.util.regex.Pattern;

public class RhscOrOvirtShellReadInputFactory implements ReadInputFactory<ReadInput>{
	public ReadInput create(InputStream stream) {
		return new ReadInput(getPrompt(), stream);
	}
	
	public Pattern getPrompt() {
		return Pattern.compile("(\\[RHSC\\sshell\\s\\(\\w+\\)\\]#\\s)|(OVIRT shell\\s\\(\\w+\\)\\]#)"); 	}
}
