package com.redhat.qe.model;


import org.apache.log4j.Logger;

import com.redhat.qe.repository.HostRepository;

import dstywho.functional.Predicate;
import dstywho.timeout.Duration;

public class WaitUtil {
	private static Logger LOG = Logger.getLogger(WaitUtil.class);

	public static boolean waitForHostStatus(final HostRepository repo, final Host host, final String status, int numAttempts) {
		long starttime = System.currentTimeMillis();
		boolean result = waitUntil(new Predicate() {
			
			@Override
			public Boolean act() {
				return repo.show(host).getState().equals(status);
			}
		}, numAttempts);
		if(result){
			LOG.debug(String.format("succeeded in waiting for host status %s. Number of Attmepts: %s. Elapsed Time(ms):", status, numAttempts, System.currentTimeMillis() - starttime));
		}else{
			LOG.debug(String.format("failed in waiting for host status %s.host status Number of Attmepts: %s. Elapsed Time(ms):", status, numAttempts, System.currentTimeMillis() - starttime));
		}
		return result;
			
	}

	public static boolean waitUntil(Predicate condition, int numAttempts) {
		for (int _ : new int[numAttempts]) {
			Duration.TEN_SECONDS.sleep();
			if (condition.call())
				return true;
		}
		return false;
	}

}
