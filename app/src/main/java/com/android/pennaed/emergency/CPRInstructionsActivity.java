package com.android.pennaed.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.pennaed.R;

public class CPRInstructionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergency_activity_cprinstructions);
		if (AppVars.getInstance().getOnlyOnePerson()) {
			Button aedLocations = (Button) findViewById(R.id.aed_button);
			aedLocations.setVisibility(View.INVISIBLE);
		}
	}

	public void onClickAEDLocations(View view) {
		Intent i = new Intent(this, AEDMapActivity.class);
		startActivity(i);
	}
}
