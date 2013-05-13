package com.redhat.qe.model;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.redhat.qe.repository.HostRepository;

import dstywho.functional.Closure;
import dstywho.functional.Predicate;
import dstywho.timeout.Duration;

public class WaitUtil {
	private static Logger LOG = Logger.getLogger(WaitUtil.class);

	public static class Nothing {

	}

	public static boolean waitForHostStatus(final HostRepository repo, final Host host, final String status, final int numAttempts) {
		final String message = "host status to become " + status;
		LOG.debug("waiting for " + message);


		WaitResult waitresult = muteLogger(new Closure<WaitResult>() {

			@Override
			public WaitResult act() {
				WaitResult result = waitUntil(new Predicate() {

					@Override
					public Boolean act() {
						return repo.show(host).getState().equals(status);
					}
				}, numAttempts);
				LOG.debug(result.toString(message));
				return result;
			}

		});

		return waitresult.isSuccessful();

	}

	// not for multi threaded apps
	public static <T> T muteLogger(Closure<T> closure) {
		LOG.debug("turning logging off");
		Level level = Logger.getRootLogger().getLevel();
		Logger.getRootLogger().setLevel(Level.ERROR);
		T result = null;
		try {
			result = closure.call();
		} finally {
			Logger.getRootLogger().setLevel(level);
			LOG.debug("turning logging on");
		}
		return result;
	}

	public static class WaitResult {
		boolean isSuccessful;
		int numAttempts;
		Duration duration;

		public WaitResult(boolean isSuccessful, int numAttempts, Duration duration) {
			this.isSuccessful = isSuccessful;
			this.numAttempts = numAttempts;
			this.duration = duration;
		}

		public boolean isSuccessful() {
			return isSuccessful;
		}

		public String toString(String forWhat) {
			if (isSuccessful()) {
				return String.format("succeeded in waiting for %s. Number of Attmepts: %s. Elapsed Time(ms):", forWhat, numAttempts, duration.toMilliseconds());
			} else {
				return String.format("failed in waiting for %s. Number of Attmepts: %s. Elapsed Time(ms):", forWhat, numAttempts, duration.toMilliseconds());
			}
		}

		public void setSuccessful(boolean isSuccessful) {
			this.isSuccessful = isSuccessful;
		}

		public int getNumAttempts() {
			return numAttempts;
		}

		public void setNumAttempts(int numAttempts) {
			this.numAttempts = numAttempts;
		}

		/**
		 * @return the duration
		 */
		public Duration getDuration() {
			return duration;
		}

		/**
		 * @param duration
		 *            the duration to set
		 */
		public void setDuration(Duration duration) {
			this.duration = duration;
		}

	}

	public static <T> Duration time(Closure<T> closure) {
		long starttime = System.currentTimeMillis();
		closure.call();
		return new Duration(TimeUnit.MILLISECONDS, System.currentTimeMillis() - starttime);
	}

	public static WaitResult waitUntil(Predicate condition, int numAttempts) {
		long starttime = System.currentTimeMillis();
		for (int _ : new int[numAttempts]) {
			Duration.ONE_SECOND.sleep();
			if (condition.call())
				return new WaitResult(true, _, new Duration(TimeUnit.MILLISECONDS, System.currentTimeMillis() - starttime));
		}
		return new WaitResult(false, numAttempts, new Duration(TimeUnit.MILLISECONDS, System.currentTimeMillis() - starttime));
	}

}
