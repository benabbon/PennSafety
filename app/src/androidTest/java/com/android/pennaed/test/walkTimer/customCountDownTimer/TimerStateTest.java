package com.android.pennaed.test.walkTimer.customCountDownTimer;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.walkTimer.CustomCountDownTimer;
import com.android.pennaed.walkTimer.WalkTimerMapActivity;

import junit.framework.TestCase;
import com.android.pennaed.R;

/**
 * Created by sruthi on 12/2/14.
 */
public class TimerStateTest extends ActivityUnitTestCase<WalkTimerMapActivity>{
	private WalkTimerMapActivity parentActivity;
	private CustomCountDownTimer timer;
	public TimerStateTest() {
		super(WalkTimerMapActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), WalkTimerMapActivity.class);
		startActivity(intent, null, null);
		parentActivity = getActivity();
		timer = new CustomCountDownTimer(parentActivity);
	}

	@SmallTest
	public void testStartTimer() {
		timer.startTimer(100000);
		assertEquals(true, timer.isRunning());
	}

	@SmallTest
	public void testFinishTimer() {
		timer.startTimer(20000);
		timer.stopTimer();
		assertEquals(true, timer.isStopped());
		assertEquals(false, timer.isRunning());
	}

	@SmallTest
	public void testRunningTimer() {
		timer.startTimer(10000);
		assertEquals(true, timer.isRunning());
		try {
			Thread.sleep(10000);
			assertEquals(false, timer.isRunning());
			assertEquals(true, timer.isStopped());
		} catch (InterruptedException e) {
			fail("Exception occurred");
		}
	}
}
