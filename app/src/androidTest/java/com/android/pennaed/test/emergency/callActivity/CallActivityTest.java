package com.android.pennaed.test.emergency.callActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.R;
import com.android.pennaed.contacts.Info;
import com.android.pennaed.emergency.CallActivity;

/**
 * Created by sruthi on 12/16/14.
 */
public class CallActivityTest extends ActivityUnitTestCase<CallActivity>{
	private CallActivity callActivity;

	public CallActivityTest() {
		super(CallActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), Info.class);
		startActivity(intent, null, null);
		callActivity = getActivity();
	}

	@SmallTest
	public void testNonNullViews() {
		int emergencyStartButtonTextID = R.id.emergency_start_button_text;
		assertNotNull(callActivity.findViewById(emergencyStartButtonTextID));
		int callButtonID = R.id.call_button;
		assertNotNull(callActivity.findViewById(callButtonID));
	}
}
