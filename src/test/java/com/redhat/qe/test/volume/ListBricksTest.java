package com.redhat.qe.test.volume;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class ListBricksTest extends TwoHostClusterTestBase{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	private Volume volume;
	private List<Brick> existingBricks;
	
	@Before
	public void _before(){
		this.volume = VolumeFactory.distributed("blah", host1, host2);
		 existingBricks = volume.getBricks();
		this.volume = getVolumeRepository().create(volume);
		
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

}
