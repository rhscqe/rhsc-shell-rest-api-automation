package com.redhat.qe.helpers;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.calgb.test.performance.HttpSession;
import org.testng.Assert;

import com.redhat.qe.config.RestApi;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.factories.HostFactory;
import com.redhat.qe.helpers.repository.ClusterHelper;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.rest.ClusterRepository;
import com.redhat.qe.repository.rest.HostRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.Credentials;

import dstywho.timeout.Timeout;

/**
 * 
 */

/**
 * @author dustin
 * 
 */
public class PollForStatus {

	private static Logger LOG = Logger.getLogger(WaitUtil.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String hostname = "latest";
		final String clustername = "myCluster";


		HttpSession session = new HttpSessionFactory()
				.createHttpSession(new RestApi(hostname, new Credentials(
						"admin@internal", "redhat")));

		Cluster cluster = new ClusterHelper()._getClusterBasedOnName(
				new ClusterRepository(session),
				ClusterFactory.cluster(clustername));
		Assert.assertNotNull(cluster, "could not find cluster");

		while (true) {
			Timeout.TIMEOUT_ONE_SECOND.sleep();
			LOG.fatal("-------------------");
			System.out.println("---------------");
			VolumeRepository volumerepo = new VolumeRepository(session, cluster);
			ArrayList<Volume> vols = volumerepo.list();

			System.out.println("num of volumes:" + vols.size());
			for (Volume vol : vols) {
				System.out.println(String.format("vol %s: %s ", vol.getName(),
						vol.getStatus()));
			}

			ArrayList<Host> hosts = new HostRepository(session).list();
			System.out.println("num of hosts:" + hosts.size());
			for (Host host : hosts) {
				System.out.println(String.format("host %s: %s ",
						host.getName(), host.getStatus().getState()));
			}
		}

	}

}
