package com.redhat.qe.test.rest;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.helpers.cleanup.RestCleanupTool;
import com.redhat.qe.helpers.utils.FileNameHelper;
import com.redhat.qe.model.Cluster;

public class PaginationTest extends RestTestBase{

	public ArrayList<Cluster> expectedClusters = new ArrayList<Cluster>();

	@Before
	public void setuptest(){
		for(int i = 0; i < 200; i ++){
			String randomName = new FileNameHelper().randomFileName();
			expectedClusters.add(getClusterRepository().create(ClusterFactory.cluster(randomName)));
		}
	}
	
	@After
	public void teardowntest(){
		new RestCleanupTool().cleanup(RhscConfiguration.getConfiguration());
	}

	@Test
	public void test(){
		Assert.assertEquals(getClusterRepository().listAll().size(),expectedClusters.size());
	}

}
