package com.android.pennaed.outOfReach;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.pennaed.R;
import com.android.pennaed.contacts.PointInPolygon;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by chaitalisg on 11/8/14.
 */
public class LocationTrackFragment extends Fragment
		implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	// Milliseconds per second
	private static final int MILLISECONDS_PER_SECOND = 1000;
	// Update frequency in seconds
	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	// Update frequency in milliseconds
	private static final long UPDATE_INTERVAL =
			MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
	// The fastest update frequency, in seconds
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	// A fast frequency ceiling in milliseconds
	private static final long FASTEST_INTERVAL =
			MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	LocationClient mLocationClient;
	private PointInPolygon pointInPolygon;

	// Set the valid location range for emergency organizations to:
	// 43rd to 30th, market to baltimore
	double[] lat_temp = {39.954844, 39.958002, 39.952491, 39.951472,
			39.949449, 39.949523, 39.949869, 39.949260, 39.949663,
			39.951571};
	double[] lng_temp = {-75.183274, -75.208213, -75.209388, -75.209398,
			-75.209291, -75.207167, -75.201309, -75.184550, -75.184057,
			-75.182544};
	// Handle to SharedPreferences for this app
	SharedPreferences mPrefs;

	// Handle to a SharedPreferences editor
	SharedPreferences.Editor mEditor;
	// Define an object that holds accuracy and frequency parameters
	LocationRequest mLocationRequest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_location_track, container,
				false);


		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		pointInPolygon = new PointInPolygon(lat_temp, lng_temp);
		// Open the shared preferences
		mPrefs = getActivity().getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		// Get a SharedPreferences editor
		mEditor = mPrefs.edit();
	    /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
		mLocationClient = new LocationClient(getActivity(), this, this);

		mLocationRequest = LocationRequest.create();
		// Use high accuracy
		mLocationRequest.setPriority(
				LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 5 seconds
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		// Set the fastest update interval to 1 second
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		mLocationRequest.setSmallestDisplacement(0);

	}

	@Override
	public void onPause() {
		// Save the current setting for updates
		super.onPause();
	}

	@Override
	public void onStart() {
		super.onStart();
		mLocationClient.connect();
	}

	@Override
	public void onResume() {
        /*
         * Get any previous setting for location updates
         * Gets "false" if an error occurs
         */
		super.onResume();
	}

	/*
	 * Called by Location Services when the request to connect the
	 * client finishes successfully. At this point, you can
	 * request the current location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
		// If already requested, start periodic updates

		//mLocationClient.requestLocationUpdates(mLocationRequest, this);
		Intent intent = new Intent(getActivity(), LocationReceiver.class);
		PendingIntent locationIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 14872, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		mLocationClient.requestLocationUpdates(mLocationRequest, locationIntent);
	}

	/*
	 * Called by Location Services if the connection to the
	 * location client drops because of an error.
	 */
	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(getActivity(), "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	/*
	 * Called by Location Services if the attempt to
	 * Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(
						getActivity(),
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
			//Magic Code !!!! :O 1001 = REQUEST_CODE_RECOVER_PLAY_SERVICES
			GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), getActivity(),
					1001).show();
		}
	}

	// Global constants
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
	private final static int
			CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	// Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {
		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	/*
	 * Handle results returned to the FragmentActivity
	 * by Google Play services
	 */
	@Override
	public void onActivityResult(
			int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

			case CONNECTION_FAILURE_RESOLUTION_REQUEST:
            /*
             * If the result code is Activity.RESULT_OK, try
             * to connect again
             */
				switch (resultCode) {
					case Activity.RESULT_OK:
                    /*
                     * Try the request again
                     */

						break;
				}

		}
	}

	private boolean servicesConnected() {
		// Check that Google Play services is available
		int resultCode =
				GooglePlayServicesUtil.
						isGooglePlayServicesAvailable(getActivity());
		// If Google Play services is available
		if (resultCode != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), 0).show();
			return false;
		}
		return true;
	}
}
