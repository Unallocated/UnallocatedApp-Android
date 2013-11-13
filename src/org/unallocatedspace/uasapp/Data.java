package org.unallocatedspace.uasapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Creates time-sensitive data banks.
 * 
 * Provides caching and couples data with a timestamp metadata. Timestamp is
 * useful to determine if data has grown too old and needs to be updated.
 * <p>
 * TODO: Overload the Data constructor with one that only requires a text
 * argument. This constructor takes the from the current activity or
 * application.
 */
public class Data {
	private SharedPreferences cache = null;
	private static final String CACHE_NAME = "appCache";
	private static final String MESSAGE = "Message";
	private static final String TIMESTAMP = "Timestamp";
	private static final String LOG_ID = "Data";

	/**
	 * Opens a shared preference to cache recent information.
	 * 
	 * NOTE: If cache doesn't have an entry, do not make one. Let setMessage()
	 * initiate the cache.
	 */
	public Data(Activity activity) {
		if (this.cache == null) {
			Log.d(LOG_ID, "Setting up cache.");
			this.cache = activity.getSharedPreferences(CACHE_NAME,
					Activity.MODE_PRIVATE);
		}
	}

	/**
	 * Returns a String with a Formated timestamp of data.
	 */
	public String getLastUpdate() {
		Date now = new Date(this.cache.getLong(Data.TIMESTAMP, 0));
		DateFormat date = SimpleDateFormat.getDateTimeInstance();
		return date.format(now);
	}

	/**
	 * Returns the data stored in this bank or "" if the bank is uninitiated.
	 */
	public String getMessage() {
		return this.cache.getString(Data.MESSAGE, "No status could be found."); // TODO:
																				// Update
																				// with
																				// default
																				// message.
	}

	/**
	 * Returns true if data stored is older than 15 minutes.
	 */
	public boolean isStale() {
		return this.isStale((long) (15 * DataTime.MINUTE));
	}

	/**
	 * Returns true if data is older then duration.
	 */
	public boolean isStale(long duration) {
		DataTime clock = new DataTime();
		long timestamp = this.cache.getLong(Data.TIMESTAMP, 0);

		return clock.isExhausted(timestamp, duration);
	}

	/**
	 * Clears out current data and reset timestamp to the present.
	 */
	public void setMessage() {
		this.setMessage("");
	}

	/**
	 * Sets data with a custom message and updates current timestamp.
	 */
	public void setMessage(String message) {
		Calendar now = (Calendar) new GregorianCalendar();

		this.setMessage(message, now.getTimeInMillis());
	}

	/**
	 * Sets data with a custom message and an arbitrary timestamp.
	 * 
	 * This method throws exception if timestamp is in the future.
	 * 
	 * TODO: Throw exception. Perhaps call it a time-traveler exception, or the
	 * Doctor exception.
	 */
	public void setMessage(String message, long timestamp) {
		Calendar now = (Calendar) new GregorianCalendar();

		if (timestamp > now.getTimeInMillis()) {
			/* throw exception */
			Log.e(LOG_ID, "timestamp should not be future dated.");
		} else {
			Editor editor = this.cache.edit();
			editor.putLong(Data.TIMESTAMP, timestamp);
			editor.putString(Data.MESSAGE, message);
			editor.commit();
		}
	}
}

/**
 * Package class to support Data Class.
 */
class DataTime {
	final static int SECOND = 1000;
	final static long MINUTE = 60 * DataTime.SECOND;

	/**
	 * Returns true if timestamp is older than duration.
	 */
	boolean isExhausted(long timestamp, long duration) {
		Calendar now = (Calendar) new GregorianCalendar();

		return this.isExhausted(timestamp, now.getTimeInMillis(), duration);
	}

	/**
	 * Returns true if startTime is older than endTime by duration seconds.
	 */
	boolean isExhausted(long startTime, long endTime, long duration) {
		boolean exhausted = false;

		if (endTime < startTime) {
			/* throw exception */
		} else if ((endTime - startTime) > duration) {
			exhausted = true;
		}

		return exhausted;
	}
}
