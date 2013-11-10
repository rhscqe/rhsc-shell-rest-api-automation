package com.redhat.qe.helpers.ssh;


public class Find extends Command{

	public Find(String... arguments) {
		super();
		_add("find");
		_add(arguments);
	}
	
}
