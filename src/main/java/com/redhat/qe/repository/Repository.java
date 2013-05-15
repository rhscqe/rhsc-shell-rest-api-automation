package com.redhat.qe.repository;

import com.redhat.qe.model.Model;
import com.redhat.qe.ovirt.shell.RhscShellSession;

public abstract class Repository<T extends Model> implements IRepository<T>{
	private RhscShellSession shell;

	public Repository(RhscShellSession shell){
		this.shell = shell;
	}
	
	public RhscShellSession getShell(){
		return shell;		
	}

}
