package org.unallocatedspace.uasapp;

import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Contains information gathered from the UAS website.
 */
public class Data {
    protected long lastUpdate;
    protected String message;
    protected String key;

/**
 * Constructor.
 *
 * If cache doesn't have an entry, do not make one. Wait until setMessage()
 * has been called.
 */
    public Data(String text) {
        this.key=text;

        /* check if cache already has entry */
        if(false) {
        } else {
            this.lastUpdate=0;
            this.message="";
        }
    }

    public String getLastUpdate() {
        /** take lastUpdate and convert to string in local TZ */
        Date now = new Date(this.lastUpdate);
        SimpleDateFormat date = new SimpleDateFormat();
        return date.format(now);
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isStale() {
        return this.isStale((long) (15*DataTime.MINUTE));
    }

    public boolean isStale(long duration) {
        DataTime clock = new DataTime();

        return clock.isExhausted(this.lastUpdate, duration);
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
            /* save to cache here */
            this.message = message;
            this.lastUpdate = timestamp;
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
