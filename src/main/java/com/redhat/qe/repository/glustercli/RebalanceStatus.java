package com.redhat.qe.repository.glustercli;

import org.jsoup.nodes.Element;

public class RebalanceStatus {
	
	public static RebalanceStatus fromElement(Element element){
		RebalanceStatus result = new RebalanceStatus();
		result.setStatusDecode(element.select("statusStr").text());
		result.setStatus(Integer.parseInt(element.select("status").text()));
		result.setFiles( Integer.parseInt(element.select("files").text()));
		result.setSize( Long.parseLong(element.select("size").text()));
		result.setLookups( Integer.parseInt(element.select("lookups").text()));
		result.setFailures( Integer.parseInt(element.select("failures").text()));
		result.setSkipped( Integer.parseInt(element.select("skipped").text()));
		result.setRuntime( element.select("runtime").text());
		return result;
	}
	
	String statusDecode;
	int status;
	int files;
	long size;
	int lookups;
	int failures;
	int skipped;
	String runtime;
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
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
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
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	

}
