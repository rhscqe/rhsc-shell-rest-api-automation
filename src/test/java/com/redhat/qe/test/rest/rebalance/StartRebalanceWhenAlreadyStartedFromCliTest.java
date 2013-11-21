package com.redhat.qe.test.rest.rebalance;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.jaxb.ActionUnmarshaller;
import com.redhat.qe.model.GeneralAction;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.gluster.Task;
import com.redhat.qe.repository.glustercli.VolumeRepository;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;
import com.redhat.qe.test.rest.RebalanceTestBase;

public class StartRebalanceWhenAlreadyStartedFromCliTest extends RebalanceTestBase{
	@Test
	@Tcms("311348")
	public void test() throws InterruptedException{
		Thread cli = new Thread(new Runnable() {
			
			public void run() {
				ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1())).withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
					
					public Response apply(ExecSshSession host1) {
						new VolumeRepository(host1)._rebalance(volume);
						return null;
					}
				});
			}		
		});
		
		
		Thread rest= new Thread(new Runnable() {
			
			public void run() {
				ResponseWrapper resp = getVolumeRepository(getHost1().getCluster())._rebalance(volume);
				Assert.assertTrue(200 != resp.getCode());
				new ActionUnmarshaller().unmarshalResult(resp);
				Assert.assertTrue(resp.getBody().contains("in progress"));
			}
		});
		cli.start();
		Thread.sleep(500);
		rest.start();
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributedUneven("startrebalwhenclistart", getHost1(), getHost2());
	}
	
	

}
