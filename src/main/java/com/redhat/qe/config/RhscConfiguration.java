package com.redhat.qe.config;

import java.io.File;
import java.io.IOException;

import com.redhat.qe.exceptions.UnableToOpenConfigurationFileException;

import dstywho.FileUtil;

public class RhscConfiguration {

	private static final String DEFAULT_CONFIG = "src/test/resources/rhsc-config.xml";
	private static Configuration INSTANCE;

	public static synchronized Configuration getConfiguration(){
		try {
			return INSTANCE == null ? getConfigFromXml() : INSTANCE;
		} catch (IOException e) {
			throw new UnableToOpenConfigurationFileException(e);
		}
	}
	
	public static String getXmlFile(){
		String result = System.getenv("RHSC_SHELL_TEST_CONFIG_FILE");
		if (result == null){
			return DEFAULT_CONFIG;
		}else{
			return result;
		}
	}

	private static Configuration getConfigFromXml() throws IOException {
		return Configuration.fromXml(FileUtil.fileToString(new File(getXmlFile())));
	}
	 
}
