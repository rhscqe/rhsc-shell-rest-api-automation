package com.redhat.qe.ssh;

public interface IResponse {

	public abstract boolean contains(String regex);

	public abstract IResponse expect(String regex);

	public abstract IResponse unexpect(String regex);

	public abstract String getRaw();

	public abstract String toString();

}