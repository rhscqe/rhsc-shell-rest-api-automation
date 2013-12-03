package com.redhat.qe.helpers.mechanize;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.calgb.test.performance.BuildPostException;
import org.calgb.test.performance.HttpSession;
import org.calgb.test.performance.HttpSession.HttpProtocol;
import org.calgb.test.performance.PostRequestFactory;
import org.calgb.test.performance.ProcessResponseBodyException;
import org.calgb.test.performance.RequestException;
import org.calgb.test.performance.UseSslException;


public class GuiSync {
 public static void sync() throws UseSslException, ProcessResponseBodyException, RequestException, BuildPostException, UnsupportedEncodingException{
//	HttpSession session = new HttpSession("latest", 443, HttpProtocol.HTTPS ); 
//	session.sendTransaction(new HttpGet("/webadmin/webadmin/WebAdmin.html#login"));
////	session.useBasicAuthentication("admin@internal", "redhat");
//	HttpPost post = new PostRequestFactory().buildPost("/webadmin/webadmin/GenericApiGWTService", "R9~\"61~org.ovirt.engine.ui.frontend.gwtservices.GenericApiGWTService~\"5~Login~D3~\"3~foe~@2~@2~\"5~admin~\"6~redhat~\"8~internal~");
//	post.setHeader("Referer", "https://latest/webadmin/webadmin/WebAdmin.html");
//	post.setHeader("X-GWT-Permutation", "BE28CFC01EFEECE4FC9B3B706120412E");
//	post.setHeader("X-GWT-Module-Base",	"https://latest/webadmin/webadmin/");
//	post.setHeader("Content-Type", "text/x-gwt-rpc; charset=utf-8");
//	post.setHeader("Accept-Encoding", "gzip, deflate");
//	post.setHeader("Accept",	"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//	post.setHeader("User-Agent",	"Mozilla/5.0 (X11; Linux x86_64; rv:17.0) Gecko/20100101 Firefox/17.0");
//	session.sendTransaction(post);
//
//	//call sync on something
//	post.setEntity(new StringEntity("R7~\"61~org.ovirt.engine.ui.frontend.gwtservices.GenericApiGWTService~\"9~RunAction~D2~\"3~Vaf~\"3~hXe~Eorg.ovirt.engine.core.common.action.VdcActionType~I220~Lorg.ovirt.engine.core.common.action.gluster.GlusterClusterParameters~I17~\"1~b~Lorg.ovirt.engine.core.compat.Guid~I1~@6~!java.util.UUID~I2~J-6707963047186886080~J2460671514011453512~I0~\"1~G~V~\"1~H~Eorg.ovirt.engine.core.common.action.VdcActionType~I0~\"1~I~Z0~\"1~J~V~\"1~K~V~\"1~L~D0~\"1~M~!java.util.ArrayList~I1~D0~I0~\"1~N~V~\"1~O~Z0~\"1~P~V~\"1~Q~Eorg.ovirt.engine.core.common.action.VdcActionType~I0~\"1~S~Z1~\"1~T~V~\"1~U~Z1~\"1~V~Eorg.ovirt.engine.core.compat.TransactionScopeOption~I2~\"1~W~V~"));
//	session.sendTransaction(post);
 }
}
