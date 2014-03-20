package com.redhat.qe.helpers.repository;

import java.util.ArrayList;

import com.google.common.base.Predicate;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.model.WaitUtil.WaitResult;
import com.redhat.qe.repository.glustercli.HostRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.Duration;

import dstywho.timeout.Timeout;

public class VolumeRepositoryHelper {

	private static final int NUM_ATTEMPTS = 3;

	
	static class StopVolumeResult{
		private WaitResult result;
		private ResponseWrapper lastRespsonse;
		
		
		
		/**
		 * @param result
		 * @param lastRespsonse
		 */
		public StopVolumeResult(WaitResult result, ResponseWrapper lastRespsonse) {
			super();
			this.result = result;
			this.lastRespsonse = lastRespsonse;
		}
		public WaitResult getResult() {
			return result;
		}
		public void setResult(WaitResult result) {
			this.result = result;
		}
		public ResponseWrapper getLastRespsonse() {
			return lastRespsonse;
		}
		public void setLastRespsonse(ResponseWrapper lastRespsonse) {
			this.lastRespsonse = lastRespsonse;
		}
		
		
	}

	public StopVolumeResult stopVolume(final VolumeRepository repo, final Volume volume) {
		ResponseWrapper lastResponse = null;
		for(int i=0; i< NUM_ATTEMPTS; i++){
			lastResponse = repo._stop(volume);
			if(lastResponse.isCodeSimilar(200))
				return new StopVolumeResult(new WaitResult(true, i, null), lastResponse);
			else
				Timeout.TIMEOUT_ONE_SECOND.sleep();
		}
		return new StopVolumeResult(new WaitResult(false, NUM_ATTEMPTS, null), lastResponse);
	}
	
	


}
