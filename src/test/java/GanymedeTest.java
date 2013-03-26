import java.io.IOException;
import java.io.OutputStreamWriter;

import net.schmizz.sshj.common.IOUtils;

import org.junit.Test;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;


public class GanymedeTest {
	@Test
	public void test() throws IOException{
		Connection conn = new Connection("rhsc-qa8");

        /* Now connect */

        conn.connect();

        /* Authenticate.
         * If you get an IOException saying something like
         * "Authentication method password not supported by the server at this stage."
         * then please check the FAQ.
         */

        boolean isAuthenticated = conn.authenticateWithPassword("root", "redhat");
        Session sess = conn.openSession();
        sess.execCommand("echo hi");
        System.out.println(IOUtils.readFully(sess.getStdout()).toString());
        sess.close();
        sess = conn.openSession(); 
        sess.requestPTY("bash");
        sess.startShell();
        
        OutputStreamWriter out = new OutputStreamWriter(sess.getStdin(), "utf-8");
        out.write("rhsc-shell"); out.flush();
        out.write("hi"); out.flush();
        sess.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF |
        		ChannelCondition.EXIT_STATUS, 10000);
        System.out.println(IOUtils.readFully(sess.getStderr()).toString());
        System.out.println(IOUtils.readFully(sess.getStdout()).toString());
       
         
        
	}

}
