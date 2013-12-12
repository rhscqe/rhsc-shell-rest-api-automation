package com.redhat.qe.helpers.utils;

public class FileSize {
	
	private static final int CONVERSION_FACTOR = 1024;
	public long kilobytes;
	
	
	private FileSize(int kilobytes){
		this.kilobytes = kilobytes;
	}
	
	public static FileSize Gigabytes(int gbs){
		return megaBytes(gbs * CONVERSION_FACTOR);
	}
	
	public static FileSize kiloBytes(int kbytes){
		return new FileSize(kbytes);
	}
	
	public static FileSize megaBytes( int mbs){
		return kiloBytes(mbs* CONVERSION_FACTOR);
	}
	
	public long toKilobytes(){
		return kilobytes;
	}

	public long toMegabytes(){
		return toKilobytes() / CONVERSION_FACTOR;
	}
	
	public long toGigabytes(){
		return toMegabytes() / CONVERSION_FACTOR;
	}

}
