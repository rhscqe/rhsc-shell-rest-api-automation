package com.redhat.qe.helpers;

import java.net.UnknownHostException;
import java.util.HashMap;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import com.redhat.reportengine.client.RemoteAPI;
import com.redhat.reportengine.client.ReportEngineClientJunitListener;

public class MyListener extends ReportEngineClientJunitListener {

	private static final String REPORT_ENGINE_ID_ENV_VAR = "RHSC_SHELL_TEST_REPORT_ENGINE_ID";
	private static final String DEFAULT_REPORT_ID = "RHSC-CLI_OVERNIGHT";
	private static final RemoteAPI reportEngine = new RemoteAPI();
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
//	@Override
//	public void testRunStarted(Description description) {
//		if (reportEngine.isClientConfigurationSuccess()) {
//			try {
//				reportEngine.insertTestGroup("all tests");
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
//	}


//	public static String getBuildVersion() {
//		return System.getProperty(reportEngine.getBuildVersionReference());
//	}


	/**
	 * Called when an atomic test has finished, whether the test succeeds or
	 * fails.
	 * 
	 * @param description
	 *            the description of the test that just ran
	 */
//	public void testFinished(Description description) {
//		Failure failure = descriptionToFailure.get(description.getDisplayName());
//		if (reportEngine.isClientConfigurationSuccess()) {
//			try {
//				if (failure == null) { // Successful test
//					reportEngine.updateTestCase("Passed");
//
//				} else {
//					reportEngine.takeScreenShot();
//					reportEngine.updateTestCase("Failed", failure.getTrace());
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

	/**
	 * Called when an atomic test fails.
	 * 
	 * @param failure
	 *            describes the test that failed and the exception that was
	 *            thrown
	 */
//	public void testFailure(Failure failure){
//		if (reportEngine.isClientConfigurationSuccess()) {
//			try {
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			descriptionToFailure.put(failure.getDescription().getDisplayName(), failure);
//		}
//	}

}
