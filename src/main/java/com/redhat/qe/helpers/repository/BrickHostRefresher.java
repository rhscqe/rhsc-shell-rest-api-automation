package com.redhat.qe.helpers.repository;

import java.util.Collection;

import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.repository.IHostRepository;

public class BrickHostRefresher {

	private IHostRepository repo;

	public BrickHostRefresher(IHostRepository iHostRepository){
		this.repo = iHostRepository;
	}
	
	public Brick refresh(Brick brick){
		Host host = repo.show(brick.getHost());
		brick.setHost(host);
		return brick;
	}
	
	public <T extends Collection<Brick>> T refresh(T bricks){
		for(Brick brick: bricks){
			refresh(brick);
		}
		return bricks;
	}

}
