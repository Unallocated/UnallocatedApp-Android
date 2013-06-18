package org.unallocatedspace.uasapp;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class InfoPage extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		GetNewUASWallPhoto();
	}

	public void launchWallPhoto(View imageBtn) {
		Log.d("UASApp", "Clicked on the wall image.");

		GetNewUASWallPhoto();
		// startActivity(new Intent(Intent.ACTION_VIEW,
		// Uri.parse("org.unallocatedspace.uasapp/drawable/thewall")));
	}

	private void GetNewUASWallPhoto() {
		String MY_URL_STRING = "http://www.unallocatedspace.org/thewall/thewall.jpg";
		new DownloadImageTask((ImageView) findViewById(R.id.imageButton1))
				.execute(MY_URL_STRING);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}

}
