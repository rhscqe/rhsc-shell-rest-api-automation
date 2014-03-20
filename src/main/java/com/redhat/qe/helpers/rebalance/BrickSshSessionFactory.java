package com.redhat.qe.helpers.rebalance;

import com.redhat.qe.config.ConfiguredHosts;
import com.redhat.qe.model.Brick;
import com.redhat.qe.ssh.ExecSshSession;

public class BrickSshSessionFactory {
	
	private ConfiguredHosts hosts;

	public BrickSshSessionFactory(ConfiguredHosts hosts){
		this.hosts = hosts;
	}
	
	public ExecSshSession getSshSession(Brick brick){
		return ExecSshSession.fromHost(hosts.getHost(brick));
	}

}
