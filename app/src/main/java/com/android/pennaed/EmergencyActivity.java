package com.android.pennaed;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class EmergencyActivity extends Activity{
	
	
	private PennAEDApp pennAedApp;
	private ArrayList<AED> aedArrayList;
	
	private LinearLayout mainLayout;
		
	private View emergencyStartView;
	private View emergencyPeopleView;
	private View emergencyCallView;
	private View emergencyCPRQuestionView;
	private View emergencyWaitView;
	private View emergencyCPRInstructionsView;
	
	private View aedView;
	private LinearLayout mapOverlayLayout;
	private TextView aedInbuildingDirections;
	
	private View emergencyAedInstructionsView;
	
	private Button emergencyButton;

	private TextView backButton;
	
	private Button emergencyStartButton;
	private Button onePersonButton;
	private Button moreThanOnePersonButton;
	private Button callButton;
	private Button cprYesButton;
	private Button cprNoButton;
	private Button aedButton;
	private Button foundAedButton;
	
	private GoogleMap map;
	private double currentLatitude;
	private double currentLongitude;
	private LatLng currentPosition;
	
	private OnMarkerClickListener markerClickListener = new OnMarkerClickListener() {
		
		@Override
		public boolean onMarkerClick(Marker marker) {
			marker.showInfoWindow();
			String aedId = pennAedApp._appVars.getAedId(marker.getId());
			for(AED aed : aedArrayList){
				if(aed.getId().equals(aedId)){
					aedInbuildingDirections.setText(aed.getInBuildingLocation());
					mapOverlayLayout.setVisibility(View.VISIBLE);
				}
			}
			return true;
		}
	};
	
	
	
	private View.OnClickListener clickListener = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			
			switch(v.getId()){
				case R.id.emergency_button:
					break;
				case R.id.emergency_back_button:
					setPrevious();
					loadView();
					break;
				case R.id.emergency_start_button:
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_PEOPLE);
					loadView();
					break;
				case R.id.one_person_button:
					pennAedApp._appVars.setOnlyOnePerson(true);
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CALL);
					loadView();
					break;
				case R.id.more_than_one_person_button:
					pennAedApp._appVars.setOnlyOnePerson(false);
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CALL);
					loadView();
					break;
				case R.id.call_button:
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_QUESTION);
			        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(PennAEDFinals.EMERGENCY_PHONE_NUMBER)); 
			        startActivity(callIntent);
					break;
				case R.id.cpr_yes:
					pennAedApp._appVars.setCPRNeeded(true);
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_INSTRUCTIONS);
					loadView();					
					break;
				case R.id.cpr_no:
					pennAedApp._appVars.setCPRNeeded(false);
					if(pennAedApp._appVars.getOnlyOnePerson()){
						pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_WAIT);
					}else{
						pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_AED_MAP);
					}
					loadView();
					break;
				case R.id.aed_button:
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_AED_MAP);
					loadView();
					break;
				case R.id.found_aed_button:
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_AED_INSTRUCTIONS);
					loadView();
				default:
					break;
				}
			}		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pennAedApp = (PennAEDApp) getApplication();
		pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_START);
		
		setContentView(R.layout.activity_emergency);
		
		setControllers();
		setFields();
		
	}
	
	private void setControllers(){
		mainLayout = (LinearLayout)findViewById(R.id.emergency_main_layout);
		emergencyStartView = getLayoutInflater().inflate(R.layout.emergency_start, null);
		emergencyPeopleView = getLayoutInflater().inflate(R.layout.emergency_people, null);
		emergencyCallView = getLayoutInflater().inflate(R.layout.emergency_call, null);
		emergencyCPRQuestionView = getLayoutInflater().inflate(R.layout.emergency_cpr_question, null);
		emergencyWaitView = getLayoutInflater().inflate(R.layout.emergency_wait, null);
		emergencyCPRInstructionsView = getLayoutInflater().inflate(R.layout.emergency_cpr_instructions, null);
		aedView = getLayoutInflater().inflate(R.layout.aed_map, null);
		emergencyAedInstructionsView = getLayoutInflater().inflate(R.layout.emergency_aed_instructions, null);
		
		mainLayout.addView(emergencyStartView);
		emergencyStartButton = (Button)findViewById(R.id.emergency_start_button);
		mainLayout.removeAllViews();
		
		mainLayout.addView(emergencyPeopleView);
		onePersonButton = (Button)findViewById(R.id.one_person_button);
		moreThanOnePersonButton = (Button)findViewById(R.id.more_than_one_person_button);
		mainLayout.removeAllViews();
		
		mainLayout.addView(emergencyCallView);
		callButton = (Button) findViewById(R.id.call_button);
		mainLayout.removeAllViews();
		
		mainLayout.addView(emergencyCPRQuestionView);
		cprYesButton = (Button) findViewById(R.id.cpr_yes);
		cprNoButton = (Button) findViewById(R.id.cpr_no);
		mainLayout.removeAllViews();
		
		mainLayout.addView(emergencyCPRInstructionsView);
		aedButton = (Button) findViewById(R.id.aed_button);
		mainLayout.removeAllViews();
		
		mainLayout.addView(aedView);
		foundAedButton = (Button) findViewById(R.id.found_aed_button);
		mapOverlayLayout = (LinearLayout) findViewById(R.id.map_overlay);
		aedInbuildingDirections = (TextView) findViewById(R.id.aed_inbuilding_directions);
		mainLayout.removeAllViews();
		
		emergencyStartButton.setOnClickListener(clickListener);
		onePersonButton.setOnClickListener(clickListener);
		moreThanOnePersonButton.setOnClickListener(clickListener);
		callButton.setOnClickListener(clickListener);
		cprYesButton.setOnClickListener(clickListener);
		cprNoButton.setOnClickListener(clickListener);
		aedButton.setOnClickListener(clickListener);
		foundAedButton.setOnClickListener(clickListener);
		
		backButton = (TextView)findViewById(R.id.emergency_back_button);
		emergencyButton = (Button)findViewById(R.id.emergency_button) ;
		backButton.setOnClickListener(clickListener);
		emergencyButton.setOnClickListener(clickListener);
	}
	
	private void setFields(){
		loadView();
		emergencyButton.setEnabled(false);	
	}
	
	private void setMap(){
		aedArrayList = pennAedApp._appVars.getAEDArrayList();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		if (map == null) {
			return;
		}
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		Location location = null;
		if(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) == null) {
			location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		else {
			location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		currentLongitude = location.getLongitude();
		currentLatitude = location.getLatitude();
		map.setMyLocationEnabled(true);
		currentPosition = new LatLng(currentLatitude, currentLongitude);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 18));
		map.setOnMarkerClickListener(markerClickListener);
		AED closestAED = getClosestAED();
		for (AED aed: aedArrayList) {
			MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(aed.getLatitude(), aed.getLongitude())).title(aed.getName());
			if(aed.equals(closestAED)){
				markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.green_cross));
			}
			else{
				markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.redcross));
			}
			Marker m = map.addMarker(markerOptions);
			pennAedApp._appVars.addToMarkerAedHM(m.getId(), aed.getId());
		}
}
	
	private AED getClosestAED() {
		AED closestAED = aedArrayList.get(0);
		double distance = Double.POSITIVE_INFINITY;
		for (AED aed: aedArrayList) {
			double new_dist = Math.sqrt(Math.pow((aed.getLatitude() - currentLatitude), 2) 
					+ Math.pow((aed.getLongitude() - currentLongitude), 2));
			if (new_dist < distance) {
				distance = new_dist;
				closestAED = aed;
			}
		}
		return closestAED;
	}
	
	
	//disable back button press
	@Override
	public void onBackPressed() {
		setPrevious();
		loadView();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		loadView();
	}
	
	private void loadView(){
		mainLayout.removeAllViews();
		switch (pennAedApp._appVars.getEmergencyStep()){
			case PennAEDFinals.EMERGENCY_START:
				mainLayout.addView(emergencyStartView);
				break;
			case PennAEDFinals.EMERGENCY_PEOPLE:
				mainLayout.addView(emergencyPeopleView);
				break;
			case PennAEDFinals.EMERGENCY_CALL:
				mainLayout.addView(emergencyCallView);
				break;
			case PennAEDFinals.EMERGENCY_CPR_QUESTION:
				mainLayout.addView(emergencyCPRQuestionView);
				break;
			case PennAEDFinals.EMERGENCY_WAIT:
				mainLayout.addView(emergencyWaitView);
				break;				
			case PennAEDFinals.EMERGENCY_CPR_INSTRUCTIONS:
				mainLayout.addView(emergencyCPRInstructionsView);
				if(pennAedApp._appVars.getOnlyOnePerson()){
					aedButton.setVisibility(View.INVISIBLE);
				}
				break;
			case PennAEDFinals.EMERGENCY_AED_MAP:
				setMap();
				mainLayout.addView(aedView);
				break;
			case PennAEDFinals.EMERGENCY_AED_INSTRUCTIONS:
				mainLayout.addView(emergencyAedInstructionsView);
				break;
			default:
				break;
		}
	}
	
	private void setPrevious(){
		switch (pennAedApp._appVars.getEmergencyStep()){
			case PennAEDFinals.EMERGENCY_START:
				pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_START);
				break;
			case PennAEDFinals.EMERGENCY_PEOPLE:
				pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_START);
				break;
			case PennAEDFinals.EMERGENCY_CALL:
				pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_PEOPLE);
				break;
			case PennAEDFinals.EMERGENCY_CPR_QUESTION:
				pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CALL);
				break;
			case PennAEDFinals.EMERGENCY_WAIT:
				pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_QUESTION);
				break;
			case PennAEDFinals.EMERGENCY_CPR_INSTRUCTIONS:
				pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_QUESTION);
				break;
			case PennAEDFinals.EMERGENCY_AED_MAP:
				if(pennAedApp._appVars.getCPRNeeded()){
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_INSTRUCTIONS);
				}else{
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_QUESTION);
				}
				break;
			case PennAEDFinals.EMERGENCY_AED_INSTRUCTIONS:
				pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_AED_MAP);
				break;
			default:
				break;
		}
	}


}
