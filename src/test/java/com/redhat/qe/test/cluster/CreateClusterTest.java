package com.redhat.qe.test.cluster;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.test.TestBase;

public class CreateClusterTest extends TestBase {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Tcms({"167062","233396"})
	@Test
	public void createCluster() {
		Cluster c = new Cluster();
		c.setName("myCluster2");
		c = getClusterRepository().createOrShow(c);
		getClusterRepository().destroy(c);
	}

	@Test
	@Tcms("212942")
	public void createClusterNegative() {
		expectedEx.expect(UnexpectedReponseException.class);
		expectedEx.expect(new ResponseMessageMatcher("name in use"));
		Cluster c = new Cluster();
		c.setName("myCluster2");
		c = getClusterRepository().createOrShow(c);
		c = getClusterRepository().create(c);
		getClusterRepository().destroy(c);
	}

	@Test
	@Tcms("233395")
	public void updateCluster() {
		Cluster c = new Cluster();
		c.setName("myCluster2");
		c = getClusterRepository().createOrShow(c);
		c.setName("myClustering");
		
		Cluster expected = c;
		Cluster actual = getClusterRepository().update(c);
		Assert.assertEquals(expected.getName(), actual.getName());
		
		getClusterRepository().destroy(c);
	}
	


}