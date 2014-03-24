package com.redhat.qe.test.ovirtshell.volume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.base.Predicate;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.helpers.utils.StringUtils;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.glustercli.transformer.VolumeParser.BrickClientDetails;
import com.redhat.qe.repository.glustercli.transformer.VolumeParser.BrickMemoryDetails;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.ovirtshell.TwoHostClusterTestBase;

import dstywho.timeout.Timeout;

public class ListBricksTest extends TwoHostClusterTestBase{
	private static final int FUZZ_FACTOR = 100;
	private static final int HUGE_FUZZ_FACTOR = 400;
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	private Volume volume;
	private List<Brick> existingBricks;
	
	@Before
	public void _before(){
		this.volume = new VolumeFactory().distributed("blah", host1, host2);
		this.volume.setCluster(cluster);
		existingBricks = volume.getBricks();
		this.volume = getVolumeRepository().showOrCreate(volume);
		
	}
	@After
	public void _after(){
		if(volume != null)
			getVolumeRepository().destroy(volume);
	}
	
	@Test
	@Tcms("251285")
	public void test(){
		ArrayList<Brick> bricks = getVolumeRepository().listBricks(volume);
		Asserts.assertContains("", bricks, existingBricks.get(0));
		Asserts.assertContains("", bricks, existingBricks.get(1));
		Asserts.assertContains("", bricks, existingBricks.get(2));
	}
	
	@Test
	@Tcms("251286")
	public void showAllTest(){
		ArrayList<Brick> bricks = getVolumeRepository().listBricks(volume, "--show-all");
		Asserts.assertContains("", bricks, existingBricks.get(0));
		Asserts.assertContains("", bricks, existingBricks.get(1));
		Asserts.assertContains("", bricks, existingBricks.get(2));
		
		
		ArrayList<Brick> response = getVolumeRepository().listBricks(volume, "--show-all");
		Collection<HashMap<String, String>> attrsforeachbrick = StringUtils.getProperties(response.toString());
		for(HashMap<String,String> attrsforBrick : attrsforeachbrick){
			Asserts.assertContains("", attrsforBrick.keySet(), "server_id");
			Asserts.assertContains("", attrsforBrick.keySet(), "gluster_volume-id");
			Asserts.assertContains("", attrsforBrick.keySet(), "status-state");
		}
	}

	@Test
	@Tcms("273549")
	public void showAllAdvancedDetailsTest() {
		if (!volume.getStatus().equals("up"))
			getVolumeRepository().start(volume);
		try {
			Timeout.TIMEOUT_FIVE_SECONDS.sleep();
			ArrayList<Brick> bricks = getVolumeRepository()
					.listBricksAllContent(volume);

			verifyGeneralDetails(bricks);
			verifyClientDetails(bricks);
			verifyMemStatus(bricks);
		} finally {
			getVolumeRepository().stop(volume);
		}

	}
	
