package com.redhat.qe.helpers.utils;

public class FileNameHelper {
	public static String randomFileName() {
		return TimestampHelper.timestamp() + "" + (RandomIntGenerator.positive() + "").substring(0, 5);
	}
}
