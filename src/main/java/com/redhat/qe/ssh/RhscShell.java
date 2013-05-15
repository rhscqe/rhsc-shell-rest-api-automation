package com.redhat.qe.ssh;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class RhscShell extends Shell {

	public RhscShell(InputStream fromShell, OutputStream toShell) {
		super(fromShell, toShell, RhscShellReadInput.class);
	}


}