	private void verifyGeneralDetails(ArrayList<Brick> bricks) {
		ExecSshSession session = ExecSshSession.fromHost(RhscConfiguration.getConfiguration().getHosts().iterator().next());
		session.start();
		try{
			ArrayList<Brick> glusterBrickDetails = new com.redhat.qe.repository.glustercli.VolumeRepository(session).status_detail(volume);
			final Brick glusterBrick= glusterBrickDetails.get(0);
			Brick rhscBrick = getMatchingRhscBrick(bricks, glusterBrick);
			
			Assert.assertEquals(glusterBrick.getAttributes().get("Port"), rhscBrick.getAttributes().get("port"));
			Assert.assertEquals(glusterBrick.getAttributes().get("Device"), rhscBrick.getAttributes().get("device"));
			Assert.assertEquals(glusterBrick.getAttributes().get("File System"), rhscBrick.getAttributes().get("fs_name"));
			Assert.assertEquals(glusterBrick.getAttributes().get("Mount Options"), rhscBrick.getAttributes().get("mnt_options")); 
			Assert.assertEquals(glusterBrick.getAttributes().get("File System"), rhscBrick.getAttributes().get("fs_name")); 
			Assert.assertEquals(glusterBrick.getDir(), rhscBrick.getDir()); 
			Assert.assertEquals(glusterBrick.getName(), rhscBrick.getName()); 
		}
		finally{
			session.stop();
		}
	}
	private void verifyClientDetails(ArrayList<Brick> bricks) {
		ExecSshSession session = ExecSshSession.fromHost(RhscConfiguration.getConfiguration().getHosts().iterator().next());
		session.start();
		try{
			ArrayList<BrickClientDetails> glusterVolumeClientDetils= new com.redhat.qe.repository.glustercli.VolumeRepository(session).status_client(volume);
			final BrickClientDetails glusterBrick = glusterVolumeClientDetils.get(0);
			
			Brick rhscBrick = getMatchingRhscBrick(bricks, glusterBrick);

			ArrayList<String> bytesRead = rhscBrick.getMixedAttributes().get("gluster_clients-gluster_client-bytes_read");
			ArrayList<String> bytesWritten = rhscBrick.getMixedAttributes().get("gluster_clients-gluster_client-bytes_written");
			ArrayList<String> ports = rhscBrick.getMixedAttributes().get("gluster_clients-gluster_client-client_port");
			ArrayList<String> hostnames = rhscBrick.getMixedAttributes().get("gluster_clients-gluster_client-host_name");
			
			for(int h=0; h <hostnames.size(); h++){
				final String hostname = hostnames.get(h);
			
				HashMap<String, String> glusterData = CollectionUtils.findFirst(glusterBrick.getData(), new Predicate<HashMap<String,String>>() {
					
					public boolean apply(HashMap<String,String> arg0) {
						return arg0.get("Hostname").contains(hostname);
					}
				});
				
				Assert.assertEquals(glusterData.get("BytesRead"), bytesRead.get(h));
				Assert.assertEquals(glusterData.get("BytesWritten"), bytesWritten.get(h));
				Asserts.assertContains("hostname", glusterData.get("Hostname"), ports.get(h));
			}
			
			
			
		}
		finally{
			session.stop();
		}
	}
	private void verifyMemStatus(ArrayList<Brick> bricks) {
		ExecSshSession session = ExecSshSession.fromHost(RhscConfiguration.getConfiguration().getHosts().iterator().next());
		session.start();
		try{
			ArrayList<BrickMemoryDetails> memDetails = new com.redhat.qe.repository.glustercli.VolumeRepository(session).memStatus(volume);
			final BrickClientDetails glusterBrick = memDetails.get(0);
			
			Brick rhscBrick = getMatchingRhscBrick(bricks, glusterBrick);
			
			ArrayList<String> names = rhscBrick.getMixedAttributes().get("memory_pools-memory_pool-name");
			ArrayList<String> allocCounts = rhscBrick.getMixedAttributes().get("memory_pools-memory_pool-alloc_count");
			ArrayList<String> coldCounts = rhscBrick.getMixedAttributes().get("memory_pools-memory_pool-cold_count");
			ArrayList<String> hotCounts = rhscBrick.getMixedAttributes().get("memory_pools-memory_pool-hot_count");
			ArrayList<String> maxAllocs = rhscBrick.getMixedAttributes().get("memory_pools-memory_pool-max_alloc");
			ArrayList<String> maxStdAllocs = rhscBrick.getMixedAttributes().get("memory_pools-memory_pool-max_stdalloc");
			ArrayList<String> paddedSize = rhscBrick.getMixedAttributes().get("memory_pools-memory_pool-padded_size");
			ArrayList<String> poolMisses = rhscBrick.getMixedAttributes().get("memory_pools-memory_pool-pool_misses");
			
			for(int h=0; h < names.size(); h++){
				final String hostname = names.get(h);
			
				HashMap<String, String> glusterData = CollectionUtils.findFirst(glusterBrick.getData(), new Predicate<HashMap<String,String>>() {
					
					public boolean apply(HashMap<String,String> arg0) {
						return arg0.get("Name").contains(hostname);
					}
				});
				
				Asserts.assertFuzzy(hostname   ,FUZZ_FACTOR, Integer.parseInt(glusterData.get("HotCount")), Integer.parseInt(hotCounts.get(h)));
				Asserts.assertFuzzy(FUZZ_FACTOR, Integer.parseInt(glusterData.get("ColdCount")), Integer.parseInt(coldCounts.get(h)));
				Assert.assertEquals(glusterData.get("Name"), names.get(h));
				Assert.assertEquals(glusterData.get("PaddedSizeof"), paddedSize.get(h));
				Assert.assertEquals(glusterData.get("Misses"), poolMisses.get(h));
				Assert.assertEquals("Max-StdAlloc", glusterData.get("Max-StdAlloc"), maxStdAllocs.get(h) );
				Assert.assertEquals( Integer.parseInt(glusterData.get("AllocCount")), Integer.parseInt(allocCounts.get(h)));
				Assert.assertEquals(rhscBrick.getDir() +":" + hostname, glusterData.get("MaxAlloc"), maxAllocs.get(h));
			}
			
			
			
		}
		finally{
			session.stop();
		}
	}

	private Brick getMatchingRhscBrick(ArrayList<Brick> bricks,
			final BrickClientDetails glusterBrick) {
		Brick rhscBrick = CollectionUtils.findFirst(bricks,
				new Predicate<Brick>() {

					public boolean apply(Brick arg0) {
						return  arg0.getDir().equals(
								glusterBrick.getBrick().getDir());
					}
				});
		return rhscBrick;
	}
	

	
	private Brick getMatchingRhscBrick(ArrayList<Brick> bricks,
			final Brick glusterBrick) {
		Brick rhscBrick = CollectionUtils.findFirst(bricks, new Predicate<Brick>(){
	
			public boolean apply(Brick arg0) {
				return arg0.getDir().equals(glusterBrick.getDir());
			}});
		return rhscBrick;
	}
}
