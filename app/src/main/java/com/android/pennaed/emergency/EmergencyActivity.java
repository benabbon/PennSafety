package com.android.pennaed.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.pennaed.R;
import com.android.pennaed.contacts.MainActivity;

public class EmergencyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergency_activity_main);
	}

	public void onClickEmergencyStart(View view) {
		Intent i = new Intent(this, NumberOfPeopleActivity.class);
		startActivity(i);
	}

	public void onClickEmergencyCall(View view) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

}
