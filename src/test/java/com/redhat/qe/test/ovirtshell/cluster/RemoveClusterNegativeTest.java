package com.redhat.qe.test.ovirtshell.cluster;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.test.ovirtshell.TwoHostClusterTestBase;

public class RemoveClusterNegativeTest extends TwoHostClusterTestBase{
 
	@Test
	@Tcms("233398")
	public void removeNegativeWhenClusterContainsHosts() {
		getClusterRepository()._destroy(host1.getCluster()).expect("contains one or more Hosts");
	}
	
}
