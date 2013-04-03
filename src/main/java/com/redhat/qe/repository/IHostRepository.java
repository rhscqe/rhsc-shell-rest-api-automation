package com.redhat.qe.repository;

import com.redhat.qe.model.Host;

public interface IHostRepository {

	public abstract Host create(Host host);

	public abstract Host show(Host host);

}