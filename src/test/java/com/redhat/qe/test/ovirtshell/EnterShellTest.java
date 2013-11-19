package com.redhat.qe.test.ovirtshell;

import java.util.regex.Pattern;

import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.ssh.FileHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.ssh.Duration;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class EnterShellTest extends SshSessionTestBase {
	Path ROOT_RHSCSHELLRC = AbsolutePath.from("/root/.rhscshellrc");
	private static final String WELCOME_MESSAGE = "Welcome to (RHSC|OVIRT) shell";

	@Test
	@Tcms("250459")
	public void test() {
		RhscShellSession rhscSession = RhscShellSession
				.fromConfiguration(session);
		rhscSession.start();
		rhscSession.connect();
	}

	@Test
	@Tcms("273548")
	public void configFileTest() {
		StringBuffer createFileCommand = new StringBuffer();
		createFileCommand.append("cat > "
				+ new AbsolutePath(ROOT_RHSCSHELLRC).toString() + " <<EOT\n");
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
		createFileCommand.append(String.format("password = redhat\n",
				RhscConfiguration.getConfiguration().getRestApi()
						.getCredentials().getPassword()));
		createFileCommand.append("EOT\n");

		RhscShellSession rhscSession = RhscShellSession
				.fromConfiguration(session);
		rhscSession.sendAndCollect(createFileCommand.toString());
		try {
			rhscSession
					.send("rhsc-shell -c").collect().expect(WELCOME_MESSAGE);

		} finally {
			ExecSshSession sessionToRemoveFile = ExecSshSession.fromSession(session);
			sessionToRemoveFile.withSession(new Function<ExecSshSession, ExecSshSession.Response>() {

						public Response apply(ExecSshSession sess) {
							return new FileHelper().removeFile(sess,
									new AbsolutePath(ROOT_RHSCSHELLRC));
						}
					});
		}

	}

}
