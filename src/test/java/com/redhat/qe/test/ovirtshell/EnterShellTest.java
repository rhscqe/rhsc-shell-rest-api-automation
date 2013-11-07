package com.redhat.qe.test.ovirtshell;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.ovirt.shell.RhscShellSession;

public class EnterShellTest extends SshSessionTestBase{
	private static final String WELCOME_MESSAGE = "Welcome to (RHSC|OVIRT) shell";

	@Test
	@Tcms("250459")
	public void test(){
		RhscShellSession rhscSession = RhscShellSession.fromConfiguration(session);
		rhscSession.send("rhsc-shell || ovirt-shell ").expect(WELCOME_MESSAGE);
	}

	@Test
	@Tcms("273548")
	public void configFileTest(){
		StringBuffer createFileCommand = new StringBuffer();
		createFileCommand.append("cat > /root/.rhscshellrc <<EOT\n");
		createFileCommand.append("[cli]\n");                                                                                                                                                                            
		createFileCommand.append("autopage = True\n");
		createFileCommand.append("[ovirt-shell]\n");
		createFileCommand.append("username = admin@internal\n");
		createFileCommand.append("url = https://localhost:443/api\n");
		createFileCommand.append("insecure = True\n");
		createFileCommand.append("filter = False\n");
		createFileCommand.append("session_timeout = -1\n");
		createFileCommand.append("timeout = -1\n");
		createFileCommand.append("dont_validate_cert_chain = False\n");
		createFileCommand.append(String.format("password = redhat\n", RhscConfiguration.getConfiguration().getRestApi().getCredentials().getPassword()));
		createFileCommand.append("EOT\n");
		
		RhscShellSession rhscSession = RhscShellSession.fromConfiguration(session);
		rhscSession.send(createFileCommand.toString());
		rhscSession.send("rhsc-shell -c").expect(WELCOME_MESSAGE);
	}

}
