package com.redhat.qe.test.rest.hooks;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;

import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.ssh.HooksHelper;
import com.redhat.qe.model.Hook;
import com.redhat.qe.model.Host;
import com.redhat.qe.ssh.ExecSshSession;

public abstract class ContentConflictHookTestBase extends HookTestBase {

	
	public String initialContent;
	protected HookPath script;
	String HOST2_CONTENT;

	@Before
	public void before(){
		initialContent = "echo hi";
		script = createHookScripts(getFilename(), initialContent);
		
		createHookScript(getHost1(), getFilename(), "host1Content");
		createHookScript(getHost2(), getFilename(), HOST2_CONTENT);
		
		getHooksRepo().sync();
		
		Hook hook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script);
		Assert.assertTrue(hook.getConflicts().contains("CONTENT_CONFLICT"));
	}

	
	abstract String getFilename() ;
	
	
	public void changeContent(Host host){
		ExecSshSession session_ = ExecSshSession.fromHost(host);
		
		session_.start();
		try{
			new HooksHelper().createAsciiHook(session_, script, "jesuis=tresbien");
		}finally{
			session_.stop();
		}
	}

	@After
	public void after(){
		getHooksRepo().delete(new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script));
	}
}