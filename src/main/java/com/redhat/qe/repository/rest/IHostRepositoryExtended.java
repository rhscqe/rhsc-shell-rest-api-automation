package com.redhat.qe.repository.rest;

import java.util.List;

import com.redhat.qe.annoations.Alias;
import com.redhat.qe.model.Host;
import com.redhat.qe.repository.IHostRepository;
import com.redhat.qe.ssh.IResponse;

public interface IHostRepositoryExtended extends IHostRepository{
	public List<Host> listAll();




}
