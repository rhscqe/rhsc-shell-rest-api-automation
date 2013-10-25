package com.redhat.qe.factories;

import java.util.ArrayList;

import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;

public class VolumeFactory {
	public static Volume distributed(String name,Host host1, Host host2){
			Volume volume = new Volume();
			volume.setName(name);
			volume.setType("distribute");
			volume.setCluster(host1.getCluster());

			ArrayList<Brick> bricks = new ArrayList<Brick>();
			bricks.add(BrickFactory.brick(host1));
			bricks.add(BrickFactory.brick(host1));
			bricks.add(BrickFactory.brick(host1));
			bricks.add(BrickFactory.brick(host2));
			bricks.add(BrickFactory.brick(host2));
			bricks.add(BrickFactory.brick(host2));
			volume.setBricks(bricks);
			return volume;
	}
	
	public static Volume distributed(String name, Host...hosts ){
		Volume volume = new Volume();
		volume.setName(name);
		volume.setType("distribute");
		volume.setCluster(hosts[0].getCluster());
		
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		for(Host host: hosts){
			bricks.add(BrickFactory.brick(host));
		}
		volume.setBricks(bricks);
		return volume;
	}

	public static Volume replicate(String name, Host host1, Host host2) {
		Volume volume = new Volume();
		volume.setName(name);
		volume.setType("replicate");
		volume.setReplicaCount(8);
		volume.setCluster(host1.getCluster());
		for (int _ : new int[4])
			volume.getBricks().add(BrickFactory.brick(host1));
		for (int _ : new int[4])
			volume.getBricks().add(BrickFactory.brick(host2));
		return volume;
		
	}
	
	public static Volume distributedReplicate(String name, Host host1, Host host2) {
		Volume volume = new Volume();
		volume.setName(name);
		volume.setType("DISTRIBUTED_REPLICATE");
		volume.setReplicaCount(2);
		volume.setCluster(host1.getCluster());
		for (int _ : new int[2])
			volume.getBricks().add(BrickFactory.brick(host1));
		for (int _ : new int[2])
			volume.getBricks().add(BrickFactory.brick(host2));
		return volume;
		
	}

}
