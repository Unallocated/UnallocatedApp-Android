package org.unallocatedspace.uasapp;

import java.net.MalformedURLException;
import java.net.URI;

import android.app.Activity;
import android.util.Log;

/**
 * The Wall object is used to retrieve the Wall image URI. The main work of
 * downloading the image and writing it to the file system is done by Info.java,
 * however, the wall specific information if handled by this class.
 * 
 * @author Usako
 */
public class Wall {
	private static final String WALL_URL = "http://www.unallocatedspace.org/thewall/thewall.jpg";
	private static final String WALL_FILENAME = "thewall.jpg";
	private Info space;
	private URI wallImageURI;
	private static final String LOG_ID = "Wall";

	/**
	 * Creates member objects from the Info class and adds them to the array
	 * members.
	 */
	Wall(Activity activity) {
		try {
			this.space = new Info(activity, WALL_URL);
			this.refresh();
		} catch (MalformedURLException e) {
			/*
			 * We should catch this exception but I don't know where to log it
			 * or how better to handle it. Will update this later.
			 */
			Log.e(LOG_ID, "Caught MalformedURLException: " + e.getMessage());
		}
	}

	/**
	 * Refreshes all member objects.
	 * 
	 * Calls getMessage() methods of all other objects to keep them up to date.
	 * Each time the activity page awakens, it will call this method to ensure
	 * freshness.
	 */
	public void refresh() {
		this.wallImageURI = this.space.getImage(WALL_FILENAME);
	}

	public URI getImgURI() {
		return this.wallImageURI;
	}
}
