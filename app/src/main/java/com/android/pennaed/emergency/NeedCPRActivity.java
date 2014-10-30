package com.android.pennaed.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.pennaed.R;

public class NeedCPRActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergency_activity_need_cpr);
	}

	public void onClickCPRNeeded(View view) {
		AppVars.getInstance().setCPRNeeded(true);
		CPRNeeded();
	}

	public void onClickCPRNotNeeded(View view) {
		AppVars.getInstance().setCPRNeeded(false);
		noCPRNeeded();
	}

	public void CPRNeeded() {
		Intent i = new Intent(this, CPRInstructionsActivity.class);
		startActivity(i);
	}

	public void noCPRNeeded() {
		Intent i;
		if (AppVars.getInstance().getOnlyOnePerson()) {
			i = new Intent(this, WaitForHelpActivity.class);
		} else {
			i = new Intent(this, AEDMapActivity.class);
		}
		startActivity(i);
	}

}
