package com.redhat.qe.repository;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.ovirt.shell.RhscShellSession;

public interface IVolumeRepositoryFactory {


	public IVolumeRepository getVolumeRepository(Cluster cluster) ;

}
