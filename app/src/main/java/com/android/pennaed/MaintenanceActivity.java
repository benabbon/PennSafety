package com.android.pennaed;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MaintenanceActivity extends Activity {
	
	static final int REQUEST_IMAGE_CAPTURE = 1;
	
	private Button emergencyButton;
	private Button maintenanceButton;
	private ArrayList<AED> aedArrayList;
	private PennAEDApp pennAedApp;
	private LinearLayout mainLayout;
	private View statusView;
	private View aedListView;
	private View subscribeView;
	private LinearLayout aedButtonList;
	
	private TextView statusTitle;
	private TextView aedStatus;
	private TextView dateText;
	private EditText indoorDirections;
	
	private ImageButton photoButton;
	private Button subscribeButton;
	private Button subscribeToUpdatesButton;
	private Button editStatusInfoButton;
	
	private Bitmap imageBitmap;
	private byte[] photoData;
	
	private int currentAEDId;
	private EditText nameText;
	private EditText emailText;
	
	private View.OnClickListener clickListener = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			
			switch(v.getId()){
				case R.id.emergency_button:
					finish();
					break;
				case R.id.maintenance_button:
					break;
				default:
					break;
				}
			}		
	};
	
	private View.OnClickListener editListener = new View.OnClickListener() {
		
		
		@Override
		public void onClick(View v) {
			AED aed = pennAedApp._appVars.gedAedByInt(currentAEDId);
			updateAEDStatusParse(aed.getId(), indoorDirections.getText().toString());
		}
	};
	
	
	private View.OnClickListener aedListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			setStatusView(id);
		}
	};
	
	
	private View.OnClickListener subscribePageListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			showSubscribePage();
			
		}
	};
	
	private View.OnClickListener photoListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			dispatchTakePictureIntent();
		}
			
	};
	
	private View.OnClickListener subscribeForUpdatesListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			v.setBackgroundColor(Color.MAGENTA);
			AED aed = pennAedApp._appVars.gedAedByInt(currentAEDId);
			sendSubscribe(aed.getId());
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pennAedApp = (PennAEDApp) getApplication();
		aedArrayList = pennAedApp._appVars.getAEDArrayList();
		setContentView(R.layout.activity_maintenance);
		setControllers();
		setFields();
	}
	
	
	
	private void setControllers(){
		emergencyButton = (Button)findViewById(R.id.emergency_button) ;
		maintenanceButton = (Button)findViewById(R.id.maintenance_button);
		
		mainLayout = (LinearLayout)findViewById(R.id.mainenance_main_layout);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		statusView = getLayoutInflater().inflate(R.layout.maintenance_status, null);
		aedListView = getLayoutInflater().inflate(R.layout.maintenance_list, null);
		subscribeView = getLayoutInflater().inflate(R.layout.subscribe_page, null);
		
		mainLayout.addView(statusView);
		mainLayout.addView(aedListView);
		mainLayout.addView(subscribeView);
		
		aedButtonList = (LinearLayout)findViewById(R.id.aed_maintenance_buttons);
		statusTitle = (TextView)findViewById(R.id.status_title);
		aedStatus = (TextView)findViewById(R.id.status);
		dateText = (TextView)findViewById(R.id.last_checked_date);
		indoorDirections = (EditText)findViewById(R.id.indoor_directions);
		subscribeButton = (Button)findViewById(R.id.subscribe_button);
		subscribeButton.setOnClickListener(subscribePageListener);
		subscribeToUpdatesButton = (Button)findViewById(R.id.subscribe_to_updates);
		subscribeToUpdatesButton.setOnClickListener(subscribeForUpdatesListener);
		
		photoButton = (ImageButton)findViewById(R.id.status_photo);
		photoButton.setOnClickListener(photoListener);
		
		editStatusInfoButton = (Button)findViewById(R.id.edit_button);
		editStatusInfoButton.setOnClickListener(editListener);
		
		
		nameText = (EditText)findViewById(R.id.name_text);
		emailText = (EditText)findViewById(R.id.email_text);
		
		// remove all
		mainLayout.removeAllViews();
		
		emergencyButton.setOnClickListener(clickListener);
		maintenanceButton.setOnClickListener(clickListener);
		
		
		for(int i = 0; i < aedArrayList.size(); i++) {
			AED aed = aedArrayList.get(i);
			GradientDrawable background = new GradientDrawable();
			background.setColor(Color.WHITE);
			background.setStroke(1, Color.BLACK);
			Button aedMaintenanceButton = new Button(this);
			aedMaintenanceButton.setText(aed.getName());
			aedMaintenanceButton.setBackground(background);
			aedMaintenanceButton.setId(aed.getIntegerId());
			aedMaintenanceButton.setOnClickListener(aedListener);
			aedButtonList.addView(aedMaintenanceButton);
		}
		mainLayout.addView(aedListView);
	}
	
	private void setStatusView(int id) {
		mainLayout.removeAllViews();
		currentAEDId = id;
		AED aed = pennAedApp._appVars.gedAedByInt(id);
		mainLayout.addView(statusView);
		statusTitle.setText(aed.getName());
		
		byte[] data;
		ParseFile pf = aed.getPhoto();
		if (pf != null) {
			try {
				data = pf.getData();
			}
			catch (ParseException e){
				data = null;
			}
			if (data != null) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				photoButton.setImageBitmap(bitmap);
			}
		}
		
		boolean status = aed.getStatus();
		if(status) {
			aedStatus.setBackgroundColor(Color.parseColor("#00FF00"));
			aedStatus.setText("Status: Normal");
		}
		else {
			aedStatus.setBackgroundColor(Color.parseColor("#FF0000"));
			aedStatus.setText("Status: Needs to be checked");
		}
		dateText.setText("Last checked: " + aed.getUpdatedDate());
		indoorDirections.setText(aed.getInBuildingLocation());
		
	}
	
	private void showSubscribePage() {
		mainLayout.removeAllViews();
		mainLayout.addView(subscribeView);
	}
	
	private void sendSubscribe(String aedObjectId) {
		String name = nameText.getText().toString();
		String email = emailText.getText().toString();
		if(nameText.getText().length() == 0 || 
				emailText.getText().length() == 0 ||
				!email.contains("@") || !email.contains(".")) {
			String text = "Invalid input.";
			Toast toast = Toast.makeText(this.getBaseContext(), text, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.TOP, 0, 10);
			toast.show();
		}
		else {
			updateSubscribeParse(aedObjectId, name, email);
		}
	}
	
	private void updateSubscribeParse(String aedObjectId, String name, String email) {
		ParseObject subscription = new ParseObject("AEDSubscriptions");
		subscription.put("aedObjectId", aedObjectId);
		subscription.put("name", name);
		subscription.put("email", email);
		subscription.saveInBackground();
	}
	
	private void updateAEDStatusParse(String aedObjectId, String indoorDirections) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("AEDInformation");
		ParseObject aed;
		try {
			aed = query.get(aedObjectId);
		} catch (ParseException e) {
			aed = null;
			return;
		}
		Date date = new Date();
		aed.put("inBuildingDirections", indoorDirections);
		aed.put("lastChecked", date.toString());
		ParseFile pf = new ParseFile(aedObjectId + ".png", photoData);
		aed.put("photoFile", pf);
		aed.saveInBackground();
	}
	
	private void setFields(){
		maintenanceButton.setEnabled(false);
	}
	
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	        
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        imageBitmap = (Bitmap) extras.get("data");
	        photoButton.setImageBitmap(imageBitmap);
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	        photoData = stream.toByteArray();
	    }
	}
	
	
	//disable back button press
	@Override
	public void onBackPressed() {}
	
	

}
