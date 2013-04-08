package com.redhat.qe.model;

import com.redhat.qe.repository.HostRepository;

import dstywho.functional.Predicate;
import dstywho.timeout.Duration;

public class WaitUtil {
	
	public static boolean waitForHostStatus(HostRepository repo, Host host, String status, int numAttempts){
		int attempt = 0;
		while(attempt < numAttempts){
			try{Thread.sleep(1000);}catch(Exception e){throw new RuntimeException(e);}
			if(repo.show(host).getState().equals(status))
				return true;
			numAttempts++;
		}
		return false;
	}
	
	public static boolean waitUntil(Predicate condition, int numAttempts){
		for(int _ : new int[numAttempts]){
			Duration.ONE_SECOND.sleep();
			if(condition.call())
				return true;
		}
		return false;
	}

}
