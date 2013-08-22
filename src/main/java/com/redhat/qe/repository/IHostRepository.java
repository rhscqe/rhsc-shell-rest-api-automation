package com.redhat.qe.repository;

import com.redhat.qe.model.Host;
import com.redhat.qe.ssh.IResponse;

public interface IHostRepository {

	public abstract Host create(Host host);

	public abstract Host show(Host host);

	public IResponse activate(Host host);
	
	public IResponse _deactivate(Host host);

	public IResponse _destroy(Host host);
	
	public IResponse destroy(Host host);
}