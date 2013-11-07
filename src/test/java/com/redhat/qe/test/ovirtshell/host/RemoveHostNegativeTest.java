package com.redhat.qe.test.ovirtshell.host;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.test.ovirtshell.TwoHostClusterTestBase;

public class RemoveHostNegativeTest extends TwoHostClusterTestBase{
	
	private Volume volume;
	
	@Before
	public void setupThis(){
		volume = VolumeFactory.distributed("mydistvolume", host1, host2);
		volume = getVolumeRepository(cluster).create(volume);
	}
	
	@After
	public void afterThis(){
		if( volume != null)
			getVolumeRepository(cluster).destroy(volume);
		
	}
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	
	@Tcms(value = { "261779" })	
	@Test
	public void test(){
		String expectedMessage = "[Cc]annot remove.*having [Gg]luster volume";
		getHostRepository().deactivate(host1);
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host1, "maintenance", 400));
		assertEquals("maintenance", getHostRepository().show(host1).getState());
		getHostRepository()._destroy(host1).expect(expectedMessage);
	}

}
