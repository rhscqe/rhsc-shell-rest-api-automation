package com.redhat.qe.test.rest;

import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.repository.DatacenterHelper;
import com.redhat.qe.model.Cluster;

public class ClusterTest extends RestTestBase{
	
	
	@Test 
	public void createDeleteTest() {
		Cluster cluster = RhscConfiguration.getConfiguration().getCluster();
		
		cluster.setDatacenter(new DatacenterHelper().getDefault(getDatacenterRepository()));
		cluster = getClusterRepository().createOrShow(cluster);
		getClusterRepository().destroy(cluster);
	}


	

}
