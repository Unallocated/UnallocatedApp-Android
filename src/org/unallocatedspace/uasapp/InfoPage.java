package org.unallocatedspace.uasapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class InfoPage extends Activity {

	private static final String LOG_CHANNEL = "UASApp-InfoPage";

	private Status status = null;
	private Wall wall = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Explode UI layout
		setContentView(R.layout.main);

		// Create a new Status object
		this.status = new Status(this);
		this.wall = new Wall(this);

		// Set Text for UAS Status
		TextView statusText = (TextView) findViewById(R.id.statusText);
		statusText.setText(this.status.getMessage());

		// Set wall photo
		ImageButton wallPhoto = (ImageButton) findViewById(R.id.wallImgButton);
		wallPhoto.setImageURI(Uri.parse(this.wall.getImgURI().toString()));
	}

	public void launchWallPhoto(View imageBtn) {
		Log.d(LOG_CHANNEL, "Clicked on the wall image.");
		Intent i = new Intent();
		i.setAction(android.content.Intent.ACTION_VIEW);
		Uri path = Uri.parse(this.wall.getImgURI().toString());

		Log.d(LOG_CHANNEL, "Path: " + path);
		i.setDataAndType(path, "image/jpg");
		startActivity(i);
	}
}
