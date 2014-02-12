package com.redhat.qe.repository.rest;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.UriBuilder;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.helpers.Times;
import com.redhat.qe.model.Model;
import com.sun.jersey.api.uri.UriBuilderImpl;

public abstract class SimpleRestRepository<T extends Model> extends Repository<T> {

	public SimpleRestRepository(HttpSession session) {
		super(session);
	}
	
	public abstract String getCollectionPath();

	public boolean isExist(T entity) {
		return _isExists(entity, getCollectionPath());
	}

	public T createOrShow(T entity) {
		return createOrShow(entity,getCollectionPath());
	}

	public T show(T entity) {
		return show(entity, getCollectionPath());
	}

	public T create(T entity) {
		return create(entity, getCollectionPath());
	}

	public  ResponseWrapper _create(T entity) {
		return _create(entity, getCollectionPath());
	}

	public ResponseWrapper destroy(T entity) {
		return delete(entity, getCollectionPath());
	}
	
	public ResponseWrapper _destroy(T entity) {
		return _delete(entity, getCollectionPath());
	}

	public ArrayList<T> list() {
		return list(getCollectionPath());
	}
	
	public ArrayList<T> listAll() {
		ArrayList<T> results = new ArrayList<T>();
		for(int i : new Times(10)){
			String url = UriBuilderImpl.fromPath(getCollectionPath()).queryParam("search", "page {arg1}").build(i + 1 + "").toString();
			ArrayList<T> result = list(url);
			if(result != null)
				results.addAll(result);
		}
		return results;
	}

	public ResponseWrapper _list() {
		return _list(getCollectionPath());
	}

	public ResponseWrapper delete(T entity) {
		return delete(entity, getCollectionPath());
	}

}