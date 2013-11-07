package com.redhat.qe.test.rest;

import org.junit.After;
import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.repository.ClusterHelper;
import com.redhat.qe.model.Cluster;

public abstract class ClusterTestBase extends RestTestBase {

	private Cluster cluster;

	public ClusterTestBase() {
		super();
	}
	protected Cluster getCluster() {
		return cluster;
	}
	public abstract Cluster getClusterToBeCreated();

	@Before
	public void createOrShowCluster() {
		cluster = new ClusterHelper().create(getClusterToBeCreated(), getClusterRepository(), getDatacenterRepository());
	}

	@After
	public void cleanupCluster() {
		getClusterRepository().destroy(cluster);
	}

}