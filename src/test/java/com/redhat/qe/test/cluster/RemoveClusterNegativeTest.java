package com.redhat.qe.test.cluster;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class RemoveClusterNegativeTest extends TwoHostClusterTestBase{
 
	@Test
	@Tcms("233398")
	public void removeNegative() {
		getClusterRepository()._destroy(host1.getCluster()).expect("vds detected");
	}
	
}
