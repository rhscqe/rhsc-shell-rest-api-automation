import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class JschTest {
	
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
//         final StringBuffer buffer = new StringBuffer();
//			new Thread(new Runnable() {
//				public void run() {
//					String line;
//					try {
//						while((line = fromChannel.readLine()) != null){
//							Thread.currentThread().sleep(10);
//							buffer.append(line + "\nbuffer:");
//							System.out.println("out" + line);
//						}
//					} catch (IOException e) {
//						throw new RuntimeException(e);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}).start();
		 
			 shellStream.println("rhsc-shell"); shellStream.flush();
			 shellStream.println("hihi"); shellStream.flush();
//			 System.out.println("buffer:" + buffer.toString()); buffer.setLength(0);
			 String line;
			 Thread.sleep(10000);
			 while(inStream.available() > 0 && (line = fromChannel.readLine()) != null){
					System.out.println("out" + line);
			 }
			 
			 
			 
//			 shellStream.println("hihihi"); shellStream.kflush();Thread.sleep(1000);
//			 System.out.println("buffer:" + buffer.toString()); buffer.setLength(0);
//			 shellStream.println("pwd"); shellStream.flush();Thread.sleep(1000);
//			 System.out.println("buffer:" + buffer.toString()); buffer.setLength(0);
//			 shellStream.println("touch a"); shellStream.flush();Thread.sleep(1000);
//			 System.out.println("buffer:" + buffer.toString()); buffer.setLength(0);
//			 
		 //System.out.println(IOUtils.toString(channel.getInputStream(), "UTF-8"));
		 
		 channel.disconnect();
		 session.disconnect();
	}
}