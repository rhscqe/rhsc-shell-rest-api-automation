package com.redhat.qe.helpers.ssh;


public class Ls extends Command{

	public Ls(String... arguments) {
		super();
		_add("ls");
		add(arguments);
	}
	
}
