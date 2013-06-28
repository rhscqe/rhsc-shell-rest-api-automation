package com.redhat.qe.helpers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.redhat.reportengine.client.RemoteAPI;

public class MyListener extends RunListener {

	private static final String REPORT_ENGINE_ID_ENV_VAR = "RHSC_SHELL_TEST_REPORT_ENGINE_ID";
	private static final String DEFAULT_REPORT_ID = "RHSC-CLI_OVERNIGHT";
	private static RemoteAPI reportEngine = new RemoteAPI();
	HashMap<String, Failure> descriptionToFailure = new HashMap<String, Failure>();

	public MyListener() throws UnknownHostException, Exception {
		reportEngine.initClient(getReportId());
	}
	
	String getReportId(){
		String result = System.getenv(REPORT_ENGINE_ID_ENV_VAR);
		if(result ==null){
			return DEFAULT_REPORT_ID;
		}else{
			return result;
		}
		
	}

	/**
	 * Called before any tests have been run.
	 * 
	 * @param description
	 *            describes the tests to be run
	 */
	public void testRunStarted(Description description) throws Exception {
		if (reportEngine.isClientConfigurationSuccess()) {
			try {
				reportEngine.insertTestGroup("all tests");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Called when all tests have finished
	 * 
	 * @param result
	 *            the summary of the test run, including all the tests that
	 *            failed
	 */
	public void testRunFinished(Result result) throws Exception {
		if (reportEngine.isClientConfigurationSuccess()) {
			try {
				reportEngine.updateTestSuite("Completed", getBuildVersion());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String getBuildVersion() {
		return System.getProperty(reportEngine.getBuildVersionReference());
	}

	/**
	 * Called when an atomic test is about to be started.
	 * 
	 * @param description
	 *            the description of the test that is about to be run (generally
	 *            a class and method name)
	 */
	public void testStarted(Description description) throws Exception {
		if (reportEngine.isClientConfigurationSuccess()) {
			try {
				reportEngine.insertTestCase(description.getDisplayName(), "Running");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Called when an atomic test has finished, whether the test succeeds or
	 * fails.
	 * 
	 * @param description
	 *            the description of the test that just ran
	 */
	public void testFinished(Description description) throws Exception {
		Failure failure = descriptionToFailure.get(description.getDisplayName());
		if (reportEngine.isClientConfigurationSuccess()) {
			try {
				if (failure == null) { // Successful test
					reportEngine.updateTestCase("Passed");

				} else {
					reportEngine.takeScreenShot();
					reportEngine.updateTestCase("Failed", failure.getTrace());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Called when an atomic test fails.
	 * 
	 * @param failure
	 *            describes the test that failed and the exception that was
	 *            thrown
	 */
	public void testFailure(Failure failure) throws Exception {
		if (reportEngine.isClientConfigurationSuccess()) {
			try {
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			descriptionToFailure.put(failure.getDescription().getDisplayName(), failure);
		}
	}

}
