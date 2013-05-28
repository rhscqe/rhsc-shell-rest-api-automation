package com.redhat.qe.helpers;

import org.junit.Assert;

import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.HostRepository;
import com.redhat.qe.ssh.Response;

import dstywho.timeout.Duration;

public class HostCleanup {
	/**
	 * @param hostRepository
	 */
	public static void destroyHost(Host host, HostRepository hostRepo) {
		if (host != null) {
			hostRepo._deactivate(host);
			Assert.assertTrue(WaitUtil.waitForHostStatus(hostRepo, host, "maintenance", 400));
			Response response = hostRepo._destroy(host, null);
			if(response.contains("locked")){
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

