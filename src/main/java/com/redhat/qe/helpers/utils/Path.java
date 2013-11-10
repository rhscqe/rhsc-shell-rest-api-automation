package com.redhat.qe.helpers.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.attribute.Size2DSyntax;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.common.base.Joiner;

public class Path {
	protected static final String DEFAULT_SEPARATOR = "/";
	String separator = DEFAULT_SEPARATOR;
	List<String> directories = new ArrayList<String>();
	
	
	public Path(String seperator) {
		super();
		this.separator = seperator;
	}

	private Path(String seperator, ArrayList<String> directories){
		this.separator = seperator;
		this.directories = directories;
	}

	public Path(Path path) {
		super();
		this.separator = path.separator;
		this.directories = new ArrayList<String>(path.directories);
	}


	public void parse(String unparsed){
		String modifiedParam = unparsed.replaceAll(separator + "*$", "");
		modifiedParam = unparsed.replaceAll("^" + separator + "*", "");
		String[] resultDirectories = modifiedParam.split(separator);
		_add(resultDirectories);
	}
	
	public static Path from(String seperator, String unparsed){
		Path path = new Path(seperator);
		path.parse(unparsed);
		return path;
	}
	
	public static Path from(String unparsed){
		return Path.from(DEFAULT_SEPARATOR, unparsed);
	}

	public static Path fromDirs(String... parts){
		Path path = new Path(DEFAULT_SEPARATOR);
		return path.add(parts);
	}
	
	public String toString(){
		return Joiner.on(this.separator).join(directories);
	}
	
	public Path add(String... dirs){
		Path result = new Path(this);
		result._add(dirs);
		return result;
	}
	
	public String dirAt(int index){
		if(index > 0){
			return directories.get(index);
		}else{
			return directories.get(directories.size() + index);
		}
		
	}
	
	
	private void _add(String... dirs){
		directories.addAll(Arrays.asList(dirs));
	}
	
	
	public List<String> _getDirectories(){
		return directories;
	}

	public String last() {
		return directories.get(directories.size() - 1);
	}
	
	public Path removeLast(){
		ArrayList<String> thesedirs = new ArrayList<String>(directories);
		thesedirs.remove(thesedirs.size()-1);
		return	new Path(separator,thesedirs);	
	}
	public int hashCode() {
	        return new HashCodeBuilder(2, 31). // two randomly chosen prime numbers
	            append(directories).
	            append(separator).
	            toHashCode();
	    }

	    public boolean equals(Object obj) {
	        if (obj == null)
	            return false;
	        if (obj == this)
	            return true;
	        if (!(obj instanceof Path))
	            return false;

	        Path rhs = (Path) obj;
	        return new EqualsBuilder().
	            // if deriving: appendSuper(super.equals(obj)).
	            append(directories, rhs.directories).
	            append(separator, rhs.separator). isEquals();
	    }
	

}
