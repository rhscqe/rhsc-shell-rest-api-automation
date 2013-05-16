package com.redhat.qe.test.host;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Host;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class ListHostTest extends TwoHostClusterTestBase{
	
	
	@Test
	@Tcms("250980")
	public void test(){
		List<Host> hosts = getHostRepository().list(null);
		hosts.contains(host1);
		hosts.contains(host2);
	}
	
	@Test
	@Tcms("250981")
	public void listShowAll(){
		Collection<HashMap<String, String>> hosts = StringUtils.getProperties(getHostRepository()._list("--show-all").toString());
		for(HashMap<String,String> hostProperties :hosts){
			Asserts.assertContains(  "id"                                                                            , hostProperties.keySet() , "id"                                   );
			Asserts.assertContains(  "name"                                                                          , hostProperties.keySet() , "name"                                 );
			Asserts.assertContains(  "address"                                                                       , hostProperties.keySet() , "address"                              );
			Asserts.assertContains(  "certificate-organization"                                                      , hostProperties.keySet() , "certificate-organization"             );
			Asserts.assertContains(  "certificate-subject"                                                           , hostProperties.keySet() , "certificate-subject"                  );
			Asserts.assertContains(  "cluster-id"                                                                    , hostProperties.keySet() , "cluster-id"                           );
			Asserts.assertContains(  "cpu-name"                                                                      , hostProperties.keySet() , "cpu-name"                             );
			Asserts.assertContains(  "cpu-mode"                                                                      , hostProperties.keySet() , "cpu-mode"                             );
			Asserts.assertContains(  "cpu-speed"                                                                     , hostProperties.keySet() , "cpu-speed"                            );
			Asserts.assertContains(  "cpu-topology-cores"                                                            , hostProperties.keySet() , "cpu-topology-cores"                   );
			Asserts.assertContains(  "cpu-topology-sockets"                                                          , hostProperties.keySet() , "cpu-topology-sockets"                 );
			Asserts.assertContains(  "cpu-topology-sockets"                                                          , hostProperties.keySet() , "cpu-topology-sockets"                 );
			Asserts.assertContains(  "cpu-topology-sockets"                                                          , hostProperties.keySet() , "cpu-topology-sockets"                 );
			Asserts.assertContains(  "cpu-topology-sockets"                                                          , hostProperties.keySet() , "cpu-topology-sockets"                 );
			Asserts.assertContains(  "cpu-topology-cores"                                                            , hostProperties.keySet() , "cpu-topology-cores"                   );
			Asserts.assertContains(  "cpu-topology-sockets"                                                          , hostProperties.keySet() , "cpu-topology-sockets"                 );
			Asserts.assertContains(  "hardware_information-family"                                                   , hostProperties.keySet() , "hardware_information-family"          );
			Asserts.assertContains(  "hardware_information-manufacturer"                                             , hostProperties.keySet() , "hardware_information-manufacturer"    );
			Asserts.assertContains(  "hardware_information-product_name"                                             , hostProperties.keySet() , "hardware_information-product_name"    );
			Asserts.assertContains(  "hardware_information-serial_number"                                            , hostProperties.keySet() , "hardware_information-serial_number"   );
			Asserts.assertContains(  "hardware_information-uuid"                                                     , hostProperties.keySet() , "hardware_information-uuid"            );
			Asserts.assertContains(  "hardware_information-version"                                                  , hostProperties.keySet() , "hardware_information-version"         );
			Asserts.assertContains(  "iscsi-initiator"                                                               , hostProperties.keySet() , "iscsi-initiator"                      );
			Asserts.assertContains(  "ksm-enabled"                                                                   , hostProperties.keySet() , "ksm-enabled"                          );
			Asserts.assertContains(  "libvirt_version-build"                                                         , hostProperties.keySet() , "libvirt_version-build"                );
			Asserts.assertContains(  "libvirt_version-full_version"                                                  , hostProperties.keySet() , "libvirt_version-full_version"         );
			Asserts.assertContains(  "libvirt_version-major"                                                         , hostProperties.keySet() , "libvirt_version-major"                );
			Asserts.assertContains(  "libvirt_version-minor"                                                         , hostProperties.keySet() , "libvirt_version-minor"                );
			Asserts.assertContains(  "libvirt_version-revision"                                                      , hostProperties.keySet() , "libvirt_version-revision"             );
			Asserts.assertContains(  "max_scheduling_memory"                                                         , hostProperties.keySet() , "max_scheduling_memory"                );
			Asserts.assertContains(  "memory"                                                                        , hostProperties.keySet() , "memory"                               );
			Asserts.assertContains(  "os-type"                                                                       , hostProperties.keySet() , "os-type"                              );
			Asserts.assertContains(  "os-version-full_version"                                                       , hostProperties.keySet() , "os-version-full_version"              );
			Asserts.assertContains(  "port"                                                                          , hostProperties.keySet() , "port"                                 );
			Asserts.assertContains(  "power_management-enabled"                                                      , hostProperties.keySet() , "power_management-enabled"             );
			Asserts.assertContains(  "status-state"                                                                  , hostProperties.keySet() , "status-state"                         );
			Asserts.assertContains(  "storage_manager-priority"                                                      , hostProperties.keySet() , "storage_manager-priority"             );
			Asserts.assertContains(  "storage_manager-valueOf"                                                       , hostProperties.keySet() , "storage_manager-valueOf"              );
			Asserts.assertContains(  "summary-total"                                                                 , hostProperties.keySet() , "summary-total"                        );
			Asserts.assertContains(  "transparent_hugepages-enabled"                                                 , hostProperties.keySet() , "transparent_hugepages-enabled"        );
			Asserts.assertContains(  "type"                                                                          , hostProperties.keySet() , "type"                                 );
			Asserts.assertContains(  "version-build"                                                                 , hostProperties.keySet() , "version-build"                        );
			Asserts.assertContains(  "version-full_version"                                                          , hostProperties.keySet() , "version-full_version"                 );
			Asserts.assertContains(  "version-major"                                                                 , hostProperties.keySet() , "version-major"                        );
			Asserts.assertContains(  "version-minor"                                                                 , hostProperties.keySet() , "version-minor"                        );
			Assert.assertEquals("up", hostProperties.get("status-state"));
			
		}
		
	}

}
