package com.redhat.qe.helpers.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class FileSize2 {
	
	private static final Long CONVERSION_FACTOR = 1024L;
	private  BigInteger bytes;
	
	
	private FileSize2(BigInteger bytes){
		this.bytes = bytes;
	}
	
	public static long getConversionFactor(int times){
		return CONVERSION_FACTOR * times;
	}

	public BigDecimal toKilobytes(){
		return new BigDecimal(bytes).divide(BigDecimal.valueOf(CONVERSION_FACTOR));
	}
	public BigDecimal toMegabytes(){
		return toKilobytes().divide(BigDecimal.valueOf(CONVERSION_FACTOR));
	}

	public BigDecimal toGigabytes(){
		return toGigabytes().divide(BigDecimal.valueOf(CONVERSION_FACTOR));
	}
	
	public static FileSize2 kilobytes(BigDecimal kbytes){
		return bytes(kbytes.multiply(BigDecimal.valueOf(CONVERSION_FACTOR)));
	}

	public static FileSize2 megabytes(BigDecimal mbs){
		return kilobytes(mbs.multiply(BigDecimal.valueOf(CONVERSION_FACTOR)));
	}

	public static FileSize2 gigabytes(BigDecimal gigs){
		return megabytes(gigs.multiply(BigDecimal.valueOf(CONVERSION_FACTOR)));
	}

	public static FileSize2 bytes(BigInteger bytes){
		return new FileSize2(bytes);
	}
	public static FileSize2 bytes(BigDecimal bytes){
		return new FileSize2(bytes.toBigInteger());
	}
	
	
	
	public BigInteger getBytes() {
		return bytes;
	}

	@Override
	public int hashCode(){
	    return new HashCodeBuilder().append(bytes.hashCode()).toHashCode();
	}
	
	@Override
	public String toString(){
		return bytes.toString();
	}

	@Override
	public boolean equals(final Object obj){
	    if(obj instanceof FileSize2){
	        final FileSize2 other = (FileSize2) obj;
	        return new EqualsBuilder()
	            .append(bytes, other.bytes)
	            .isEquals();
	    } else{
	        return false;
	    }
	}
	

}
