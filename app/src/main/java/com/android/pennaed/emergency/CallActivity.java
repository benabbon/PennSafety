package com.android.pennaed.emergency;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.pennaed.AppVars;
import com.android.pennaed.PennAEDFinals;
import com.android.pennaed.R;

public class CallActivity extends Activity {

	public void onCall(View view){
		AppVars.getInstance().setEmergencyStep(PennAEDFinals.EMERGENCY_CPR_QUESTION);
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(PennAEDFinals.EMERGENCY_PHONE_NUMBER));
		startActivity(callIntent);
	}

	public void needCPRActivity(){//starts NeedCPRActivity
		Intent i = new Intent(this, NeedCPRActivity.class);
		startActivityForResult(i,PennAEDFinals.EMERGENCY_CPR_QUESTION);
	}

	@Override
	public void onResume() {
		super.onResume();
		needCPRActivity();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
    }

}
