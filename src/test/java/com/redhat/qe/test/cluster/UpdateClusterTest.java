package com.redhat.qe.test.cluster;


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
import com.redhat.qe.test.RhscShellSessionTestBase;

public class UpdateClusterTest extends RhscShellSessionTestBase {
	
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
	@Tcms({"233395","250978"})
	public void updateCluster() {
		c1.setName("blah");
		Cluster expected = c1;
		Cluster actual = getClusterRepository().update(expected);
		Assert.assertEquals(expected.getName(), actual.getName());
		
	}
	


}