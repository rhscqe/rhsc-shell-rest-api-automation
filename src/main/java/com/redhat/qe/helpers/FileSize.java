package com.redhat.qe.helpers;

public class FileSize {
	
	private static final int CONVERSION_FACTOR = 1024;
	public long bytes;
	
	private FileSize(int bytes){
		this.bytes = bytes;
	}
	
	public static FileSize Gigabytes(int gbs){
		return megaBytes(gbs * CONVERSION_FACTOR);
	}
	
	public static FileSize kiloBytes(int kbytes){
		return new FileSize(kbytes * CONVERSION_FACTOR);
	}
	
	public static FileSize megaBytes( int mbs){
		return kiloBytes(mbs* CONVERSION_FACTOR);
	}
	
	public long toKilobytes(){
		return bytes * CONVERSION_FACTOR;
	}

	public long toMegabytes(){
		return toKilobytes() * CONVERSION_FACTOR;
	}
	
	public long toGigabytes(){
		return toMegabytes() * CONVERSION_FACTOR;
	}

}
