package com.redhat.qe.repository.rhscshell;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.ClusterFactory;

public class ClusterSerializer {
	
	public String writeToString(File file, Map bindings){
		Writable template = createTemplate(file, bindings);
		return writeTemplate(template);
	}


	/**
	 * @param template
	 * @return
	 */
	private String writeTemplate(Writable template) {
		StringWriter writer = new StringWriter();
		try {
			template.writeTo(writer);
		} catch (IOException e) {
			new RuntimeException(e);
		}
		writer.flush();
		return writer.toString();
	}


	/**
	 * @param file
	 * @param bindings
	 * @return
	 */
	private Writable createTemplate(File file, Map bindings) {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Writable template = null;
		try {
			template = engine.createTemplate(file).make(bindings);
		} catch (CompilationFailedException e1) {
			new RuntimeException(e1);
		} catch (ClassNotFoundException e1) {
			new RuntimeException(e1);
		} catch (IOException e1) {
			new RuntimeException(e1);
		}
		return template;
	}

	
	public String toXml(final Cluster cluster) {
		return writeToString(new File("src/main/resources/cluster.xml.gsp"), new HashMap<String, Cluster>(){{
			put("cluster", cluster);
			
		}});
	}

	public static void main(String[] args) throws CompilationFailedException, ClassNotFoundException, IOException{
		String xml = new ClusterSerializer().toXml(ClusterFactory.cluster("meow"));
		System.out.println(xml);
		
	}

}
