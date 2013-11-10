package com.redhat.qe.helpers.rest;

import java.util.List;

import org.calgb.test.performance.HttpSession;

import com.google.common.base.Function;
import com.redhat.qe.helpers.utils.Null;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;

public class WithEachBrick {

	
	private Brick brick;
	private Host host;
	
	//assume brick list only had host ids
	//need session to refresh host information
	public static void withEachBrick(List<Brick> bricks, HttpSession session, Function<WithEachBrick, Null> eachBrick ) {
		for (final Brick brick : bricks) {
			Host host = brick.getConfiguredHostFromBrickHost(session);
			eachBrick.apply(new WithEachBrick(brick, host));
		}
	}

	/**
	 * @param brick
	 * @param host
	 */
	public WithEachBrick(Brick brick, Host host) {
		super();
		this.brick = brick;
		this.host = host;
	}

	public Brick getBrick() {
		return brick;
	}

	public void setBrick(Brick brick) {
		this.brick = brick;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}
	
	
	
	
	
}
