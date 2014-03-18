package com.redhat.qe.helpers.ssh;

import java.util.List;

import junit.framework.Assert;

import com.google.common.base.Function;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

import dstywho.functional.Predicate;
import dstywho.timeout.Timeout;

public class RebalanceProcessHelper {
	
	public void waitForRebalanceProcessesToFinish(final Host host){
		Assert.assertTrue("wait for rebalance to stop", WaitUtil.waitUntil(new Predicate() {
			
			@Override
			public Boolean act() {
				Timeout.TIMEOUT_FIVE_SECONDS.sleep();
				return Integer.parseInt(ExecSshSession.fromHost(host).withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
					
					public Response apply(ExecSshSession session) {
						session.runCommand("ps uax | grep rebalance | grep -v grep");
						return session.runCommand("ps uax | grep rebalance | grep -v grep | wc | awk '{print $1}'");
					}
				}).getStdout().trim()) <= 0;
			}
		}, 120).isSuccessful());
	}

	public void waitForRebalanceProcessToTerminate(List<Host> hosts) {
		for(Host host: hosts){
		  waitForRebalanceProcessesToFinish(host);
		}
	}
}
