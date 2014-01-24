package com.redhat.qe.repository.rest.clibrokers;

import java.util.ArrayList;
import java.util.List;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.IVolumeRepositoryExtended;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.IResponse;

public class VolumeRepositoryBroker implements IVolumeRepositoryExtended{
	
	private VolumeRepository repo;

	public VolumeRepositoryBroker(HttpSession session, Cluster cluster){
		this.repo = getVolumeRepository(session,cluster);
	}
	
	public ArrayList<com.redhat.qe.model.Volume> list(Cluster cluster) {
		return repo.list();
	}

	private VolumeRepository getVolumeRepository(HttpSession session, Cluster cluster) {
		return new VolumeRepository(session, cluster);
	}

	public IResponse _stop(Volume volume) {
		return repo._stop(volume);
	}

	public IResponse destroy(Volume volume) {
		return repo.destroy(volume);
	}

	public Volume create(Volume volume) {
		return repo.create(volume);
	}

	public Volume createOrShow(Volume entity) {
		return repo.createOrShow(entity);
	}

	public Volume show(Volume entity) {
		return repo.show(entity);
	}

	public boolean isExist(Volume entity) {
		return repo.isExist(entity);
	}

	public IResponse _destroy(Volume entity) {
		return repo._destroy(entity);
	}

	public List<Volume> list() {
		return repo.list();
	}

	public ArrayList<Volume> listAll() {
		return repo.list();
	}

	public IResponse _listAll() {
		return repo._list();
	}

	public Volume createWithForceCreationOfBrickDirectories(Volume volume) {
		// TODO Auto-generated method stub
		return null;
	}


}
