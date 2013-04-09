package com.redhat.qe.helpers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redhat.qe.annoations.Tcms;


public class TestCase {
	
	public static Set<Method> getImplementedTestCases(){
		Reflections reflections = new Reflections(ClasspathHelper.forPackage(""), 
		          new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner(), new MethodAnnotationsScanner() );
		
		return reflections.getMethodsAnnotatedWith(Tcms.class);
	}
	
	public static HashMap<String, ArrayList<Method>> getTestCaseToMethodMap(){
		HashMap<String, ArrayList<Method>> testCaseToMethod = new HashMap<String, ArrayList<Method>>();
		
		for(Method method:getImplementedTestCases()){
			
			for(String testcase :method.getAnnotation(Tcms.class).value()){
				if(testCaseToMethod.get(testcase)== null){
					testCaseToMethod.put(testcase, new ArrayList<Method>());
				}
				testCaseToMethod.get(testcase).add(method);
			}
		}
		return testCaseToMethod;
		
	}
	public static HashMap<String, ArrayList<String>> getTestCaseToMethodNameMap(){
		HashMap<String,ArrayList<String>> result = new HashMap<String,ArrayList<String>>();
		HashMap<String, ArrayList<Method>> testcasesToMethods = getTestCaseToMethodMap();
		for (String testcase: testcasesToMethods.keySet()){
			ArrayList<Method> methods = testcasesToMethods.get(testcase);
			Collection<String> methodNames = Collections2.transform(methods, new Function<Method, String>() {
				public String apply(Method method){
					return method.getDeclaringClass().getName() + "." +method.getName();
				}
				
			});
			result.put(testcase, new ArrayList<String>(methodNames));
			
		}
		return result;
	}
	
	
	public static void main(String[] args){
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(TestCase.getTestCaseToMethodNameMap()));
		
	}

}
