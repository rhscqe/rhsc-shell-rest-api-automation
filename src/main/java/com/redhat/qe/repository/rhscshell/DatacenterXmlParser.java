package com.redhat.qe.repository.rhscshell;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.redhat.qe.model.Datacenter;

public class DatacenterXmlParser {
	
	/*
	 * "<data_centers>[\n]"
2013-08-12 20:00:42,880 [main] DEBUG org.apache.http.wire - << "    <data_center href="/api/datacenters/5849b030-626e-47cb-ad90-3ce782d831b3" id="5849b030-626e-47cb-ad90-3ce782d831b3">[\n]"
2013-08-12 20:00:42,880 [main] DEBUG org.apache.http.wire - << "        <name>Default</name>[\n]"
2013-08-12 20:00:42,881 [main] DEBUG org.apache.http.wire - << "        <description>The default Data Center</description>[\n]"
2013-08-12 20:00:42,881 [main] DEBUG org.apache.http.wire - << "        <link href="/api/datacenters/5849b030-626e-47cb-ad90-3ce782d831b3/storagedomains" rel="storagedomains"/>[\n]"
2013-08-12 20:00:42,881 [main] DEBUG org.apache.http.wire - << "        <link href="/api/datacenters/5849b030-626e-47cb-ad90-3ce782d831b3/clusters" rel="clusters"/>[\n]"
2013-08-12 20:00:42,881 [main] DEBUG org.apache.http.wire - << "        <link href="/api/datacenters/5849b030-626e-47cb-ad90-3ce782d831b3/permissions" rel="permissions"/>[\n]"
2013-08-12 20:00:42,881 [main] DEBUG org.apache.http.wire - << "        <link href="/api/datacenters/5849b030-626e-47cb-ad90-3ce782d831b3/quotas" rel="quotas"/>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "        <storage_type>nfs</storage_type>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "        <version major="3" minor="1"/>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "        <supported_versions>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "            <version major="3" minor="2"/>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "            <version major="3" minor="1"/>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "        </supported_versions>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "        <status>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "            <state>uninitialized</state>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "        </status>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "    </data_center>[\n]"
2013-08-12 20:00:42,882 [main] DEBUG org.apache.http.wire - << "</data_centers>[\n]"
	 */
	
	public Datacenter parseDatacenter(String raw){
		Datacenter result = new Datacenter();
		result.setDescription(Jsoup.parse(raw).select("description").text());
		result.setName(Jsoup.parse(raw).select("name").text());
		result.setId(Jsoup.parse(raw).select("data_center").first().attr("id"));
		return result;
		
	}

	public ArrayList<Datacenter> parseDatacenters(String raw){
		Elements datacenterElems = Jsoup.parse(raw).select("data_center");
		ArrayList<Datacenter> results = new ArrayList<Datacenter>();
		for( Element datacenterelem: datacenterElems){
			results.add(parseDatacenter(datacenterelem.toString()));
		}
		return results;
		
	}

}
