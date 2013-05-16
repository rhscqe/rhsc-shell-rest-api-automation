package com.redhat.qe.test.cluster;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.test.OpenShellSessionTestBase;

public class ListClustersTest extends OpenShellSessionTestBase{
	private Cluster c1;
	private Cluster c2;
	@Before
	public void beforeThis(){
		c1 = getClusterRepository().createOrShow(ClusterFactory.cluster("c1"));
		c2 = getClusterRepository().createOrShow(ClusterFactory.cluster("c2"));
		
	}

	@After
	public void afterThis(){
		getClusterRepository().destroy(c1);
		getClusterRepository().destroy(c2);
	}
	@Test
	@Tcms("250545")
	public void test(){
		List<Cluster> clusters = getClusterRepository().list();
		clusters.contains(c1);
		clusters.contains(c2);
		
	}
}
