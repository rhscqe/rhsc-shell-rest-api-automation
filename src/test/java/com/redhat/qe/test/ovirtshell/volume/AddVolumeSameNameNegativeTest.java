package com.redhat.qe.test.ovirtshell.volume;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.ResponseMessageMatcher;

public class AddVolumeSameNameNegativeTest extends SingleVolumeTestBase{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	
	@Test
	@Tcms("261781")
	public void test(){
		expectedEx.expect(UnexpectedReponseException.class);
		expectedEx.expect(new ResponseMessageMatcher("already exists"));
		getVolumeRepository(volume.getCluster()).create(VolumeFactory.distributed(volume.getName(), host1, host2));
	}

}
