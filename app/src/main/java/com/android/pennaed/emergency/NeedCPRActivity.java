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

public class NeedCPRActivity extends Activity {

	public void onClickNeedCPR(View view){
		AppVars.getInstance().setCPRNeeded(true);
		AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_INSTRUCTIONS);
//		loadView();
		CPRNeeded();
	}

	public void onClickNoCPRNeeded(View view){

		AppVars.getInstance().setCPRNeeded(false);
		if (AppVars.getInstance().getOnlyOnePerson()) {
			AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_WAIT);
		} else {
			AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_AED_MAP);
		}
//		loadView();
		noCPRNeeded();
	}

	public void CPRNeeded(){//starts CPRNeeded activity
		Intent i = new Intent(this, CPRInstructions.class);
		startActivityForResult(i,PennAEDFinals.EMERGENCY_CPR_INSTRUCTIONS);
	}

	public void noCPRNeeded(){//start wait activity

		Intent i = new Intent(this, WaitForHelp.class);
		startActivityForResult(i,PennAEDFinals.EMERGENCY_WAIT);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_cpr);
    }

}
