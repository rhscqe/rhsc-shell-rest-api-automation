package com.redhat.qe.test.rest.hooks;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.ssh.HooksHelper;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.Hook;
import com.redhat.qe.model.Host;
import com.redhat.qe.repository.rest.HookRepository;
import com.redhat.qe.ssh.ExecSshSession;

public class EnableHookTest extends HooksTestBase{
	
	private HookPath script1;

	@Before
	public void before(){
		String filename = "K90test.sh";
		script1 = createHookScript(getHost1(),filename);
		HookPath script2 = createHookScript(getHost2(),filename);
		
		//Sync the scripts 
		getHooksRepo().sync();
	}
	
	@After
	public void after(){
		getHooksRepo().delete(new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1));
		
	}

	private HookRepository getHooksRepo() {
		HookRepository hooksRepo = new HookRepository(getSession(), getHost1().getCluster());
		return hooksRepo;
	}

	@Tcms("322495")
	@Test
	public void test(){
		Hook hookUnderTest = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1);
		Assert.assertTrue(hookUnderTest.getStatus().getState().toLowerCase().equals("disabled"));
		getHooksRepo().enable(hookUnderTest);
		
		
		Hook enabledHook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1);
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


}
