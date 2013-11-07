package com.redhat.qe.helpers.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class TimestampHelper {
	/**
	 * @return 
	 * 
	 */
	public static String timestamp() {
		DateTime time = new DateTime();
		return time.toString(DateTimeFormat.forPattern("YYYYMMddHHmmssSSS"));
	}
}
