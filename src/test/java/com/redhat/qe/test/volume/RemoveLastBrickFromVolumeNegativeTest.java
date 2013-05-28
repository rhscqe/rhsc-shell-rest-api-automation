package com.redhat.qe.test.volume;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class RemoveLastBrickFromVolumeNegativeTest extends TwoHostClusterTestBase{
	private Brick brick;
	private Volume volume;
	@Before
	public void _before(){
		volume = new Volume();
		volume.setName("blah");
		volume.setType("distribute");
		volume.setCluster(host1.getCluster());

		ArrayList<Brick> bricks = new ArrayList<Brick>();
		 brick = BrickFactory.brick(host1);
		bricks.add(brick);
		volume.setBricks(bricks);
		volume = getVolumeRepository().create(volume);
	}
	
	@After
	public void _after(){
		getVolumeRepository().destroy(volume);
	}
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	

	
	@Test
	@Tcms("251280")
	public void test(){
		expectedEx.expect(UnexpectedReponseException.class);
		expectedEx.expect(new ResponseMessageMatcher("can not remove all bricks from volume"));
		Brick brickToRemove = getVolumeRepository().listBricks(volume).get(0);
		getVolumeRepository().removeBrick(volume, brickToRemove);
	}

}
