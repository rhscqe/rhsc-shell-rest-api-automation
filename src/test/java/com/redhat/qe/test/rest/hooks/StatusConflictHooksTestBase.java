package com.redhat.qe.test.rest.hooks;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;

import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.ssh.HooksHelper;
import com.redhat.qe.model.Hook;

public abstract class StatusConflictHooksTestBase extends HookTestBase {

	protected HookPath script1;
	protected HookPath script2;

	public StatusConflictHooksTestBase() {
		super();
	}
	
	
	@Before
	public void before(){
		script1 = createHookScript(getHost1(), "K" + getPartialFileName());
		script2 = createHookScript(getHost2(), "S" + getPartialFileName());
		
		getHooksRepo().sync();

		Hook hook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1);
		Assert.assertTrue(hook.getConflicts().contains("STATUS_CONFLICT"));
	}
	
	abstract String getPartialFileName() ;

	@After
	public void after(){
		getHooksRepo().delete(new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1));
	}


}