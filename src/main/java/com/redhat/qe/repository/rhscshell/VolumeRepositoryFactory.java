package com.redhat.qe.repository.rhscshell;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.IVolumeRepository;
import com.redhat.qe.repository.IVolumeRepositoryFactory;

public class VolumeRepositoryFactory implements IVolumeRepositoryFactory {

	private RhscShellSession session;

	public VolumeRepositoryFactory(RhscShellSession session) {
		this.session  = session;
	}

	public IVolumeRepository getVolumeRepository(Cluster cluster) {
		return new VolumeRepository(session, cluster);
	}

}
