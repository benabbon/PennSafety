package com.android.pennaed.walkTimer;

import android.app.Activity;
import android.app.Fragment;
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

    public void setMap(Activity parentActivity) {
        map = ((MapFragment) parentActivity.getFragmentManager().findFragmentById(R.id.walkTimerMap)).getMap();
        if (map == null) {
            return;
        }
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LocationManager lm = (LocationManager) parentActivity.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        if (lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) == null) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        currentLongitude = location.getLongitude();
        currentLatitude = location.getLatitude();
        map.setMyLocationEnabled(true);
        currentPosition = new LatLng(currentLatitude, currentLongitude);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 18));
    }
}
