package com.redhat.qe.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;

public class VolumeFactory {
	
	public static class InfiniteIterator<V,T extends Iterable<V>>{
		private Iterator<V> currentIterator;
		private T iterable;

		public InfiniteIterator(T iterable){
			this.iterable = iterable;
			currentIterator = (iterable.iterator());
		}
		
		public V next(){
			if(!currentIterator.hasNext()){
				currentIterator = iterable.iterator();
			}
			return currentIterator.next();
			
		}
		
	}
	public static Volume distributed(String name,int numbricks, Host... hosts){
		Volume volume = new Volume();
		volume.setName(name);
		volume.setType("distribute");
		volume.setCluster(hosts[0].getCluster());
		
		ArrayList<Brick> bricks = createBricksUsingHosts(numbricks, hosts);
		volume.setBricks(bricks);
		return volume;
	}
	
	
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
	
	public static Volume distributedUneven(String name,Host host1, Host host2){
		Volume volume = new Volume();
		volume.setName(name);
		volume.setType("distribute");
		volume.setCluster(host1.getCluster());
		
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		bricks.add(BrickFactory.brick(host1));
		bricks.add(BrickFactory.brick(host1));
		bricks.add(BrickFactory.brick(host1));
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
			bricks.add(BrickFactory.brick(host));
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
		volume.getBricks().add(BrickFactory.brick(host1));
		volume.getBricks().add(BrickFactory.brick(host2));
		volume.getBricks().add(BrickFactory.brick(host1));
		volume.getBricks().add(BrickFactory.brick(host2));
		volume.getBricks().add(BrickFactory.brick(host1));
		volume.getBricks().add(BrickFactory.brick(host2));
		return volume;
		
	}
	public static Volume distributedReplicate(String name, int repCount, int numBricks, Host... hosts) {
		Volume volume = new Volume();
		volume.setName(name);
		volume.setType("DISTRIBUTED_REPLICATE");
		volume.setReplicaCount(repCount);
		
		volume.setCluster(hosts[0].getCluster());
		
		ArrayList<Brick> bricks = createBricksUsingHosts(numBricks, hosts);
		volume.setBricks(bricks);
		return volume;
		
	}


	private static ArrayList<Brick> createBricksUsingHosts(int numBricks,
			Host... hosts) {
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		InfiniteIterator<Host, ArrayList<Host>> hostiterator = new InfiniteIterator<Host, ArrayList<Host>>(new ArrayList<Host>(Arrays.asList(hosts)));
		for(int i=0; i< numBricks;i ++){
			bricks.add(BrickFactory.brick(hostiterator.next()));
		}
		return bricks;
	}

}
