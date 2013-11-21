package com.redhat.qe.model.jaxb;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.model.Action;
import com.redhat.qe.model.Brick;
import com.redhat.qe.repository.rest.HostRepository;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "action")
public class MigrateBrickAction extends Action {

	@XmlElement
	MigrateBrickWrapperList bricks;

	/**
	 * @return the bricks
	 */
	public MigrateBrickWrapperList getBrickList() {
		return bricks;
	}

	/**
	 * @param bricks
	 *            the bricks to set
	 */
	public void setBrickList(MigrateBrickWrapperList bricks) {
		this.bricks = bricks;
	}

	public static MigrateBrickAction create(HttpSession session, Brick... bricks) {
		MigrateBrickWrapperList brickList = new MigrateBrickWrapperList();
		brickList.setBricks(new ArrayList<MigrateBrickWrapper>());
		for (Brick brick : bricks) {
			brick.setHost(new HostRepository(session).show(brick.getHost()));
			brickList.getBrickWrappers().add(new MigrateBrickWrapper(brick));
		}
		MigrateBrickAction action = new MigrateBrickAction();
		action.setBrickList(brickList);
		return action;
	}

}
