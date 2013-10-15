package com.redhat.qe.repository;

import java.util.ArrayList;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ssh.IResponse;

public interface IVolumeRepositoryExtended extends IVolumeRepository{

	ArrayList<Volume> listAll();
	IResponse _listAll();



}
