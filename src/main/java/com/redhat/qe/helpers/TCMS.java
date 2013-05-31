package com.redhat.qe.helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class TCMS {
	
	private void acceptAllSSLCerts() throws KeyManagementException, NoSuchAlgorithmException{
		 // Create a trust manager that does not validate certificate chains
	    TrustManager[] trustAllCerts = new TrustManager[] {
	        new X509TrustManager() {
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	 
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                // Trust always
	            }
	 
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                // Trust always
	            }
	        }
	    };
	 
	    // Install the all-trusting trust manager
	    SSLContext sc = SSLContext.getInstance("SSL");
	    // Create empty HostnameVerifier
	    HostnameVerifier hv = new HostnameVerifier() {
	                public boolean verify(String arg0, SSLSession arg1) {
	                        return true;
	                }
	    };

	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	    HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}
	
	private void acceptAllCerts(){
		try {
			acceptAllSSLCerts();
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void init(String username, String password){
	    XmlRpcClient client = new XmlRpcClient();
	    acceptAllCerts();
	    XmlRpcClientConfigImpl configuration = configure(username, password);
	    client.setConfig(configuration);
	    
	    try {
			Object out = client.execute("TestPlan.get_test_cases", Arrays.asList(new Object[]{7332}) );
			System.out.println(out);
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param username
	 * @param password
	 * @return 
	 */
	private XmlRpcClientConfigImpl configure(String username, String password) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setBasicUserName(username);
		config.setBasicPassword(password);
	    try {
			config.setServerURL(new URL("https://tcms.engineering.redhat.com:443/xmlrpc/"));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	    return config;
	}
	
	public static void main(String[] args){
		TCMS t = new TCMS();
		
		
	}

}
