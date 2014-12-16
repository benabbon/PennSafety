package com.android.pennaed.test.walkTimer.walkTimerMapActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.R;
import com.android.pennaed.contacts.Info;
import com.android.pennaed.walkTimer.WalkTimerMapActivity;

/**
 * Created by sruthi on 12/16/14.
 */
public class WalkTimerMapActivityTest extends ActivityUnitTestCase<WalkTimerMapActivity> {
	private WalkTimerMapActivity walkTimerMapActivity;

	public WalkTimerMapActivityTest() {
		super(WalkTimerMapActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), WalkTimerMapActivity.class);
		startActivity(intent, null, null);
		walkTimerMapActivity = getActivity();
	}

	@SmallTest
	public void testNonNullLayout() {
		int layoutID = R.id.linearLayout;
		assertNotNull(walkTimerMapActivity.findViewById(layoutID));
	}

	@SmallTest
	public void testNonNullButton() {
		int buttonId = R.id.stop_timer_button;
		assertNotNull(walkTimerMapActivity.findViewById(buttonId));
	}
}
