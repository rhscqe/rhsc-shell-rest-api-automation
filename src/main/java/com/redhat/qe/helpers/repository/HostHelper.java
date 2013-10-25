package com.redhat.qe.helpers.repository;

import org.apache.log4j.Logger;

import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.IHostRepository;

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
