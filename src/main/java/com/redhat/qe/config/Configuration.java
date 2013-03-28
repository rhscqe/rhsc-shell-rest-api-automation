package com.redhat.qe.config;

import java.io.File;
import java.io.IOException;

import com.redhat.qe.exceptions.UnableToOpenConfigurationFileException;
import com.redhat.qe.ssh.Credentials;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import dstywho.FileUtil;

public class Configuration {
	
	private RestApi restApi;
	private ShellHost shellHost;
	/**
	 * @param restApi
	 * @param shellHost
	 */
	public Configuration(RestApi restApi, ShellHost shellHost) {
		super();
		this.restApi = restApi;
		this.shellHost = shellHost;
	}
	/**
	 * @return the restApi
	 */
	public RestApi getRestApi() {
		return restApi;
	}
	/**
	 * @param restApi the restApi to set
	 */
	public void setRestApi(RestApi restApi) {
		this.restApi = restApi;
	}
	/**
	 * @return the shellHost
	 */
	public ShellHost getShellHost() {
		return shellHost;
	}
	/**
	 * @param shellHost the shellHost to set
	 */
	public void setShellHost(ShellHost shellHost) {
		this.shellHost = shellHost;
	}
	
	public String toXml(){
		XStream xstream = new XStream(new DomDriver());
		return xstream.toXML(this);
	}
	public static Configuration fromXml(String config){
		XStream xstream = new XStream(new DomDriver());
		return (Configuration) xstream.fromXML(config);
	}
	
	public static Configuration INSTANCE;
	public static synchronized Configuration getConfiguration(){
		try {
			return INSTANCE == null ? fromXml(FileUtil.fileToString(new File("src/test/resources/config.xml"))) : INSTANCE;
		} catch (IOException e) {
			throw new UnableToOpenConfigurationFileException(e);
		}
	}
	 
	public static void main(String[] a){
		RestApi api = new RestApi("https://localhost:443/api", new Credentials("admin@internal", "redhat"));
		ShellHost shell = new ShellHost("localhost", new Credentials("root", "redhat"),22);
		Configuration config = new Configuration(api, shell);
		System.out.println(config.toXml());
		String u = fromXml(config.toXml()).getRestApi().getCredentials().getUsername();
		System.out.println(u);
		try {
			System.out.println(fromXml(FileUtil.fileToString(new File("src/test/resources/config.xml"))).toXml());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
