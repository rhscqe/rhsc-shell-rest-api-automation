package com.redhat.qe.test.rest.hooks;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.model.Hook;

public class ResolveStatusConflictByDisablingHooksTest extends StatusConflictHooksTestBase{


	@Test
	@Tcms("322500")
	public void test(){
		Hook conflictedHook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1);
		getHooksRepo().disable(conflictedHook);
		
		
		Hook enabledHook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script1);
		Assert.assertTrue(enabledHook.getStatus().getState().toLowerCase().equals("disabled"));
		
		assertScriptFilenameIsDisabled(getHost1(), script1);
		assertScriptFilenameIsDisabled(getHost1(), script2);
		
	}

	@Override
	String getPartialFileName() {
		return "90test.sh";
	}
	
}
