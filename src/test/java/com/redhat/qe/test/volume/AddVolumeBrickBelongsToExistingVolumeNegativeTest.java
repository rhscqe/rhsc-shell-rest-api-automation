package com.redhat.qe.test.volume;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class AddVolumeBrickBelongsToExistingVolumeNegativeTest extends TwoHostClusterTestBase{
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
	@Tcms("261783")
	public void test(){
		expectedEx.expect(UnexpectedReponseException.class);
		expectedEx.expect(new ResponseMessageMatcher("brick already exists in volume"));
		
		Volume volumeToAdd = VolumeFactory.distributed("failvolume", host1, host2);
		replaceBrickFromExistingVolume(volumeToAdd);
		getVolumeRepository().create(volumeToAdd);
	}

	/**
	 * @param volumeToAdd
	 */
	private void replaceBrickFromExistingVolume(Volume volumeToAdd) {
		List<Brick> bricks = volumeToAdd.getBricks();
		Brick replacementBrick = existingBricks.get(0);
		bricks.add(replacementBrick);
		bricks.remove(0);
		volumeToAdd.setBricks(bricks);
	}

}
