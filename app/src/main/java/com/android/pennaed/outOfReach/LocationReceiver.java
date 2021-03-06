package com.android.pennaed.outOfReach;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.android.pennaed.contacts.PointInPolygon;
import com.google.android.gms.location.LocationClient;

/**
 * Created by sruthi on 11/8/14.
 */
public class LocationReceiver extends BroadcastReceiver {
	private static String TAG = "LocationBroadcastReceiver";
	private static boolean showNotification = true;
	private double[] lat_temp = {39.954844, 39.958002, 39.952491, 39.951472,
			39.949449, 39.949523, 39.949869, 39.949260, 39.949663,
			39.951571};
	private double[] lng_temp = {-75.183274, -75.208213, -75.209388, -75.209398,
			-75.209291, -75.207167, -75.201309, -75.184550, -75.184057,
			-75.182544};
	private PointInPolygon pointInPolygon;

	public static void enableNotifications() {
		showNotification = true;
	}

	public static void disableNotifications() {
		showNotification = false;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Location location = (Location) intent.getExtras().get(LocationClient.KEY_LOCATION_CHANGED);
		String msg = "Updated Location: " +
				Double.toString(location.getLatitude()) + "," +
				Double.toString(location.getLongitude());
		//Toast.makeText(context, "Called location receiver", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "Receiver called with: " + location.getLatitude() + " " + location.getLongitude() + " WITH already inside: " + showNotification);
		pointInPolygon = new PointInPolygon(lat_temp, lng_temp);
		if (!pointInPolygon.coordinate_is_inside_polygon(location.getLatitude(), location.getLongitude())) {
			if (showNotification) {
				showNotification = false;
				Notification.createOutOfReachNotification(context);
			}
		} else {
			showNotification = true;
		}
	}

	public PointInPolygon getPointInPolygon(){
		return pointInPolygon;
	}

	public boolean getShowNotifications(){
		return showNotification;
	}
}
