package com.redhat.qe.repository.glustercli;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import junit.framework.Assert;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.joda.time.Duration;
import org.jsoup.nodes.Element;

import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.helpers.utils.FileSize2;

public class RebalanceStatus {
	
	private static final FileSize2 TEN_MEGS = FileSize2.megabytes(BigDecimal.valueOf(10));
	public static RebalanceStatus fromElement(Element element){
		RebalanceStatus result = new RebalanceStatus();
		result.setStatusDecode(element.select("statusStr").text());
		result.setStatus(Integer.parseInt(element.select("status").text()));
		result.setFiles( Integer.parseInt(element.select("files").text()));
		result.setSize( FileSize2.bytes(BigInteger.valueOf(Long.parseLong(element.select("size").text()))));
		result.setLookups( Integer.parseInt(element.select("lookups").text()));
		result.setFailures( Integer.parseInt(element.select("failures").text()));
		result.setSkipped( Integer.parseInt(element.select("skipped").text()));
		result.setRuntime( Duration.millis((long) (Double.parseDouble(element.select("runtime").text()) * 1000)));
		result.setNodeName( element.select("nodeName").text());
		return result;
	}
	

	String statusDecode;
	int status;
	int files;
	FileSize2 size;
	int lookups;
	int failures;
	int skipped;
	Duration runtime;
	String nodeName;
	public String getStatusDecode() {
		return statusDecode;
	}
	public void setStatusDecode(String statusStr) {
		this.statusDecode = statusStr;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getFiles() {
		return files;
	}
	public void setFiles(int files) {
		this.files = files;
	}
	public FileSize2 getSize() {
		return size;
	}
	public void setSize(FileSize2 size) {
		this.size = size;
	}
	public int getLookups() {
		return lookups;
	}
	public void setLookups(int lookups) {
		this.lookups = lookups;
	}
	public int getFailures() {
		return failures;
	}
	public void setFailures(int failures) {
		this.failures = failures;
	}
	public int getSkipped() {
		return skipped;
	}
	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}
	public Duration getRuntime() {
		return runtime;
	}
	public void setRuntime(Duration runtime) {
		this.runtime = runtime;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	
	public void _verifyEquals(RebalanceStatus other){
		Assert.assertEquals(getNodeName(), other.getNodeName());
		Assert.assertEquals(getStatusDecode(), other.getStatusDecode());
		Assert.assertEquals(getFiles(), other.getFiles());
		Assert.assertEquals(getLookups(), other.getLookups());
		Assert.assertEquals(getFailures(), other.getFailures());
		Assert.assertEquals(getSkipped(), other.getSkipped());
		Assert.assertEquals(getRuntime().toStandardSeconds(), other.getRuntime().toStandardSeconds());
		BigInteger expectedSizeLowerBound = getSize().getBytes().subtract(TEN_MEGS.getBytes());
		BigInteger expectedSizeUpperBound = getSize().getBytes().add(TEN_MEGS.getBytes());
		Assert.assertTrue(other.getSize().getBytes().compareTo(expectedSizeUpperBound) <= 0);
		Assert.assertTrue(other.getSize().getBytes().compareTo(expectedSizeLowerBound) >= 0);
	}
	
	

}
