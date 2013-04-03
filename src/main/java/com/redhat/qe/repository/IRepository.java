package com.redhat.qe.repository;

public interface IRepository <T extends Repository> {
	
	public T show(T entity);
	public T destroy(T entity);
	public T create(T entity);

}
