package com.redhat.qe.test.ovirtshell.host;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.helpers.cleanup.CleanupTool;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.rhscshell.ClusterRepository;
import com.redhat.qe.test.ovirtshell.TwoHostClusterTestBase;

public class LoopCreateVolume extends TwoHostClusterTestBase{
	
	private Volume volume;

	@Before
	public void setup() {
	}

	@After
	public void teardown() {
	}
	
	@Test
	public void setupThis(){
		for(int i = 0 ; i< 100; i ++){
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx" + i);
			super.setup();
			volume = new VolumeFactory().distributed("mydistvolume", host1, host2);
			volume = getVolumeRepository(cluster).create(volume);
			getVolumeRepository(cluster).destroy(volume);
			super.teardown();
		}
	}
	
}
