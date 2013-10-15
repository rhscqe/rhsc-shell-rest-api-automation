package com.redhat.qe.repository;

import com.redhat.qe.model.Host;
import com.redhat.qe.ssh.IResponse;

public interface IHostRepository extends IGenericRepository<Host> {

	public IResponse activate(Host entity);

	public IResponse deactivate(Host entity);
	public IResponse _deactivate(Host entity);

}