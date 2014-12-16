package com.android.pennaed.test.emergency.waitForHelpActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.R;
import com.android.pennaed.contacts.Info;
import com.android.pennaed.emergency.AEDInstructionsActivity;
import com.android.pennaed.emergency.AEDMapActivity;
import com.android.pennaed.emergency.WaitForHelpActivity;

/**
 * Created by sruthi on 12/16/14.
 */
public class WaitForHelpActivityTest extends ActivityUnitTestCase<WaitForHelpActivity> {

	WaitForHelpActivity waitForHelpActivity;
	public WaitForHelpActivityTest(){
		super(WaitForHelpActivity.class);
		waitForHelpActivity = new WaitForHelpActivity();
	}

	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), WaitForHelpActivity.class);
		startActivity(intent, null, null);
		waitForHelpActivity = getActivity();
	}

	@SmallTest
	public void testNonNullViews() {
		int emergencyStartButtonTextID = R.id.emergency_start_button_text;
		assertNotNull(waitForHelpActivity.findViewById(emergencyStartButtonTextID));
	}

}
