package com.redhat.qe.helpers.repository;


import junit.framework.Assert;

import com.google.common.base.Predicate;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.repository.IClusterRepository;
import com.redhat.qe.repository.rest.DatacenterRepository;

public class ClusterHelper {
	
	public Cluster create(Cluster cluster, IClusterRepository repo, DatacenterRepository datacenterRepo){
		cluster.setDatacenter(new DatacenterHelper().getDefault(datacenterRepo));
		Cluster  result = repo.createOrShow(cluster);
		return result;
	}
	
	public Cluster getClusterBasedOnName(IClusterRepository repo, final Cluster clusterWithName) {
		Cluster cluster = _getClusterBasedOnName(repo, clusterWithName);
		Assert.assertNotNull( "cluster for volume has not been created yet when expected to have been created", cluster);
		return cluster;
	}
	
	public Cluster _getClusterBasedOnName(IClusterRepository repo, final Cluster clusterWithName) {
		Cluster cluster = CollectionUtils._findFirst(repo.list(), new Predicate<Cluster>() {

			public boolean apply(Cluster cluster) {
				return cluster.getName().equals(clusterWithName.getName());
			}
		});
		return cluster;

	}

}
