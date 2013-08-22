package com.redhat.qe.repository;

import java.util.List;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.ssh.IResponse;

public interface IClusterRepository {

	public List<Cluster> list() ;

	public IResponse destroy(Cluster cluster);

}
