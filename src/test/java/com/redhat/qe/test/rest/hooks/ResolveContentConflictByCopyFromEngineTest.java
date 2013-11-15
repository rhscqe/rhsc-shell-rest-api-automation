package com.redhat.qe.test.rest.hooks;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.helpers.ssh.FileHelper;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Hook;
import com.redhat.qe.model.HookResolutionAction;
import com.redhat.qe.model.Host;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class ResolveContentConflictByCopyFromEngineTest extends ContentConflictHookTestBase{


	@Override
	protected String getFilename() {
		return "s34contentconflict.sh";
	}
	
	
	@Tcms("322501")
	@Test
	public void test(){
		Hook conflictedHook = new HookRepoHelper().getHookFromHooksList( getHooksRepo(), script);

		HookResolutionAction resolution = new HookResolutionAction();
		resolution.setResolutionType("COPY");
		getHooksRepo().resolve(conflictedHook, resolution);
		
		Assert.assertEquals(initialContent.trim(), getFileContents(getHost1(),script).getStdout().trim());
		Assert.assertEquals(initialContent.trim(), getFileContents(getHost2(),script).getStdout().trim());
		
	}
	
	private Response getFileContents(Host host, HookPath script){
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), host));
		host1session.start();
		try{
			return new FileHelper().getFilecontents(host1session, new AbsolutePath(script.getPath()));
		}finally{
			host1session.stop();		
		}
	}
	
	

}
