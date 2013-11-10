package com.redhat.qe.helpers.ssh;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.base.Joiner;

public class Command {
	
	private ArrayList<String> arguments;
	
	private Command(ArrayList<String> arguements){
		this.arguments = arguements;
	}
	
	public Command(String... arguements){
		this.arguments = new ArrayList<String>(Arrays.asList(arguements));
	}

	public String toString(){
		return  Joiner.on(" ").join(arguments);
	}
	
	protected Command _add(String... arguments){
		this.arguments.addAll(Arrays.asList(arguments));
		return this;
	}

	public Command add(String... arguments){
		ArrayList<String> currentArgs = new ArrayList<String>(this.arguments);
		for(String args: arguments){
			currentArgs.add(args);
		}
		return new Command(currentArgs);
	}
	

}
