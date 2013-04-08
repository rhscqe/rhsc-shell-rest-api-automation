package com.redhat.qe.repository;

import com.redhat.qe.model.Model;
import com.redhat.qe.ssh.Response;

public interface IRepository <T extends Model> {
	
	public T show(T entity);
	public Response destroy(T entity);
	public T create(T entity);
	public T update(T entity);
	public boolean isExist(T entity);
}
