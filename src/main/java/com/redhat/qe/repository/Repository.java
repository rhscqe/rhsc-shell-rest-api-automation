package com.redhat.qe.repository;

import com.redhat.qe.ovirt.shell.RhscShell;

public class Repository {
	private RhscShell shell;

	public Repository(RhscShell shell){
		this.shell = shell;
	}
	
	public RhscShell getShell(){
		return shell;		
	}
}
