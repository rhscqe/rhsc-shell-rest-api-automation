package com.redhat.qe;

import org.junit.Test;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.repository.ClusterRepository;

public class CreateCluster extends TestBase {
	@Test
	public void test(){
		ClusterRepository repo = new ClusterRepository(getShell());
		Cluster c = new Cluster();
		c.setName("myCluster");
		repo.create(c);
		
	}


}