package com.redhat.qe.helpers.rebalance;

import com.redhat.qe.helpers.ssh.FileHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.Brick;

public class BrickFiles {

	private BrickSshSessionFactory sessionFactory;

	public BrickFiles(BrickSshSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String listFiles(Brick brick) {
		return new FileHelper().listFiles(sessionFactory.getSshSession(brick),
				new AbsolutePath(Path.from(brick.getDir()))).getStdout();
	};

}
