package com.redhat.qe.repository.sh;

import java.util.ArrayList;
import java.util.HashMap;
import com.redhat.qe.helpers.FileSize;

import com.google.common.base.Joiner;

public class DD {

	public static DD writeRandomData(final String outputFile, FileSize filesize) {
		return writeData("/dev/urandom", outputFile, "1024k", filesize.toMegabytes() );
	}

	public static DD writeRandomData(final String outputFile, final String blocksize, final long count) {
		return writeData("/dev/urandom", outputFile, blocksize, count);
	}
	public static DD writeData(final String inputFile, final String outputFile, final String blocksize, final long count) {
		return new DD(new HashMap<String, String>() {
			{
				put("if", inputFile);
				put("of", outputFile);
				put("bs", blocksize);
				put("count", count + "");
			}
		});

	}

	public HashMap<String, String> namedParams = new HashMap<String, String>();

	public DD(HashMap<String, String> namedParams) {
		this.namedParams = namedParams;
	}

	public String argsToString() {
		ArrayList<String> arguments = new ArrayList<String>();
		for (String param : namedParams.keySet()) {
			arguments
					.add(String.format("%s=%s", param, namedParams.get(param)));
		}
		return Joiner.on(" ").join(arguments);
	}

	public String toString() {
		return String.format("dd %s", argsToString());
	}

}
