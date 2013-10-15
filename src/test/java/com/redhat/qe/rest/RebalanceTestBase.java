package com.redhat.qe.rest;

import com.redhat.qe.model.Host;

public class RebalanceTestBase extends TwoHostClusterTestBase {

	@Override
	protected Host getHost1ToBeCreated() {
		return null;
	}

	@Override
	protected Host getHost2ToBeCreated() {
		// TODO Auto-generated method stub
		return null;
	}

}
