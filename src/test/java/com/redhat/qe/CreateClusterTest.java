package com.redhat.qe;

import org.junit.Test;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.ssh.Response;

public class CreateClusterTest extends TestBase {
	@Test
	public void test(){
		ClusterRepository repo = new ClusterRepository(getShell());
		Cluster c = new Cluster();
		c.setName("myCluster2");
		c = repo.createOrShow(c);
		repo.destroy(c);
	}


}