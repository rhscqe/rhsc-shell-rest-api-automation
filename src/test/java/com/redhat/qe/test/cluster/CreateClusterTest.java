package com.redhat.qe.test.cluster;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.test.OpenShellSessionTestBase;

public class CreateClusterTest extends OpenShellSessionTestBase {


	@Tcms({"167062","233396"})
	@Test
	public void createCluster() {
		Cluster c = getClusterRepository().createOrShow(ClusterFactory.cluster("rhsccluster"));
		getClusterRepository().destroy(c);
	}

	


}