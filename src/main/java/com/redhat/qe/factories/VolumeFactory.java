package com.redhat.qe.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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


	private BrickFactory brickFactory;
	
	
	public VolumeFactory(BrickFactory brickFactory){
		this.brickFactory = brickFactory;
	}
	
	public BrickFactory getBrickFactory(){
		return brickFactory;
	}

	public Volume distributed(String name,int numbricks, Host... hosts){
		return create(name, "distribute", numbricks, hosts);
	}
	
	
	public Volume create(String name, String type, int numbricks, Host... hosts){
		return create(name, type, null, null,numbricks, hosts);
	}
	
	public Volume create(String name, String type,Integer repCount, Integer stripeCount, Integer numbricks, Host... hosts){
		Volume volume = new Volume();
		volume.setName(name);
		volume.setType(type);
		if(repCount != null)
			volume.setReplicaCount(repCount);
		if(stripeCount != null)
			volume.setStripe_count(stripeCount);
		volume.setCluster(hosts[0].getCluster());
		
		ArrayList<Brick> bricks = createBricksUsingHosts(numbricks, hosts);
		volume.setBricks(bricks);
		return volume;
	}

	public Volume distributed(String name, Host... hosts){
		return distributed(name, 6, hosts);
	}
	
	public Volume distributedUneven(String name,Host host1, Host host2){
		Volume volume = new Volume();
		volume.setName(name);
		volume.setType("distribute");
		volume.setCluster(host1.getCluster());
		
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		bricks.add(new BrickFactory().brick(host1));
		bricks.add(new BrickFactory().brick(host1));
		bricks.add(new BrickFactory().brick(host1));
		volume.setBricks(bricks);
		return volume;
	}
	
	public Volume distributed(String name, List<Host> hosts ){
		return distributed(name, hosts.toArray(new Host[0]));
	}

	public Volume replicate(String name, Host... hosts) {
		return create(name, "replicate", 2, null, 8, hosts);
	}
	
	public VolumeFactory(){
		this(new BrickFactory());
	}
	
	public Volume distributedReplicate(String name, Host... hosts) {
		return distributedReplicate(name, 2, 6, hosts);
	}

	public Volume distributedReplicate(String name, int repCount, int numBricks, Host... hosts) {
		return create(name, "DISTRIBUTED_REPLICATE", repCount, null, numBricks, hosts);
		
	}

	public Volume distributedStripe(String name, int stripeCount, int numBricks, Host... hosts) {
		return create(name, "DISTRIBUTED_STRIPE", null, stripeCount, numBricks, hosts);
	}


	private ArrayList<Brick> createBricksUsingHosts(int numBricks,
			Host... hosts) {
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		InfiniteIterator<Host, ArrayList<Host>> hostiterator = new InfiniteIterator<Host, ArrayList<Host>>(new ArrayList<Host>(Arrays.asList(hosts)));
		for(int i=0; i< numBricks;i ++){
			bricks.add(getBrickFactory().brick(hostiterator.next()));
		}
		return bricks;
	}

}
