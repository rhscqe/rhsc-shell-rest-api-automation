package com.redhat.qe.repository.glustercli.transformer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.redhat.qe.helpers.utils.StringUtils;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;

import dstywho.regexp.RegexMatch;

public class VolumeParser {
	
	public Volume fromAttrs(String rawattrs){
		Volume vol = new Volume();
		HashMap<String, String> attributes = StringUtils.keyAttributeToHash(rawattrs);
		vol.setName(attributes.get("Volume Name"));
		vol.setType(attributes.get("Type"));
		vol.setId(attributes.get("Volume ID"));
		//TODO 
		return vol;
	}
	
	
	public ArrayList<Volume> fromListAttrGroups(String raw){
		ArrayList<Volume> result = new ArrayList<Volume>();
		Collection<String> groups = StringUtils.getPropertyKeyValueSets(raw);
		for(String group : groups){
			result.add(fromAttrs(group));
		}
		return result;
	}
	
	
	public Brick fromVolumeDeailsSingleBrickData(String raw){
		Brick brick = new Brick();
		HashMap<String, String> props = StringUtils.keyAttributeToHash(raw);
		String name = props.get("Brick").replaceAll("Brick ", "");		
		brick.setHostDirFromName(name);
		brick.setAttributes(props);
		return brick;
	}
	
	public static class BrickClientDetails{
		Brick brick;
		ArrayList<HashMap<String,String>> data;
		public Brick getBrick() {
			return brick;
		}
		public ArrayList<HashMap<String, String>> getData() {
			return data;
		}
		
	} public BrickClientDetails fromVolumeStatusClientSingleBrickData(String raw){
		Brick brick = new Brick();
		HashMap<String, String> props = StringUtils.keyAttributeToHash(raw);
		String name = props.get("Brick").replaceAll("Brick ", "");		
		brick.setHostDirFromName(name);
		brick.setAttributes(props);
		
		String table = new RegexMatch(raw).find("Hostname(.|\n|\r)*").firstElement().getText();
		ArrayList<HashMap<String, String>> data = parseTable(table);

		BrickClientDetails details = new BrickClientDetails();
		details.data = data;
		details.brick = brick;
		return details;
	}


	private ArrayList<HashMap<String, String>> parseTable(String table) {
		String[] tableHeaders = parseHeaders(table);
		String[] rows = parseTableBody(table);
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		for( String row : rows){
			String[] cells = row.split("(\\t|\\s)+");
			if(cells.length != tableHeaders.length)
				System.out.println("warning!!!");
			
			HashMap<String,String> dataRow= new HashMap<String,String>();
			for(int i=0; i< tableHeaders.length; i++){
				dataRow.put(tableHeaders[i], cells[i]);
			}
			data.add(dataRow);
		}
		return data;
	}

	public static class BrickMemoryDetails extends BrickClientDetails{
		
	}

	public BrickMemoryDetails fromVolumeMemoryStatusFromSingleBrickRaw( String rawBrickData) {
		Brick brick = new Brick();
		HashMap<String, String> props = StringUtils.keyAttributeToHash(rawBrickData);
		String name = props.get("Brick").replaceAll("Brick ", "");		
		brick.setHostDirFromName(name);
		brick.setAttributes(props);

		Matcher matcher = Pattern.compile("Name").matcher(rawBrickData);
		matcher.find();
		int start = matcher.start();

		ArrayList<HashMap<String, String>> data = parseMemTable(rawBrickData.substring(start, rawBrickData.length()));
		
		BrickMemoryDetails result = new BrickMemoryDetails();
		result.brick = brick;
		result.data = data;
		
		return result;
	}


	private ArrayList<HashMap<String, String>> parseMemTable(String table) {
		String[] tableHeaders = parseHeaders(table);
		String[] rows = parseTableBody(table);
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		for( String row : rows){
			List<String> cellsList = parseRawRowsIntoCells(row);
			if(cellsList.size() != tableHeaders.length)
				System.out.println("warning!!!");
			
			HashMap<String,String> dataRow= new HashMap<String,String>();
			for(int i=0; i< tableHeaders.length; i++){
				dataRow.put(tableHeaders[i], cellsList.get(i));
			}
			data.add(dataRow);
		}
		return data;
	}


	private List<String> parseRawRowsIntoCells(String row) {
		String name = new RegexMatch(row).find("(\\w|:|-|_)+(\\s(\\w|:|-|_)+){0,1}").firstElement().getText();
		String[] cells = row.replaceFirst(name, "").trim().split("(\\t|\\s)+");
		ArrayList<String> result = new ArrayList<String>();
		result.add(name);
		result.addAll(Arrays.asList(cells));
		return result;
	}


	private String[] parseTableBody(String table) {
		Matcher divder = Pattern.compile("----.*").matcher(table);
		divder.find();
		String[] rows = table.substring(divder.end()+1).split("\n");
		return rows;
	}


	private String[] parseHeaders(String table) {
		String[] tableHeaders = new RegexMatch(table).find(".*").firstElement().getText().split("(\\t|\\s)+");
		return tableHeaders;
	}
}
