package com.redhat.qe.test.rest.hooks;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.model.Hook;

public class DiableHookTest extends HooksTestBase{
	



	@Tcms("322496")
	@Test
	public void test(){
		Hook hookUnderTest = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script);
		Assert.assertTrue(hookUnderTest.getStatus().getState().toLowerCase().equals("enabled"));
		getHooksRepo().disable(hookUnderTest);
		
		
		Hook disabledHook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script);
		Assert.assertTrue(disabledHook.getStatus().getState().toLowerCase().equals("disabled"));
		
		assertScriptFilenameIsDisabled(getHost1());
		assertScriptFilenameIsDisabled(getHost2());
	}



	@Override
	protected String getFilename() {
		return "S90test.sh";
	}




}
