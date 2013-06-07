package org.unallocatedspace.uasapp;

import android.app.Activity;
import android.content.SharedPreferences;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    private SharedPreferences cache;
    private SharedPreferences.Editor editor;
    private String key;
    private static final String MESSAGE="Message";
    private static final String TIMESTAMP="Timestamp";

/**
 * Opens a shared preference to cache recent information.
 *
 * NOTE: If cache doesn't have an entry, do not make one. Let setMessage()
 * initate the cache.
 */
    public Data(Activity activity, String text) {
        this.key = text;
        this.cache = activity.getSharedPreferences(this.key, activity.MODE_PRIVATE);
        this.editor = cache.edit();
    }

/**
 * Returns a String with a Formated timestamp of data. 
 */
    public String getLastUpdate() {
        Date now = new Date(this.cache.getLong(this.TIMESTAMP, 0));
        SimpleDateFormat date = new SimpleDateFormat();
        return date.format(now);
    }

/**
 * Returns the data stored in this bank or "" if the bank is uninitiated.
 */
    public String getMessage() {
        return this.cache.getString(this.MESSAGE, "");
    }

/**
 * Returns true if data stored is older than 15 minutes.
 */
    public boolean isStale() {
        return this.isStale((long) (15*DataTime.MINUTE));
    }

/**
 * Returns true if data is older then duration.
 */
    public boolean isStale(long duration) {
        DataTime clock = new DataTime();
        long timestamp = this.cache.getLong(this.TIMESTAMP, 0);

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
 * TODO: Throw exception. Perhaps call it a timetravler exception, or the
 * Doctor exception.
 */
    public void setMessage(String message, long timestamp) {
        Calendar now = (Calendar) new GregorianCalendar();

        if(timestamp > now.getTimeInMillis()) {
            /* throw exception */
        } else {
           this.editor=this.editor.putLong(this.TIMESTAMP, timestamp);
           this.editor=this.editor.putString(this.MESSAGE, message);
           this.editor.apply();
        }
    }
}

/**
 * Package class to support Data Class.
 */
class DataTime {
    final static int SECOND = 1000;
    final static long MINUTE = 60*DataTime.SECOND;

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

        if(endTime < startTime) {
            /* throw exception */
        } else if((endTime-startTime) > duration) {
            exhausted=true;
        }

        return exhausted;
    }
}
