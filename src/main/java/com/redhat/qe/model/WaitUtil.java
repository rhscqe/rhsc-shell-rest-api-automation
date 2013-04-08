package com.redhat.qe.model;

import com.redhat.qe.repository.HostRepository;

import dstywho.functional.Predicate;
import dstywho.timeout.Duration;

public class WaitUtil {

	public static boolean waitForHostStatus(HostRepository repo, Host host, String status, int numAttempts) {
		for (int _ : new int[numAttempts]) {
			Duration.ONE_SECOND.sleep();
			if (repo.show(host).getState().equals(status))
				return true;
		}
		return false;
	}

	public static boolean waitUntil(Predicate condition, int numAttempts) {
		for (int _ : new int[numAttempts]) {
			Duration.ONE_SECOND.sleep();
			if (condition.call())
				return true;
		}
		return false;
	}

}
