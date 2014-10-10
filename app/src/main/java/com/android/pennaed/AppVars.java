package com.android.pennaed;

import android.util.Log;

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

	private ArrayList<AED> aedArrayList;

	private HashMap<Integer, AED> aedIntegerMap;

	//Restricting constructor
	private AppVars() {
	}

	//create/get instance -> singleton class
	public static synchronized AppVars getInstance() {
		if (instance == null) {
			instance = new AppVars();
			instance.setEmergencyStep(PennAEDFinals.EMERGENCY_START);
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
		return aedArrayList;
	}

	public AED gedAedByInt(Integer id) {
		return aedIntegerMap.get(id);
	}

	public void setEmergencyStep(int emergencyStep) {
		this.emergencyStep = emergencyStep;
	}

	public int getEmergencyStep() {
		return emergencyStep;
	}

	public void setOnlyOnePerson(boolean onlyOnePerson) {
		this.onlyOnePerson = onlyOnePerson;
	}

	public boolean getOnlyOnePerson() {
		return onlyOnePerson;
	}

	public void setCPRNeeded(boolean cprNeeded) {
		this.cprNeeded = cprNeeded;
	}

	public boolean getCPRNeeded() {
		return cprNeeded;
	}

	public void addToMarkerAedHM(String markerId, String aedId) {
		if (markerIdToAedId == null) markerIdToAedId = new HashMap<String, String>();
		markerIdToAedId.put(markerId, aedId);
	}

	public String getAedId(String markerId) {
		return markerIdToAedId.get(markerId);
	}

}
