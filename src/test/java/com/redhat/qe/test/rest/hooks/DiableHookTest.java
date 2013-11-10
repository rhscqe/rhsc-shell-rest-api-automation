package com.redhat.qe.test.rest.hooks;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.Hook;
import com.redhat.qe.model.Host;
import com.redhat.qe.repository.rest.HookRepository;

public class DiableHookTest extends HooksTestBase{
	
	private HookPath script1;

	@Before
	public void before(){
		String filename = "S90test.sh";
		script1 = createHookScript(getHost1(),filename);
		createHookScript(getHost2(),filename);
		
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

	@Tcms("322496")
	@Test
	public void test(){
		Hook hookUnderTest = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1);
		Assert.assertTrue(hookUnderTest.getStatus().getState().toLowerCase().equals("enabled"));
		getHooksRepo().disable(hookUnderTest);
		
		
		Hook disabledHook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1);
		Assert.assertTrue(disabledHook.getStatus().getState().toLowerCase().equals("disabled"));
		
		assertScriptNameHasChanged(getHost1());
		assertScriptNameHasChanged(getHost2());
	}

	private void assertScriptNameHasChanged(Host host) {
		Path expectedNewfileName = script1.getDirectories().add(script1.getPath().last().replaceAll("^[a-zA-z]*", "K"));
		ensureHookFileExistsonHost(host, expectedNewfileName);
	}




}
