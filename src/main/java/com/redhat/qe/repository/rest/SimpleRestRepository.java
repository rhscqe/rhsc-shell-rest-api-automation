package com.redhat.qe.repository.rest;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Model;

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

	public ResponseWrapper destroy(T entity) {
		return delete(entity, getCollectionPath());
	}

	public ArrayList<T> list() {
		return list(getCollectionPath());
	}

	public ResponseWrapper delete(T entity) {
		return delete(entity, getCollectionPath());
	}

}