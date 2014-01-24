package com.redhat.qe.test.rest.hooks;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.model.Hook;

public class DisableAlreadyDisabledTest extends NoConflictHooksTestBase{
	
	
	@Tcms("322498")
	@Test
	public void test(){
		Hook hookUnderTest = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script);
		Assert.assertTrue(hookUnderTest.getStatus().getState().toLowerCase().equals("disabled"));

		getHooksRepo().disable(hookUnderTest);
		Hook enabledHook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script);
		Assert.assertTrue(enabledHook.getStatus().getState().toLowerCase().equals("disabled"));
		
		assertScriptFilenameIsDisabled(getHost1(),script);
		assertScriptFilenameIsDisabled(getHost2(),script);
	}


	@Override
	protected String getFilename() {
		return "K90test.sh";
	}



}
