package com.redhat.qe.test.rest.volume;

import java.util.ArrayList;

import org.junit.Test;

import com.redhat.qe.model.Brick;
import com.redhat.qe.repository.rest.BrickRepository;

public class RemoveBrickTest extends SingleVolumeTestBase{
	
	@Test
	public void test(){
		BrickRepository brickrepo = new BrickRepository(getSession(), getVolume().getCluster(), getVolume());
		ArrayList<Brick> bricks = brickrepo.list();
		brickrepo.delete(bricks.get(0));
	}

}
