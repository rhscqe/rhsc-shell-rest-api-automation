package com.redhat.qe.ssh;

import java.util.concurrent.TimeUnit;

public class Duration{
	private long interval;
	private TimeUnit units;

	public Duration(TimeUnit units, long interval){
		this.units = units;
		this.interval = interval;
	}

	/**
	 * @return the interval
	 */
	public long getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(long interval) {
		this.interval = interval;
	}

	/**
	 * @return the units
	 */
	public TimeUnit getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(TimeUnit units) {
		this.units = units;
	}
}