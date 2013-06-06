package org.unallocatedspace.uasapp;

import android.app.Activity;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.String;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Contains information gathered from the UAS website.
 */
public class Info extends Data {
    private URL url;

/**
 * Sets urlFeed.
 * This doesn't catch Exception MalformedURLException, because we do not have
 * a plan on how to deal with errors yet, and this _shouldn't_ occur. That
 * said it probably will.
 */
    Info(Activity activity, String url) throws MalformedURLException  {
        super(activity, url);

        this.url = new URL(url);
    }

/**
 * Fetches updates from UAS.
 *
 * Calls HttpUrlConnection using the value in urlFeed.
 * <p>
 * If the resource is available this methods updates message and lastUpdate.
 * Then updates the cache with these values. Updates status with a statement
 * that the recent connection succeeded.
 * <p>
 * If the network or the resource is unavailable then this method pulls the
 * values from the cache, or set the value with "Err: NoCache" if there is not
 * a previous update. Updates status with a timestamp of last attempt an a
 * statement of the failure.
 * <p>
 * Test by checking message. lastUpdate, and status.
 */
    public String getMessage() {
        int i;
        byte[] buffer;
        char[] data;
        String message;
        HttpURLConnection connection;
        BufferedInputStream in;

        if(this.isStale()) {
            connection = null;

            try {
                connection = (HttpURLConnection) this.url.openConnection();
                in = new BufferedInputStream(connection.getInputStream());
                buffer = new byte[in.available()];
                in.read(buffer, 0, in.available());
                data = new char[buffer.length];
 
                for(i=0;i < buffer.length ; i++) {
                    data[i]=(char) buffer[i];
                }

                message=String.copyValueOf(data);
                this.setMessage(message);
            } catch(IOException read) {
/* We should catch this exception but I don't know where to log it or how better
 * to handle it. Will update this later.
 */
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
            }
        }

        return super.getMessage();
    }
}
