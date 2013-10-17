package com.redhat.qe.repository;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.ovirt.shell.RhscShellSession;

public class VolumeRepositoryFactory implements IVolumeRepositoryFactory {

	private RhscShellSession session;

	public VolumeRepositoryFactory(RhscShellSession session) {
		this.session  = session;
	}

	public IVolumeRepository getVolumeRepository(Cluster cluster) {
		return new VolumeRepository(session, cluster);
	}

}
