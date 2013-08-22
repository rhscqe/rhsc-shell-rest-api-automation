package com.redhat.qe.repository;

import java.util.ArrayList;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ssh.IResponse;

public interface IVolumeRepositoryExtended extends IVolumeRepository{

	ArrayList<Volume> list(Cluster cluster);

	IResponse _stop(Volume volume);

	IResponse destroy(Volume volume);

}
