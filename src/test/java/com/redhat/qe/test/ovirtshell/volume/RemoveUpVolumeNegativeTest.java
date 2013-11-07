package com.redhat.qe.test.ovirtshell.volume;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.helpers.ResponseMessageMatcher;

public class RemoveUpVolumeNegativeTest extends SingleVolumeTestBase{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void _before(){
		getVolumeRepository().start(volume);
		
	}
	
	@After
	public void _after(){
		getVolumeRepository().stop(volume);
	}
	
	@Test
	@Tcms("261778")
	public void test(){
		expectedEx.expect(UnexpectedReponseException.class);
		expectedEx.expect(new ResponseMessageMatcher("volume is up"));
		getVolumeRepository().destroy(volume);
	}

}
