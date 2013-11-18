package com.redhat.qe.test.rest.hooks;

import org.junit.After;
import org.junit.Before;

import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.helpers.ssh.HookPath;

public abstract class NoConflictHooksTestBase extends HookTestBase {

	private static final String HOOK_CONTENT = "echo 'hi'\n echo 'hi'";
	protected HookPath script;

	public NoConflictHooksTestBase() {
		super();
	}
	
	public String getHookContent(){
		return HOOK_CONTENT;
	}

	@Before
	public void before(){
		script = createHookScripts(getFilename(), getHookContent());
	}

	protected abstract String getFilename();
	@After
	public void after(){
		getHooksRepo().delete(new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script));
	}


}