package com.android.pennaed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.pennaed.emergency.NumberOfPeopleActivity;

public class EmergencyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency);
	}

	public void onClickEmergencyStart(View view) {
		Intent i = new Intent(this, NumberOfPeopleActivity.class);
		startActivity(i);
	}
}
