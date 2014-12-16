package com.android.pennaed.test.emergency;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.R;
import com.android.pennaed.contacts.Info;
import com.android.pennaed.emergency.AEDInstructionsActivity;
import com.android.pennaed.emergency.AEDMapActivity;

/**
 * Created by chaitalisg on 12/16/14.
 */
public class AEDInstructionsActivityTest extends ActivityUnitTestCase<AEDInstructionsActivity> {

	AEDInstructionsActivity aedInstructionsActivity;
	AEDMapActivity aedMapActivity;

	public AEDInstructionsActivityTest(){
		super(AEDInstructionsActivity.class);
		aedMapActivity = new AEDMapActivity();
	}

	public void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent(aedMapActivity, AEDInstructionsActivity.class);
		aedInstructionsActivity.startActivity(i);
	}

	@SmallTest
	public void testNonNullViews() {

	}
}

