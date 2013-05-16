package com.redhat.qe.test.host;

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
import com.redhat.qe.test.TwoHostClusterTestBase;

public class RemoveHostNegativeTest extends TwoHostClusterTestBase{
	
	private Volume volume;
	
	@Before
	public void setupThis(){
		volume = VolumeFactory.distributed("mydistvolume", host1, host2);
		volume = getVolumeRepository().create(volume);
	}
	
	@After
	public void afterThis(){
		if( volume != null)
			getVolumeRepository().destroy(volume);
		
	}
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	
	@Tcms(value = { "261779" })	
	@Test
	public void test(){
		expectedEx.expect(UnexpectedReponseException.class);
		expectedEx.expect(new ResponseMessageMatcher("cannot remove host having gluster volume"));
		getHostRepository().deactivate(host1);
		Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host1, "maintenance", 400));
		assertEquals("maintenance", getHostRepository().show(host1).getState());
		getHostRepository().destroy(host1);
	}

}
