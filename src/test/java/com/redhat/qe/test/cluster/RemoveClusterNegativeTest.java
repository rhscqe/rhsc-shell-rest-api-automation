package com.redhat.qe.test.cluster;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.test.HostInClusterTestBase;

public class RemoveClusterNegativeTest extends HostInClusterTestBase{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	@Tcms("233398")
	public void removeNegative() {
		expectedEx.expect(UnexpectedReponseException.class);
		expectedEx.expect(new ResponseMessageMatcher("vds detected"));
		getClusterRepository().destroy(host1.getCluster());
	}
}
