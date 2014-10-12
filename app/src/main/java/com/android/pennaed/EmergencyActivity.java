package com.android.pennaed;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pennaed.emergency.AEDInstructionsActivity;
import com.android.pennaed.emergency.CallActivity;
import com.android.pennaed.emergency.NumberOfPeopleActivity;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class EmergencyActivity extends Activity {


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

	private AEDMap aedMap;

	private OnMarkerClickListener markerClickListener = new OnMarkerClickListener() {

		@Override
		public boolean onMarkerClick(Marker marker) {
			marker.showInfoWindow();
			String aedId = pennAedApp._appVars.getAedId(marker.getId());
			for (AED aed : aedArrayList) {
				if (aed.getId().equals(aedId)) {
					aedInbuildingDirections.setText(aed.getInBuildingLocation());
					mapOverlayLayout.setVisibility(View.VISIBLE);
				}
			}
			return true;
		}
	};

	public void openInstructions(){//to be deleted afterwards, same method in AEDMap
		Intent i = new Intent(this, AEDInstructionsActivity.class);
		startActivityForResult(i,PennAEDFinals.EMERGENCY_AED_INSTRUCTIONS);
	}


	public void onCall(){//to be deleted afterwards, same method in NumberOfPeopleActivity
		Intent i = new Intent(this, CallActivity.class);
		startActivityForResult(i,PennAEDFinals.EMERGENCY_CPR_QUESTION);
	}

	public void numberOfPeople(){
		Intent i = new Intent(this, NumberOfPeopleActivity.class);
		startActivityForResult(i,PennAEDFinals.EMERGENCY_PEOPLE);
	}

	public void onClickNeedCPR(){
		AppVars.getInstance().setCPRNeeded(true);
		AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_INSTRUCTIONS);
//		loadView();

	}

	public void onClickNoCPRNeeded(){

		AppVars.getInstance().setCPRNeeded(false);
		if (AppVars.getInstance().getOnlyOnePerson()) {
			AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_WAIT);
		} else {
			AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_AED_MAP);
		}
//		loadView();

	}

	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				case R.id.emergency_button:
					break;
				case R.id.emergency_back_button:
					setPrevious();
					loadView();
					break;
				case R.id.emergency_start_button://should call NumberOfPeople
//					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_PEOPLE);
//					loadView();
					numberOfPeople();
					break;
				case R.id.one_person_button:
					onCall();
//					numberOfPeople();//won't be here
					break;
				case R.id.more_than_one_person_button:
//					numberOfPeople();//won't be here
					onCall();
					break;
				case R.id.call_button:
//					onCall();
					//actual call, shifted to CallActivity
					break;
				case R.id.cpr_yes:
//					pennAedApp._appVars.setCPRNeeded(true);
//					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_INSTRUCTIONS);
//					loadView();
					onClickNeedCPR();
					break;
				case R.id.cpr_no:
//					pennAedApp._appVars.setCPRNeeded(false);
//					if (pennAedApp._appVars.getOnlyOnePerson()) {
//						pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_WAIT);
//					} else {
//						pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_AED_MAP);
//					}
//					loadView();
					onClickNoCPRNeeded();
					break;
				case R.id.aed_button:
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_AED_MAP);
					loadView();
					break;
				case R.id.found_aed_button:
					openInstructions();
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

	private void setControllers() {
		mainLayout = (LinearLayout) findViewById(R.id.emergency_main_layout);
		emergencyStartView = getLayoutInflater().inflate(R.layout.emergency_start, null);
		emergencyPeopleView = getLayoutInflater().inflate(R.layout.emergency_people, null);
		emergencyCallView = getLayoutInflater().inflate(R.layout.emergency_call, null);
		emergencyCPRQuestionView = getLayoutInflater().inflate(R.layout.emergency_cpr_question, null);
		emergencyWaitView = getLayoutInflater().inflate(R.layout.emergency_wait, null);
		emergencyCPRInstructionsView = getLayoutInflater().inflate(R.layout.emergency_cpr_instructions, null);
		aedView = getLayoutInflater().inflate(R.layout.aed_map, null);
		emergencyAedInstructionsView = getLayoutInflater().inflate(R.layout.emergency_aed_instructions, null);

		mainLayout.addView(emergencyStartView);
		emergencyStartButton = (Button) findViewById(R.id.emergency_start_button);
		mainLayout.removeAllViews();

		mainLayout.addView(emergencyPeopleView);
		onePersonButton = (Button) findViewById(R.id.one_person_button);
		moreThanOnePersonButton = (Button) findViewById(R.id.more_than_one_person_button);
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

		backButton = (TextView) findViewById(R.id.emergency_back_button);
		emergencyButton = (Button) findViewById(R.id.emergency_button);
		backButton.setOnClickListener(clickListener);
		emergencyButton.setOnClickListener(clickListener);
	}

	private void setFields() {
		loadView();
		emergencyButton.setEnabled(false);
	}

	//disable back button press
	@Override
	public void onBackPressed() {
		setPrevious();
		loadView();
	}

	@Override
	public void onResume() {
		super.onResume();
		loadView();
	}

	private void loadView() {
		mainLayout.removeAllViews();
		switch (pennAedApp._appVars.getEmergencyStep()) {
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
				if (pennAedApp._appVars.getOnlyOnePerson()) {
					aedButton.setVisibility(View.INVISIBLE);
				}
				break;
			case PennAEDFinals.EMERGENCY_AED_MAP:
				setAEDMap();
				mainLayout.addView(aedView);
				break;
			case PennAEDFinals.EMERGENCY_AED_INSTRUCTIONS:
				mainLayout.addView(emergencyAedInstructionsView);
				break;
			default:
				break;
		}
	}

	private void setAEDMap() {

		aedArrayList = pennAedApp._appVars.getAEDArrayList();
		aedMap = new AEDMap();
		aedMap.setMap(aedArrayList, this, markerClickListener);

	}

	private void setPrevious() {
		switch (pennAedApp._appVars.getEmergencyStep()) {
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
				if (pennAedApp._appVars.getCPRNeeded()) {
					pennAedApp._appVars.setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_INSTRUCTIONS);
				} else {
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
