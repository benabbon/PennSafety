package com.android.pennaed.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pennaed.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class AEDMapActivity extends Activity {

	private ArrayList<AED> aedArrayList;
	private GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {

		@Override
		public boolean onMarkerClick(Marker marker) {
			LinearLayout mapOverlayLayout = (LinearLayout) findViewById(R.id.map_overlay);
			TextView aedInbuildingDirections = (TextView) findViewById(R.id.aed_inbuilding_directions);
			marker.showInfoWindow();
			String aedId = AppVars.getInstance().getAedId(marker.getId());
			for (AED aed : aedArrayList) {
				if (aed.getId().equals(aedId)) {
					aedInbuildingDirections.setText(aed.getInBuildingLocation());
					mapOverlayLayout.invalidate();
					mapOverlayLayout.bringToFront();
					mapOverlayLayout.setVisibility(View.VISIBLE);
				}
			}
			return true;
		}
	};
	private AEDMap aedMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergency_activity_aedmap);
		aedMap = new AEDMap();
		aedArrayList = AppVars.getInstance().getAEDArrayList();
		aedMap.setMap(aedArrayList, this, markerClickListener);
	}

	public void onClickFoundAED(View v) {
		Intent i = new Intent(this, AEDInstructionsActivity.class);
		startActivity(i);
	}

}
