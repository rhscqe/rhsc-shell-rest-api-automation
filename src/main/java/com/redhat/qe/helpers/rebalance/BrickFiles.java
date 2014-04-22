package com.redhat.qe.helpers.rebalance;

import com.redhat.qe.helpers.ssh.FileHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.Brick;
import com.redhat.qe.ssh.ExecSshSession;

public class BrickFiles {

	private BrickSshSessionFactory sessionFactory;

	public BrickFiles(BrickSshSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String listFiles(Brick brick) {
		ExecSshSession session = sessionFactory.getSshSession(brick);
		session.start();
		try{
			return new FileHelper().listFiles(session,
				new AbsolutePath(Path.from(brick.getDir()))).getStdout();
		}finally{
			session.stop();
		}
	};

}
