package com.redhat.qe.repository.rest.clibrokers;

import java.util.List;

import com.redhat.qe.model.Host;
import com.redhat.qe.repository.rest.HostRepository;
import com.redhat.qe.repository.rest.IHostRepositoryExtended;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.ssh.IResponse;

public class HostRepositoryBroker implements IHostRepositoryExtended{
	
	private HostRepository repo;

	public HostRepositoryBroker(HostRepository repo){
		this.repo = repo;
		
	}

	public Host create(Host host) {
		return repo.create(host);
	}

	public Host show(Host host) {
		return repo.show(host);
	}

	public IResponse activate(Host host) {
		return repo.activate(host);
	}

	public List<Host> listAll() {
		return list();
	}

	public ResponseWrapper _deactivate(Host host) {
		return repo.deactivate(host);
	}

	public IResponse _destroy(Host host) {
		return repo._destroy(host);
	}

	public IResponse destroy(Host host) {
		return repo.destroy(host);
	}

	public Host createOrShow(Host host) {
		return repo.createOrShow(host);
	}

	public List<Host> list() {
		return repo.list();
	}

}
