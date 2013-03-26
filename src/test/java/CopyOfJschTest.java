import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;
import org.junit.Test;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.redhat.qe.ssh.ReadInput;


public class CopyOfJschTest {
	
//	@Test
//	public void test2() throws JSchException{
//		JSch jsch=new JSch();  
//		Session session = jsch.getSession("root", "rhsc-qa8",22);
//		session.setPassword("redhat");
//		session.setConfig("StrictHostKeyChecking", "no");
//		session.connect();
//		
//		 ChannelExec channel = (ChannelExec) session.openChannel("exec");
//		 channel.get
//		
//	}
//	
	


	
	@Test
	public void test() throws Exception{
		JSch jsch=new JSch();  
		Session session = jsch.getSession("root", "rhsc-qa8",22);
		session.setPassword("redhat");
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		Channel channel=session.openChannel("shell");
		 
		 //channel.setInputStream(in);
		 channel.connect();
		 
		 
		 PrintStream shellStream = new PrintStream(channel.getOutputStream());  // printStream for convenience
		 //BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
		 InputStream inStream = channel.getInputStream();
         final BufferedReader fromChannel = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
		 
			 shellStream.println("rhsc-shell"); shellStream.flush();
			 System.out.println("1" + new ReadInput(inStream).call());
			 shellStream.println("hihi"); shellStream.flush();
			 System.out.println("2" + new ReadInput(inStream).call());
			 shellStream.println("exit"); shellStream.flush();
			 System.out.println("3" + new ReadInput(inStream).call());
	}
}