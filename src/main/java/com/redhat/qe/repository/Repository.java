package com.redhat.qe.repository;

import com.redhat.qe.model.Model;
import com.redhat.qe.ovirt.shell.RhscShell;

public abstract class Repository<T extends Model> implements IRepository<T>{
	private RhscShell shell;

	public Repository(RhscShell shell){
		this.shell = shell;
	}
	
	public RhscShell getShell(){
		return shell;		
	}

}
