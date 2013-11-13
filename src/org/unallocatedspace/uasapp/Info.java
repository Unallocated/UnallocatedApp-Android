package org.unallocatedspace.uasapp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.util.Log;

/**
 * Contains information gathered from the UAS website.
 */
public class Info extends Data {
	private URL url;
	private static final String LOG_ID = "Info";
	private File appDir = null;

	/**
	 * Sets urlFeed. This doesn't catch Exception MalformedURLException, because
	 * we do not have a plan on how to deal with errors yet, and this
	 * _shouldn't_ occur. That said it probably will.
	 */
	Info(Activity activity, String url) throws MalformedURLException {
		super(activity);
		this.url = new URL(url);
		this.appDir = activity.getFilesDir();
		Log.d(LOG_ID, "App directory: " + appDir.getAbsolutePath());
		Log.d(LOG_ID, "App directory: " + appDir.getPath());
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
	 * values from the cache, or set the value with "Err: NoCache" if there is
	 * not a previous update. Updates status with a timestamp of last attempt an
	 * a statement of the failure.
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

		if (this.isStale()) {
			connection = null;

			try {
				Log.d(LOG_ID,
						"Attempting connection to: " + this.url.toString());
				connection = (HttpURLConnection) this.url.openConnection();
				Log.d(LOG_ID, "connection made?");

				in = new BufferedInputStream(connection.getInputStream());
				buffer = new byte[in.available()];
				in.read(buffer, 0, in.available());
				data = new char[buffer.length];

				for (i = 0; i < buffer.length; i++) {
					data[i] = (char) buffer[i];
				}

				message = String.copyValueOf(data);
				Log.d(LOG_ID, "Message is: " + message);
				this.setMessage(message);
			} catch (IOException read) {
				/*
				 * We should catch this exception but I don't know where to log
				 * it or how better to handle it. Will update this later.
				 */
				Log.e(LOG_ID, "Caught read exception:" + read.getMessage());
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		}

		return super.getMessage();
	}

	public URI getImage(String fileName) {
		File file = new File(appDir + File.pathSeparator + fileName);
		try {
			long startTime = System.currentTimeMillis();
			Log.d(LOG_ID, "download begining");
			Log.d(LOG_ID, "download url:" + this.url);
			Log.d(LOG_ID, "downloaded file name:" + fileName);

			/* Create the file */
			file.createNewFile();

			/* Open a connection to that URL. */
			URLConnection ucon = this.url.openConnection();

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
			Log.d(LOG_ID, "download ready in"
					+ ((System.currentTimeMillis() - startTime) / 1000)
					+ " sec");

		} catch (IOException e) {
			Log.d(LOG_ID, "Error: " + e);
		}

		return file.toURI();
	}
}
