package com.redhat.qe.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Model {
	
	public static <T> T fromJson(String json,Class<T> clazz){
		return new Gson().fromJson(json,clazz);
	}
	public String toJson(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

}
