package com.redhat.qe.test.rest.hooks;

import java.util.ArrayList;

import junit.framework.Assert;

import org.jclouds.util.Predicates2;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicate;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.ssh.HooksHelper;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.GlusterHookList;
import com.redhat.qe.model.Hook;
import com.redhat.qe.model.Host;
import com.redhat.qe.repository.rest.HookRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

public class EnableHookTest extends TwoHostClusterTestBase{
	
	private HookPath script1;

	@Before
	public void before(){
		script1 = createHookScript(getHost1());
		HookPath script2 = createHookScript(getHost2());
		
		//Sync the scripts 
		getHooksRepo().sync();
	}

	private HookRepository getHooksRepo() {
		HookRepository hooksRepo = new HookRepository(getSession(), getHost1().getCluster());
		return hooksRepo;
	}

	private HookPath createHookScript(Host host) {
		HookPath hook;
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), host));
		host1session.start();
		try{
			hook = new HooksHelper().createAsciiHook(host1session, "1", "test", "pre", "K90test.sh", "echo 'test'");
		}finally{
			host1session.stop();
		}
		return hook;
	}
	
	@Test
	public void test(){
		Hook hookUnderTest = getHookFromHooksList();
		Assert.assertTrue(hookUnderTest.getStatus().getState().toLowerCase().equals("disabled"));
		getHooksRepo().enable(hookUnderTest);
		
		
		Hook enabledHook = getHookFromHooksList();
		Assert.assertTrue(enabledHook.getStatus().getState().toLowerCase().equals("enabled"));
		
		assertScriptNameHasChanged(getHost1());
		assertScriptNameHasChanged(getHost2());
	}

	private void assertScriptNameHasChanged(Host host) {
		Path expectedNewfileName = script1.getDirectories().add(script1.getPath().last().replaceAll("^[a-zA-z]*", "S"));
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), host));
		host1session.start();
		try{
			ArrayList<HookPath> listofhookfiles = new HooksHelper().listHooks(host1session);
			listofhookfiles.get(1).getPath().equals(expectedNewfileName);
			Assert.assertTrue(listofhookfiles.contains(new HookPath(expectedNewfileName)));
		}finally{
			host1session.stop();		
		}
	}

	private Hook getHookFromHooksList() {
		ArrayList<Hook> hooks = getHooksRepo().list();
		Hook hookUnderTest = CollectionUtils.findFirst(hooks, new Predicate<Hook>(){

			public boolean apply(Hook hook) {
				return hook.getName().equals(script1.getRestApiCannonicalName());
			}});
		return hookUnderTest;
	}

}
