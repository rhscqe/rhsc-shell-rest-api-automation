package com.redhat.qe.config;

import java.io.File;
import java.io.IOException;

import com.redhat.qe.exceptions.UnableToOpenConfigurationFileException;

import dstywho.FileUtil;

public class RhscConfiguration {

	private static Configuration INSTANCE;

	public static synchronized Configuration getConfiguration(){
		try {
			return INSTANCE == null ? Configuration.fromXml(FileUtil.fileToString(new File("src/test/resources/rhsc-config.xml"))) : INSTANCE;
		} catch (IOException e) {
			throw new UnableToOpenConfigurationFileException(e);
		}
	}
	 
}
