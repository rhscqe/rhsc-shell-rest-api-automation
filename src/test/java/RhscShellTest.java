import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.SshSession;



public class RhscShellTest {
	
	private static SshSession session;
	private static RhscShell shell;


	@BeforeClass
	public static void before(){
		session = new SshSession(new Credentials("root", "redhat"), "rhsc-qa8");
		session.start();
		shell = new RhscShell(session, "https://localhost:443/api", new Credentials("admin@internal", "redhat"));
		shell.start();
		shell.connect();
	}
	
	@AfterClass
	public static void after(){
		session.stop();
	}


	@Test
	public void test() {
		shell.send("hi").expect("aadlkfja");
	}

}
