package com.android.pennaed.emergency;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import com.android.pennaed.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppVars {

	private static AppVars instance;

	//relate marker id to aedId
	private Map<String, String> markerIdToAedId;

	//emergency view location
	private int emergencyStep;

	//people present
	private boolean onlyOnePerson;

	//cpr needed
	private boolean cprNeeded;

	private ArrayList<AED> aedArrayList = null;

	private HashMap<Integer, AED> aedIntegerMap;

	//Restricting constructor
	private AppVars() {
	}

	//create/get instance -> singleton class
	public static synchronized AppVars getInstance() {
		if (instance == null) {
			instance = new AppVars();
			instance.setOnlyOnePerson(true);
			instance.setCPRNeeded(true);
			instance.setAEDArrayList();
		}
		return instance;
	}

	private void setAEDArrayList() {
		aedArrayList = new ArrayList<AED>();
		aedIntegerMap = new HashMap<Integer, AED>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AEDInformation");
		query.setLimit(150);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> aedList, ParseException e) {
				if (e == null) {
					int integerId = 0;
					for (ParseObject aedParse : aedList) {
						AED aed = new AED(aedParse.getObjectId(), aedParse.getString("name"),
								aedParse.getDouble("latitude"), aedParse.getDouble("longitude"),
								aedParse.getString("address"), aedParse.getString("inBuildingDirections"),
								aedParse.getString("contactName"), aedParse.getInt("contactPhone"),
								aedParse.getUpdatedAt(), aedParse.getParseFile("photoFile"), integerId);
						aedIntegerMap.put(integerId, aed);
						aedArrayList.add(aed);
						integerId++;
					}
				} else {
					Log.d("AEDLOAD", "Error: " + e.getMessage());
				}
			}
		});
	}

	public ArrayList<AED> getAEDArrayList() {
		if (aedArrayList == null) {
			setAEDArrayList();
		}
		return aedArrayList;
	}

	public AED gedAedByInt(Integer id) {
		return aedIntegerMap.get(id);
	}

	public int getEmergencyStep() {
		return emergencyStep;
	}

	public void setEmergencyStep(int emergencyStep) {
		this.emergencyStep = emergencyStep;
	}

	public boolean getOnlyOnePerson() {
		return onlyOnePerson;
	}

	public void setOnlyOnePerson(boolean onlyOnePerson) {
		this.onlyOnePerson = onlyOnePerson;
	}

	public boolean getCPRNeeded() {
		return cprNeeded;
	}

	public void setCPRNeeded(boolean cprNeeded) {
		this.cprNeeded = cprNeeded;
	}

	public void addToMarkerAedHM(String markerId, String aedId) {
		if (markerIdToAedId == null) markerIdToAedId = new HashMap<String, String>();
		markerIdToAedId.put(markerId, aedId);
	}

	public String getAedId(String markerId) {
		return markerIdToAedId.get(markerId);
	}

	public void enableLocation(final Activity activity) {
		if (activity == null) {
			return;
		}
		LocationManager service = (LocationManager) activity.
				getSystemService(activity.LOCATION_SERVICE);
		boolean gpsEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean networkEnabled = service
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		// Check if enabled, if not send to the GPS settings. Dialogue box
		// appears if app is opened for first time

		if (!gpsEnabled) {

			AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

			// Setting Dialog Title
			alertDialog.setTitle(activity.getString(R.string.gps_enable_alert_title));

			// Setting Dialog Message
			alertDialog.setMessage(activity.getString(R.string.gps_enable_alert_details));

			// Setting Positive "Yes" Button
			alertDialog.setPositiveButton("yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							// Activity transfer to wifi settings
							Intent intent = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							activity.startActivity(intent);
						}
					});

			// Setting Negative "NO" Button
			alertDialog.setNegativeButton("no",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							dialog.cancel();
						}
					});

			// Showing Alert Message
			alertDialog.show();
		}
	}

}
