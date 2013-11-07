package com.redhat.qe.test.ovirtshell.host;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.utils.StringUtils;
import com.redhat.qe.model.Host;
import com.redhat.qe.test.ovirtshell.TwoHostClusterTestBase;

public class UpdateHostTest extends TwoHostClusterTestBase{
	
	
	@Test
	@Tcms("250989")
	public void test(){
		Host expected = host1;
		host1.setName("hostnamers");
		Host actual = getHostRepository().update(host1);
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected, actual);
	}
	
		

}
