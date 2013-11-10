package com.redhat.qe.helpers.repository;

import java.util.ArrayList;

import com.google.common.base.Predicate;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.model.Hook;
import com.redhat.qe.repository.rest.HookRepository;

public class HookRepoHelper {
	

	 public Hook getHookFromHooksList(HookRepository repo, final HookPath filename) {
		ArrayList<Hook> hooks = repo.list();
		Hook hookUnderTest = CollectionUtils.findFirst(hooks, new Predicate<Hook>(){

			public boolean apply(Hook hook) {
				return hook.getName().equals(filename.getRestApiCannonicalName());
			}});
		return hookUnderTest;
	}

}
