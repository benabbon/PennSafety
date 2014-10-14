package com.android.pennaed.emergency;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.android.pennaed.PennAEDFinals;
import com.android.pennaed.R;

public class CallActivity extends Activity {

	// This is used to decide when to move to next screen by calling needCPRActivity
	private int madeTheCall = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (madeTheCall == 1) {
			needCPRActivity();
		}
		madeTheCall++;
	}

	public void onCall(View view) {
		Intent callIntent = new Intent(Intent.ACTION_CALL,
				Uri.parse(PennAEDFinals.EMERGENCY_PHONE_NUMBER));
		startActivity(callIntent);
	}

	public void needCPRActivity() {
		Intent i = new Intent(this, NeedCPRActivity.class);
		startActivity(i);
	}

}