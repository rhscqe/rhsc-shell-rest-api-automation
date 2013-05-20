package com.redhat.qe.test;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.PropertyListParse;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.ssh.BashShell;
import com.redhat.qe.ssh.Response;

import dstywho.regexp.RegexMatch;

public class VersionTest extends ShellSessionTestBase {
//	private static final String VERSION_PATTERN = "(\\d+\\.?)+-?.*[^.noarch]";
	private static final String VERSION_PATTERN = "\\d+\\.\\d+";

	@Test
	@Tcms("250538")
	public void test() {
		BashShell bash = BashShell.fromSsh(session);
		bash.clear();
		String cliVersion = cliVersion(bash);
		String sdkversion = getSdkVersion(bash);
		String pythonversion = getPythonVersion(bash);

		shell = RhscShellSession.fromConfiguration(session, RhscConfiguration.getConfiguration());
		shell.start();
		shell.connect();
		Response info = shell.send("info");
		HashMap<String, String> infoMap = PropertyListParse.parsePropertyList(info.toString());
		Asserts.assertContains("python version", infoMap.get("python version"), pythonversion);
		Asserts.assertContains("entry point", infoMap.get("entry point"),RhscConfiguration.getConfiguration().getRestApi().getUrl());
		Asserts.assertContains("sdk version", infoMap.get("sdk version"),sdkversion);
		Asserts.assertContains("cli version", infoMap.get("cli version"),cliVersion);

	}

	/**
	 * @param bash
	 * @return
	 */
	private String getSdkVersion(BashShell bash) {
		Response sdkverison = bash.send("rpm -qa | grep rhsc-sdk").expect("");
		String sdk_version = new RegexMatch(sdkverison.toString()).find("rhsc-sdk-.*\\.noarch").get(0).find(VERSION_PATTERN).get(0).getText().trim();
		return sdk_version;
	}

	/**
	 * @param bash
	 * @return
	 */
	private String getPythonVersion(BashShell bash) {
		Response pythonVersion = bash.send("python -V").expect("");
		String pversion = new RegexMatch(pythonVersion.toString()).find(VERSION_PATTERN).get(0).getText().trim();
		return pversion;
	}

	/**
	 * @param versionPattern
	 * @param bash
	 * @return
	 */
	private String cliVersion( BashShell bash) {
		Response rhscversions = bash.send("rpm -qa | grep rhsc-cli").expect("");
		String version = new RegexMatch(rhscversions.toString()).find("rhsc-cli-.*\\.noarch").get(0).find(VERSION_PATTERN).get(0).getText().trim();
		return version;
	}

}
