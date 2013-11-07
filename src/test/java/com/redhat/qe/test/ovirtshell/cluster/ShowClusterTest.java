package com.redhat.qe.test.ovirtshell.cluster;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.test.ovirtshell.RhscShellSessionTestBase;

public class ShowClusterTest extends RhscShellSessionTestBase {

	private Cluster cluster;
	private Cluster actual;

	@Before
	public void beforeThis() {
		cluster = ClusterFactory.cluster("c1", "description");

	}

	@After
	public void afterThis() {
		if (actual != null)
			getClusterRepository().destroy(actual);
	}

	@Test
	@Tcms({ "250986" })
	public void showCluster() {
		actual = getClusterRepository().createOrShow(cluster);
		Assert.assertEquals("cluster name", cluster.getName(), actual.getName());
		Assert.assertEquals("cluster name", cluster.getDescription(), actual.getDescription());
	}

}