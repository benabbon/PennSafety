package com.android.pennaed.emergency;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.android.pennaed.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by chaitalisg on 10/10/14.
 */
public class AEDMap {

	private GoogleMap map;
	private double currentLatitude;
	private double currentLongitude;
	private LatLng currentPosition;

	public void setMap(ArrayList<AED> aedArrayList, Activity parentActivity, OnMarkerClickListener markerClickListener) {
		AppVars.getInstance().enableLocation(parentActivity);
		map = ((MapFragment) parentActivity.getFragmentManager().findFragmentById(R.id.map)).getMap();
		if (map == null) {
			return;
		}
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		LocationManager lm = (LocationManager) parentActivity.getSystemService(Context.LOCATION_SERVICE);
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		if (location != null) {
			currentLongitude = location.getLongitude();
			currentLatitude = location.getLatitude();
			currentPosition = new LatLng(currentLatitude, currentLongitude);
		} else {
			currentPosition = PennAEDFinals.DEFAULT_POSITION;
		}
		map.setMyLocationEnabled(true);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 18));
		map.setOnMarkerClickListener(markerClickListener);
		AED closestAED = getClosestAED(aedArrayList);
		for (AED aed : aedArrayList) {
			MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(aed.getLatitude(), aed.getLongitude())).title(aed.getName());
			if (aed.equals(closestAED)) {
				markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.green_cross));
			} else {
				markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.redcross));
			}
			Marker m = map.addMarker(markerOptions);
			AppVars.getInstance().addToMarkerAedHM(m.getId(), aed.getId());
		}
	}

	private AED getClosestAED(ArrayList<AED> aedArrayList) {
		if (aedArrayList.isEmpty()) {
			return null;
		}
		AED closestAED = aedArrayList.get(0);
		double distance = Double.POSITIVE_INFINITY;
		for (AED aed : aedArrayList) {
			double new_dist = Math.sqrt(Math.pow((aed.getLatitude() - currentLatitude), 2)
					+ Math.pow((aed.getLongitude() - currentLongitude), 2));
			if (new_dist < distance) {
				distance = new_dist;
				closestAED = aed;
			}
		}
		return closestAED;
	}

}
