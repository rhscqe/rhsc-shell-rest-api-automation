package com.redhat.qe.helpers.repository;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.IHostRepository;
import com.redhat.qe.ssh.ExecSshSession;

public class HostHelper {
	private static Logger LOG = Logger.getLogger(HostHelper.class);

	public Host createAndWaitForUp(IHostRepository repo, Host host) {
		host = repo.createOrShow(host);
		if(host.getState().equals("maintenance"))
			repo.activate(host);
		WaitUtil.waitForHostStatus(repo, host, "up", 30);
		return host;
	}
	
	

}
