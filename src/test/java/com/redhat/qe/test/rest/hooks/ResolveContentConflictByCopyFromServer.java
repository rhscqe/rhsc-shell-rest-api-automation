package com.redhat.qe.test.rest.hooks;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.helpers.ssh.FileHelper;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Hook;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.jaxb.HookResolutionAction;
import com.redhat.qe.repository.rest.HookRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class ResolveContentConflictByCopyFromServer extends ContentConflictHookTestBase{


	@Override
	String getFilename() {
		return "S20contentconflict.sh";
	}

	@Tcms("322502")
	@Test
	public void test(){
		Hook conflictedHook = new HookRepoHelper().getHookFromHooksList( getHooksRepo(), script);

		HookResolutionAction resolution = new HookResolutionAction();
		resolution.setResolutionType("COPY");
		resolution.setHost(host2);
//	TODO log a bug against improper rsdl. HookResolutionActionHost host = new HookResolutionActionHost();
//		host.populateFromHost(getHost1());
//		resolution.setHost(host);
		Action resolutionResult = getHooksRepo().resolve(conflictedHook, resolution);

		
		Hook resolvedHook= new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script);
		Assert.assertTrue(resolvedHook.getConflicts()== null || resolvedHook.getConflicts().isEmpty());
		
		String actualContent= getFileContents(getHost1(),script).getStdout();
		Assert.assertEquals(HOST2_CONTENT, actualContent.trim());
		Assert.assertEquals(HOST2_CONTENT,  getFileContents(getHost2(),script).getStdout().trim());
		Assert.assertEquals(HOST2_CONTENT, getHooksRepo().show(conflictedHook).getContent().trim());
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
