package com.redhat.qe.test.rest.hooks;
 
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.helpers.ssh.FileHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Hook;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.self.HookHelperTest;
 
public class ShowHookTest extends NoConflictHooksTestBase{
 
    private String expectedMD5Sum;
 
 
    @Override
    protected String getFilename() {
        return "S90showHookTest.sh";
    }
 
    @Override
    public String getHookContent(){
         
//      String allChars = "";
//      for(int i=0 ; i < 255; i ++){
//          allChars = allChars + "\\" + String.format("%c",i);
//      }
        StringBuilder result = new StringBuilder();
        result.append("#!/bin/sh\n" );
        result.append("echo \"this is the radical\"\n" );
        result.append("echo '" + "!@#$%^&*( )1234567890-="+ "'" );
        return result.toString();
    }
     
    @Before
    public void beforethese(){
        expectedMD5Sum = withHostSession(getHost1(), new Function<ExecSshSession, String>() {
             
            public String apply(ExecSshSession arg0) {
                return new FileHelper().getMd5Sum(arg0, new AbsolutePath(script.getPath())).getStdout();
            }
        });
         
    }
     
    @Tcms("322504")
    @Test
    public void test(){
        Hook hook = new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script);
        hook = getHooksRepo().show(hook);
        Assert.assertEquals("content", getHookContent().trim(),hook.getContent().trim());
        Assert.assertEquals("checksum", expectedMD5Sum,hook.getChecksum());
         
    }
}