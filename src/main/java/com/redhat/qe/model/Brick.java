package com.redhat.qe.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.redhat.qe.helpers.ListUtil;
import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.helpers.StringUtils.RepeatingHashMap;
import com.redhat.qe.repository.GlusterOption;

public class Brick extends Model{
	private  Host host;
	private String dir;
	private String id;
	
	public String toString(){
		return String.format("brick.server_id=%s,brick.brick_dir=%s", host.getId(), dir);
	}

	/**
	 * @return the host
	 */
	public Host getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(Host host) {
		this.host = host;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public static ArrayList<Brick> listFromReponse(String response){
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		Collection<HashMap<String, String>> attrsforeachbrick = StringUtils.getProperties(response)	;		
		for(HashMap<String,String> brickattrs : attrsforeachbrick){
			Brick brick = fromAttrs(brickattrs);
			bricks.add(brick);
		}
		return bricks;
	}

	/**
	 * @param brickattrs
	 * @return
	 */
	private static Brick fromAttrs(HashMap<String, String> brickattrs) {
		Brick brick = new Brick();
		brick.setId(brickattrs.get("id"));
		String name = (brickattrs.get("name"));
		brick.setHost(parseHost(name));
		brick.setDir(parseDir(name));
		return brick;
	}

	/**
	 * @param names
	 * @param i
	 * @return
	 */
	private static Host parseHost(String name) {
		String[] hostnameToBrickDir = name.split(":");
		Host myhost = new Host();
		myhost.setAddress(hostnameToBrickDir[0]);
		return myhost;
	}
	private static String parseDir(String name) {
		String[] hostnameToBrickDir = name.split(":");
		return hostnameToBrickDir[1];
	}
	
	
	@Override
	public boolean equals(Object o){
		return (o instanceof Brick) 
				&& (getDir() == null || ((Brick)o).getDir() == null ||((Brick)o).getDir().equals(getDir()))				
				&& ( getHost() == null 
						|| ((Brick)o).getHost() == null 
						|| getHost().getAddress() == null
						|| ((Brick)o).getHost().getAddress() == null
						||((Brick)o).getHost().getAddress().equals(getHost().getAddress()));
	}	
	
	@Override
	public int hashCode(){
		HashCodeBuilder builder = new HashCodeBuilder(17, 19);
		builder.append(getDir());
		if (getHost() == null) {
			builder.append(getHost());
		} else {
			builder.append(getHost().getAddress());
		}

		return builder.toHashCode();
	}	
	
	
}
