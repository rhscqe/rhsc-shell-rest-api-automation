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

public class ResolveContentContentMissingByAddFromEngineTest extends NoConflictHooksTestBase{

	private String expectedCheckSum;

	@Override
	protected String getFilename() {
		return "S20addmissingfromengine.sh";
	}
	
	
	@Before
	public void beforethis(){
		ExecSshSession host1Session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		host1Session.start();
		try{
			expectedCheckSum = new FileHelper().getMd5Sum(host1Session, new AbsolutePath(script.getPath())).getStdout();
			new FileHelper().removeFile(host1Session, new AbsolutePath(script.getPath()));
		}finally{
			host1Session.stop();
		}
		getHooksRepo().sync();
		
		Hook hook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script);
		Assert.assertTrue(hook.getConflicts().contains("MISSING_HOOK"));
	}
	
	@Tcms("323357")
	@Test
	public void test(){
		Hook conflictedHook = new HookRepoHelper().getHookFromHooksList( getHooksRepo(), script);

		HookResolutionAction resolution = new HookResolutionAction();
		resolution.setResolutionType("ADD");
		getHooksRepo().resolve(conflictedHook, resolution);
		
		String actualmd5Sum = withHostSession(getHost1(), new Function<ExecSshSession, String>() {
			
			public String apply(ExecSshSession session) {
				Response md5SumResponse = new FileHelper().getMd5Sum(session, new AbsolutePath(script.getPath()));
				Assert.assertTrue(md5SumResponse.isSuccessful());
				return md5SumResponse.getStdout();
			}
		});
		Assert.assertEquals( expectedCheckSum, actualmd5Sum);
		String actualContent= getFileContents(getHost1(),script).getStdout();
		Assert.assertEquals(getHookContent().trim(), actualContent.trim());
		
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
