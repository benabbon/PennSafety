package com.android.pennaed.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.pennaed.R;

public class NumberOfPeopleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergency_activity_number_of_people);
	}

	public void onClickOnePerson(View view) {
		AppVars.getInstance().setOnlyOnePerson(true);
		goToNeedCPRActivity();
	}

	public void onClickMoreThanOnePerson(View view) {
		AppVars.getInstance().setOnlyOnePerson(false);
		goToNeedCPRActivity();
	}

	public void goToNeedCPRActivity() {
		Intent i = new Intent(this, NeedCPRActivity.class);
		startActivity(i);
	}
}
