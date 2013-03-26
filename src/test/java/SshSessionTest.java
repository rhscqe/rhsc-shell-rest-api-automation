import org.junit.Test;

import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.Shell;
import com.redhat.qe.ssh.SshSession;


public class SshSessionTest {
	@Test
	public void test() {
		SshSession session = new SshSession(new Credentials("root", "redhat"), "rhsc-qa8");
		session.start();
		try {
			Shell shell = session.getShell();
			System.out.println(shell.send("rhsc-shell"));
			System.out.println(shell.send("connect --url 'https://localhost:443/api' --user 'admin@internal' --password 'redhat' -I"));
			System.out.println(shell.send(""));
		} finally {
			session.stop();
		}
	}


}
