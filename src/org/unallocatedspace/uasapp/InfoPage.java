package org.unallocatedspace.uasapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class InfoPage extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void launchWallPhoto(View imageBtn) {
		Log.d("UASApp", "Clicked on the wall image.");
//		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("org.unallocatedspace.uasapp/drawable/thewall")));
	}
}
