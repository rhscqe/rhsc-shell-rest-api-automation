package com.redhat.qe.test.rest.hooks;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.model.Hook;

public class ResolveStatusConflictByEnablingHooksTest extends StatusConflictHooksTestBase{


	@Test
	@Tcms("322499")
	public void test(){
		Hook conflictedHook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1);
		getHooksRepo().enable(conflictedHook);
		
		
		Hook enabledHook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1);
		Assert.assertTrue(enabledHook.getStatus().getState().toLowerCase().equals("enabled"));
		
		assertScriptFilenameIsEnabled(getHost1(), script1);
		assertScriptFilenameIsEnabled(getHost2(), script2);
	
		
	}

	@Override
	String getPartialFileName() {
		return "test.sh";
	}
	
}
