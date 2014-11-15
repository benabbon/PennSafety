package com.android.pennaed.walkTimer;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.android.pennaed.walkTimer.GeometryUtils.SphericalUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by nabilbenabbou1 on 10/20/14.
 */
public class DestinationTimer {

	public static View.OnClickListener getStartTimerByDesination(final Activity parentActivity) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(parentActivity, WalkTimerMapActivity.class);
				intent.putExtra("TIMER_TYPE", "DESTINATION_BASED");
				parentActivity.startActivity(intent);

			}
		};
	}

	public static GoogleMap.OnMapLongClickListener getOnMapDestinationClick(
			final WalkTimerMapActivity walkTimerMapActivity, final LatLng currentPosition) {
		return new GoogleMap.OnMapLongClickListener() {
			@Override
			public void onMapLongClick(LatLng latLng) {
				if (!walkTimerMapActivity.isTimerOn()) {
					double distance = SphericalUtil.computeDistanceBetween(latLng, currentPosition);
					double walkingSpeed = 1.11; // 4km/hour => 1.11m/s
					long time = (long) Math.round(distance / walkingSpeed);
					// Since the distance is over the air, we give an offset to match walking distance
					time *= 1.2;
					walkTimerMapActivity.setTimer(time * 1000);
					Toast.makeText(walkTimerMapActivity,
							time / 60 + " minutes and " + (time % 60) + " seconds to destination.",
							Toast.LENGTH_LONG).show();
					GoogleMap map = walkTimerMapActivity.map.getMap();
					map.clear();
					Marker startMarker = map.addMarker(
							new MarkerOptions().position(currentPosition).title("Start"));
					startMarker.showInfoWindow();
					Marker destinationMarker = map.addMarker(
							new MarkerOptions().position(latLng).title("Destination"));
					destinationMarker.showInfoWindow();
				}
			}
		};
	}
}
