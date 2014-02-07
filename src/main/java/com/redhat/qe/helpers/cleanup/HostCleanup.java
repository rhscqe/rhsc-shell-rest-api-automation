package com.redhat.qe.helpers.cleanup;

import org.junit.Assert;

import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.rest.IHostRepositoryExtended;
import com.redhat.qe.repository.rhscshell.HostRepository;
import com.redhat.qe.ssh.IResponse;

import dstywho.timeout.Duration;

public class HostCleanup {
	/**
	 * @param hostRepository
	 */
	public static void destroyHost(Host host, IHostRepositoryExtended hostRepo) {
		if (host != null) {
			hostRepo.deactivate(host);
			if( ! hostRepo.show(host).getState().equalsIgnoreCase("install_failed"));
			{
				Assert.assertTrue(WaitUtil.waitForHostStatus(hostRepo, host, "maintenance", 400));
			}
			IResponse response = hostRepo._destroy(host);
			if(response.contains("locked") || response.contains("Conflict")){
				Duration.ONE_SECOND.sleep();
				hostRepo.destroy(host);
			}else if(response.contains("complete")){
				return;
			}else{
				throw new UnexpectedReponseException("expecting 'complete'",response);
				
			}
		}
	}
}

