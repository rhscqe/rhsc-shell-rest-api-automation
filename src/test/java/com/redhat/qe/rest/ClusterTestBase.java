package com.redhat.qe.rest;

import org.junit.After;
import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.model.Cluster;

public class ClusterTestBase extends TestBase {

	private Cluster cluster;

	public ClusterTestBase() {
		super();
	}

	@Before
	public void beforeTest() {
		cluster = RhscConfiguration.getConfiguration().getCluster();
		cluster.setDatacenter(defaultDatatcenter());
		cluster = getClusterRepository().createOrShow(cluster);
	}

	@After
	public void afterTest() {
		getClusterRepository().delete(cluster);
	}

}