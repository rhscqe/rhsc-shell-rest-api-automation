package com.redhat.qe.test.volume;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.VolumeRepository;
import com.redhat.qe.ssh.IResponse;

public class ListVolumeTest extends ThreeVolumeTestBase{


	@Test
	@Tcms("251001")
	public void test(){
		List<Volume> volumes = getVolumeRepository().list();
		Asserts.assertContains("volume1", volumes, volume1);
		Asserts.assertContains("volume2", volumes, volume2);
		Asserts.assertContains("volume3", volumes, volume3);
	}
	

	@Test
	@Tcms("251002")
	public void testShowall(){
		ArrayList<Volume> volumes = getVolumeRepository().list(volume1.getCluster(),"--show-all");
		Asserts.assertContains("volume1", volumes, volume1);
		Asserts.assertContains("volume2", volumes, volume2);
		Asserts.assertContains("volume3", volumes, volume3);
		IResponse listResponse= getVolumeRepository()._listAll();
		Collection<HashMap<String, String>> volumesProperties = StringUtils.getProperties(listResponse.toString());
		for(HashMap<String, String> volumeProperties: volumesProperties){
			Set<String> keys = volumeProperties.keySet();
			
			Volume actual = Volume.fromAttrs(volumeProperties, null);
			Volume expected = Collections2.filter(Arrays.asList(new Volume[]{volume1,volume2,volume3}), Predicates.equalTo(actual)).iterator().next();			
			assertEquals("replica count",expected.getReplica_count(),actual.getReplica_count());
			assertEquals("status","down",actual.getStatus());
			assertEquals("stripe count",expected.getStripe_count(),actual.getStripe_count());
			assertEquals("transport type","tcp",actual.getTransportType());
			
			Asserts.assertContains("", keys, "perfomance.cache.size");
			
		}
	}
	

}