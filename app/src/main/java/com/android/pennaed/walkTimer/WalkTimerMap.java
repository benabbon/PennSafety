package com.android.pennaed.walkTimer;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.android.pennaed.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sruthi on 10/15/14.
 */


public class WalkTimerMap {
	private GoogleMap map;
	private double currentLatitude;
	private double currentLongitude;
	private LatLng currentPosition;

	public boolean setMap(Activity parentActivity) {
		map = ((MapFragment) parentActivity.getFragmentManager().findFragmentById(R.id.walkTimerMap)).getMap();
		if (map == null) {
			return false;
		}
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		LocationManager lm = (LocationManager) parentActivity.getSystemService(Context.LOCATION_SERVICE);
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		if (location == null) {
			return false;
		}
		currentLongitude = location.getLongitude();
		currentLatitude = location.getLatitude();
		currentPosition = new LatLng(currentLatitude, currentLongitude);
		map.setMyLocationEnabled(true);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
		return true;
	}

	protected void getTimerFromDestinationClick(WalkTimerMapActivity walkTimerMapActivity) {
		map.setOnMapLongClickListener(
				DestinationTimer.getOnMapDestinationClick(walkTimerMapActivity, currentPosition));
	}

	protected GoogleMap getMap() {
		return map;
	}
}
