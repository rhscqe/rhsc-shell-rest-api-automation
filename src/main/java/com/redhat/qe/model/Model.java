package com.redhat.qe.model;

import com.google.gson.Gson;

public class Model {
	
	public static <T> T fromJson(String json,Class<T> clazz){
		return new Gson().fromJson(json,clazz);
	}

}
