package org.unallocatedspace.uasapp;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Contains information gathered from the UAS website.
 */
public class Data {
    protected SharedPreferences cache;
    protected SharedPreferences.Editor editor;
    protected String key;
    protected static final String MESSAGE="Message";
    protected static final String TIMESTAMP="Timestamp";

/**
 * Constructor.
 *
 * If cache doesn't have an entry, do not make one. Wait until setMessage()
 * has been called.
 */
    public Data(String text) {
        Context context = new Context();

        this.key = text;
        this.cache = context.getSharedPreferences(this.key, context.MODE_PRIVATE);
        this.editor = cache.edit();
    }

    public String getLastUpdate() {
        Date now = new Date(this.cache.getLong(this.TIMESTAMP, 0));
        SimpleDateFormat date = new SimpleDateFormat();
        return date.format(now);
    }

    public String getMessage() {
        return this.cache.getString(this.MESSAGE, "");
    }

    public boolean isStale() {
        return this.isStale((long) (15*DataTime.MINUTE));
    }

    public boolean isStale(long duration) {
        DataTime clock = new DataTime();
        long timestamp = this.cache.getLong(this.TIMESTAMP, 0);

        return clock.isExhausted(timestamp, duration);
    }

    public void setMessage() {
        this.setMessage("");
    }

    public void setMessage(String message) {
        Calendar now = (Calendar) new GregorianCalendar();

        this.setMessage(message, now.getTimeInMillis());
    }

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

class DataTime {
    final static int SECOND = 1000;
    final static long MINUTE = 60*DataTime.SECOND;

    boolean isExhausted(long timestamp, long duration) {
        Calendar now = (Calendar) new GregorianCalendar();

        return this.isExhausted(timestamp, now.getTimeInMillis(), duration);
    }

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
