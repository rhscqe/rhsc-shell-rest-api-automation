package com.redhat.qe.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.calgb.test.performance.HttpSession;

import com.google.common.base.Predicate;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.jaxb.BrickHostToServerIdXmlAdapter;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.helpers.utils.StringUtils;
import com.redhat.qe.helpers.utils.StringUtils.RepeatingHashMap;
import com.redhat.qe.repository.rest.HostRepository;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="brick")
public class Brick extends Model {
	@XmlJavaTypeAdapter(value=BrickHostToServerIdXmlAdapter.class )
	@XmlElement(name="server_id")
	private  Host host;
	@XmlElement(name="brick_dir")
	private String dir;
	private String id;
	private RepeatingHashMap<String, String> mixedAttributes;
	private HashMap<String, String> attributes;

	public String toString() {
		return String.format("brick.server_id=%s,brick.brick_dir=%s",
				host.getId(), dir);
	}

	/**
	 * @return the host
	 */
	public Host getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(Host host) {
		this.host = host;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir
	 *            the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public static ArrayList<Brick> listFromReponse(String response) {
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		Collection<HashMap<String, String>> attrsforeachbrick = StringUtils
				.getProperties(response);
		for (HashMap<String, String> brickattrs : attrsforeachbrick) {
			Brick brick = fromAttrs(brickattrs);
			bricks.add(brick);
		}
		return bricks;
	}

	public static ArrayList<Brick> allContentlistFromReponse(String response) {
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		Collection<String> rawBrickDataForAllBricks = StringUtils
				.getPropertyKeyValueSets(response);
		for (String rawBrickData : rawBrickDataForAllBricks) {
			HashMap<String, String> basicInfo = StringUtils
					.getProperties(rawBrickData).iterator().next();
			if (basicInfo != null) {
				Brick brick = fromAttrs(basicInfo);
				RepeatingHashMap<String, String> attrs = StringUtils
						.repeatingKeyAttributeToHash(rawBrickData);
				brick.setAttributes(attrs);

				bricks.add(brick);
			}
		}
		return bricks;
	}

	/**
	 * @param brickattrs
	 * @return
	 */
	public static Brick fromAttrs(HashMap<String, String> brickattrs) {
		Brick brick = new Brick();
		brick.setId(brickattrs.get("id"));
		String name = (brickattrs.get("name"));
		brick.setHostDirFromName(name);

		return brick;
	}

	public void setHostDirFromName(String name) {
		setHost(parseHost(name));
		setDir(parseDir(name));
	}

	/**
	 * @param names
	 * @param i
	 * @return
	 */
	private static Host parseHost(String name) {
		String[] hostnameToBrickDir = name.split(":");
		Host myhost = new Host();
		myhost.setAddress(hostnameToBrickDir[0]);
		return myhost;
	}

	private static String parseDir(String name) {
		String[] hostnameToBrickDir = name.split(":");
		return hostnameToBrickDir[1];
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Brick)
				&& (getDir() == null || ((Brick) o).getDir() == null || ((Brick) o)
						.getDir().equals(getDir()))
				&& (getHost() == null || ((Brick) o).getHost() == null
						|| getHost().getAddress() == null
						|| ((Brick) o).getHost().getAddress() == null || ((Brick) o)
						.getHost().getAddress().equals(getHost().getAddress()));
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder(17, 19);
		builder.append(getDir());
		if (getHost() == null) {
			builder.append(getHost());
		} else {
			builder.append(getHost().getAddress());
		}

		return builder.toHashCode();
	}

	public String getName() {
		return String.format("%s:%s", getHost().getAddress(), getDir());
	}

	public HashMap<String, String> getAttributes() {
		HashMap<String, String> results = new HashMap<String, String>();
		if (attributes != null)
			results.putAll(attributes);
		if (mixedAttributes != null)
			results.putAll(mixedAttributes.flatten());
		return results;

	}

	public void setAttributes(HashMap<String, String> map) {
		this.attributes = map;
	}

	public RepeatingHashMap<String, String> getMixedAttributes() {
		return mixedAttributes;
	}

	public void setAttributes(RepeatingHashMap<String, String> attrs) {
		this.mixedAttributes = attrs;
	}
	
	public Host getConfiguredHostFromBrickHost(HttpSession session) {
		final Host host = new HostRepository(session).show(getHost());
		Host configuredHost = CollectionUtils.findFirst(RhscConfiguration.getConfiguration().getHosts(), new Predicate<Host>() {

				public boolean apply(Host configHost) {
					return configHost.getName().equals(host.getName());
				}
		});
		return configuredHost;
	}

}
