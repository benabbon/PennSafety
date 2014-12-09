package com.android.pennaed.outOfReach;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.pennaed.R;
import com.android.pennaed.contacts.PointInPolygon;
import com.android.pennaed.emergency.AppVars;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

/**
 * Created by chaitalisg on 11/8/14.
 */
public class LocationTrackFragment extends Fragment
		implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	// Update frequency in seconds
	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	// Milliseconds per second
	private static final int MILLISECONDS_PER_SECOND = 1000;
	// Update frequency in milliseconds
	private static final long UPDATE_INTERVAL =
			MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
	// The fastest update frequency, in seconds
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	// A fast frequency ceiling in milliseconds
	private static final long FASTEST_INTERVAL =
			MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	// Global constants
	/* Define a request code to send to Google Play services
	   This code is returned in Activity.onActivityResult
     */
	private final static int
			CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	LocationClient mLocationClient;
	// Set the valid location range for emergency organizations to:
	// 43rd to 30th, market to baltimore
	double[] lat_temp = {39.954582,39.956683,39.959574,39.959233,39.957880,
                        39.957555,39.956893,39.957909,39.949605,39.950004,
                        39.949572,39.944534,39.944036,39.943695,39.943230,
                        39.949247,39.951645,};
    double[] lng_temp = {-75.181359,-75.198048,-75.197120,-75.202039,-75.201830,
                        -75.199496,-75.199673,-75.208101,-75.209249,-75.201347,-75.199850,
                     -75.197935, -75.196835,-75.195950,-75.192994,-75.184631,-75.182491};


    double[] lat_t = { 39.959498, 39.947933, 39.943473 , 39.944686,   39.943955, 39.940899,  39.945463,   39.943972,   39.943755, 39.954564,    39.964025,   39.964025, 39.961267, 39.958878,  39.958609,   39.958062};
    double[] lng_t = {-75.220938,-75.223263, -75.220235 , -75.218454,  -75.217250 ,-75.212604 ,-75.208627 ,-75.206221 ,-75.192384 ,-75.181245,  -75.184514 , -75.184514 ,-75.206659 ,-75.206224 ,  -75.207937 , -75.209423};
	// Handle to SharedPreferences for this app
	SharedPreferences mPrefs;

	// Handle to a SharedPreferences editor
	SharedPreferences.Editor mEditor;
	// Define an object that holds accuracy and frequency parameters
	LocationRequest mLocationRequest;
	private PointInPolygon pointInPolygon;
	private View view;
    private GoogleMap map;
	public void setMap() {

		LatLng currentPosition;


		if (map == null) {
			return;
		}

		LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		if (location == null) {

			return;
		}



		currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
		map.setMyLocationEnabled(true);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 14));


	}

    public void initMap(){

        LatLng currentPosition;


        if (map == null) {
            return;
        }
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        PolygonOptions polyOptions = new PolygonOptions().add(
                new LatLng(lat_t[0], lng_t[0]),
                new LatLng(lat_t[1], lng_t[1]),
                new LatLng(lat_t[2], lng_t[2]),
                new LatLng(lat_t[3], lng_t[3]),
                new LatLng(lat_t[4], lng_t[4]),
                new LatLng(lat_t[5], lng_t[5]),
                new LatLng(lat_t[6], lng_t[6]),
                new LatLng(lat_t[7], lng_t[7]),
                new LatLng(lat_t[8], lng_t[8]),
                new LatLng(lat_t[9], lng_t[9]),
                new LatLng(lat_t[10], lng_t[10]),
                new LatLng(lat_t[11], lng_t[11]),
                new LatLng(lat_t[12], lng_t[12]),
                new LatLng(lat_t[13], lng_t[13]),
                new LatLng(lat_t[14], lng_t[14])
        ).fillColor(0x2fff00ff).strokeColor(0xffffff);

        PolygonOptions polyOptionsInner = new PolygonOptions().add(
                new LatLng(lat_temp[0], lng_temp[0]),
                new LatLng(lat_temp[1], lng_temp[1]),
                new LatLng(lat_temp[2], lng_temp[2]),
                new LatLng(lat_temp[3], lng_temp[3]),
                new LatLng(lat_temp[4], lng_temp[4]),
                new LatLng(lat_temp[5], lng_temp[5]),
                new LatLng(lat_temp[6], lng_temp[6]),
                new LatLng(lat_temp[7], lng_temp[7]),
                new LatLng(lat_temp[8], lng_temp[8]),
                new LatLng(lat_temp[9], lng_temp[9]),
                new LatLng(lat_temp[10], lng_temp[10]),
                new LatLng(lat_temp[11], lng_temp[11]),
                new LatLng(lat_temp[12], lng_temp[12]),
                new LatLng(lat_temp[13], lng_temp[13]),
                new LatLng(lat_temp[14], lng_temp[14]),
                new LatLng(lat_temp[15], lng_temp[15]),
                new LatLng(lat_temp[16], lng_temp[16])
        ).fillColor(0x2FA50000).strokeColor(0xffffff);
        Polygon polygon = map.addPolygon(polyOptions);
        Polygon polygonInner = map.addPolygon(polyOptionsInner);

        currentPosition = new LatLng(lat_temp[0],lng_temp[0]);
        map.setMyLocationEnabled(true);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 12));

    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {


		view = inflater.inflate(R.layout.fragment_location_track, container,
				false);
        map = ((MapFragment) this.getFragmentManager().findFragmentById(R.id.outofreachmap)).getMap();
        checkGPS();
        initMap();
		setMap();
		return view;
	}

    public void checkGPS(){
        AppVars.getInstance().enableLocation(getActivity());

    }

	@Override
	public void onDestroy() {


		Fragment curMap = (getFragmentManager().findFragmentById(R.id.outofreachmap));
		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
		ft.remove(curMap);
		try {
			ft.commit();
		} catch (IllegalStateException e) {

		}
		super.onDestroy();
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
}
