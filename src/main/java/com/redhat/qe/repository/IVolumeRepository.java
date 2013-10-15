package com.redhat.qe.repository;

import com.redhat.qe.model.Volume;
import com.redhat.qe.ssh.IResponse;


public interface IVolumeRepository extends IGenericRepository<Volume>{
	
	IResponse _stop(Volume volume);

}
