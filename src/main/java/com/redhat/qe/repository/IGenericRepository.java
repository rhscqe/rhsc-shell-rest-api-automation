package com.redhat.qe.repository;

import java.util.List;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Model;
import com.redhat.qe.ssh.IResponse;

public interface IGenericRepository<T extends Model> {
	public abstract T createOrShow(T entity);

	public abstract T create(T entity);

	public abstract T show(T entity);

	

	public IResponse _destroy(T entity);
	
	public IResponse destroy(T host);
	public List<T> list() ;
	
}