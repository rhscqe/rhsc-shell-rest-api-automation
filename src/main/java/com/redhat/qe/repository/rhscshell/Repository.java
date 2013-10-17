package com.redhat.qe.repository.rhscshell;

import com.redhat.qe.model.Model;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.IRepository;

public abstract class Repository<T extends Model> implements IRepository<T>{
	private RhscShellSession shell;

	public Repository(RhscShellSession shell){
		this.shell = shell;
	}
	
	public RhscShellSession getShell(){
		return shell;		
	}

}
