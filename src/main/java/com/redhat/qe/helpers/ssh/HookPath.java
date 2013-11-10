package com.redhat.qe.helpers.ssh;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.redhat.qe.helpers.utils.Path;

import dstywho.regexp.RegexMatch;

public class HookPath {

	private static final int LIFECYCLE_INDEX = -2;
	private static final int EVENT_INDEX = LIFECYCLE_INDEX - 1;
	private static final int VERSION_INDEX = EVENT_INDEX - 1;
	private Path path;

	public HookPath(Path path) {
		this.path = path;
	}

	public void parse() {

	}

	public String getPrefix() {
		return new RegexMatch(path.last()).find("^\\w\\d+").getText();
	}

	public String getName() {
		return filenameWithoutExtension().replace(getPrefix(), "");
	}

	private String filenameWithoutExtension() {
		return new RegexMatch(path.last()).find("^[^\\.]*\\.").getText()
				.replaceAll("\\.$", "");
	}

	public String getEvent() {
		return path.dirAt(EVENT_INDEX);
	}

	public String getLifeCycle() {
		return path.dirAt(LIFECYCLE_INDEX);
	}

	public String getVersion() {
		return path.dirAt(VERSION_INDEX);

	}

	public boolean isEnabled() {
		return getPrefix().toLowerCase().contains("s");
	}

	public boolean isDisabled() {
		return !isEnabled();
	}

	public Path getPath() {
		return path;
	}

	public Path getDirectories() {
		return path.removeLast();
	}

	public String getRestApiCannonicalName() {
		return String.format("%s-%s-%s", getEvent(), getLifeCycle()
				.toUpperCase(), getFilename().replaceAll("^[a-zA-z]*", ""));
	}

	private String getFilename() {
		return path.last();
	}

	public int hashCode() {
		return new HashCodeBuilder(2, 17).append(path).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof HookPath))
			return false;

		HookPath rhs = (HookPath) obj;
		return new EqualsBuilder().append(path, rhs.path).isEquals();
	}

}
