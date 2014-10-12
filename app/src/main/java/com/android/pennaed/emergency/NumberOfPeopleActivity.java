package com.android.pennaed.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.pennaed.AppVars;
import com.android.pennaed.PennAEDFinals;
import com.android.pennaed.R;

public class NumberOfPeopleActivity extends Activity {

	public void onClickOnePerson(View view){

		AppVars.getInstance().setOnlyOnePerson(true);
		AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_CALL);
		onCall();
	}

	public void onClickMoreThanOnePerson(View view){

		AppVars.getInstance().setOnlyOnePerson(false);
		AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_CALL);
		onCall();
	}

	public void onCall(){//starts CallActivity
		Intent i = new Intent(this, CallActivity.class);
		startActivityForResult(i,PennAEDFinals.EMERGENCY_CPR_QUESTION);
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//	    AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_START);
        setContentView(R.layout.activity_number_of_people);
    }

}
