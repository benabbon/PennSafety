package com.android.pennaed.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.pennaed.AppVars;
import com.android.pennaed.R;

public class NumberOfPeopleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_of_people);
	}

	public void onClickOnePerson(View view) {
		AppVars.getInstance().setOnlyOnePerson(true);
		call();
	}

	public void onClickMoreThanOnePerson(View view) {
		AppVars.getInstance().setOnlyOnePerson(false);
		call();
	}

	public void call() {
		Intent i = new Intent(this, CallActivity.class);
		startActivity(i);
	}
}
