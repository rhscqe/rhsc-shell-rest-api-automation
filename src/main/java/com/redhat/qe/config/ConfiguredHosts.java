package com.redhat.qe.config;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;

public class ConfiguredHosts {
	private List<Host> hosts;

	public ConfiguredHosts(List<Host> hosts){
		this.hosts = hosts;
	}
	
	public Host getHostByName(final Host host){
		return CollectionUtils.findFirst(hosts, new Predicate<Host>() {

			public boolean apply(Host input) {
				return input.getName().equals(host.getName());
			}
		});

	}
	
	public Host getHost(final Brick brick){
		return getHostByName(brick.getHost());
	}
}
