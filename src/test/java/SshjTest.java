import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;


public class SshjTest {
	@Test
public void test() throws IOException{
	 final SSHClient ssh = new SSHClient();
     ssh.loadKnownHosts();

     ssh.connect("rhsc-qa8");
     try {
    	 ssh.authPassword("root", "redhat");
         final Session session = ssh.startSession();
         try {
             final Command cmd = session.exec("rhsc-shell");
             System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
             cmd.join(5, TimeUnit.SECONDS);
             System.out.println("\n** exit status: " + cmd.getExitStatus());
         } finally {
             session.close();
         }
     } finally {
         ssh.disconnect();
     }
}
}
