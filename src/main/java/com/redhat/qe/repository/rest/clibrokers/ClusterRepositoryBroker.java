package com.redhat.qe.repository.rest.clibrokers;

import java.util.List;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.repository.IClusterRepository;
import com.redhat.qe.repository.rest.ClusterRepository;
import com.redhat.qe.ssh.IResponse;

public class ClusterRepositoryBroker implements IClusterRepository {
	
	ClusterRepository repo;

	public ClusterRepositoryBroker(HttpSession session) {
		repo = new ClusterRepository(session);
	}

	public Cluster createOrShow(Cluster entity) {
		return repo.createOrShow(entity);
	}

	public Cluster create(Cluster entity) {
		return repo.create(entity);
	}

	public Cluster show(Cluster entity) {
		return repo.show(entity);
	}

	public boolean isExist(Cluster entity) {
		return repo.isExist(entity);
	}

	public IResponse _destroy(Cluster entity) {
		return repo._destroy(entity);
	}

	public IResponse destroy(Cluster host) {
		return repo.destroy(host);
	}

	public List<Cluster> list() {
		return repo.listAll();
	}

}
