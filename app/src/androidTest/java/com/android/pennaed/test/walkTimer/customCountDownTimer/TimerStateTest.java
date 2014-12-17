package com.android.pennaed.test.walkTimer.customCountDownTimer;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

import com.android.pennaed.walkTimer.CustomCountDownTimer;
import com.android.pennaed.walkTimer.WalkTimerMapActivity;

import junit.framework.TestCase;
import com.android.pennaed.R;

/**
 * Created by sruthi on 12/2/14.
 */
public class TimerStateTest extends TestCase{
	private CustomCountDownTimer timer;
	private WalkTimerMapActivity walkTimerMapActivity;

	public void setUp() throws Exception {
		super.setUp();
		walkTimerMapActivity = new WalkTimerMapActivity() {
			@Override
			public void changeTimerButtonText(String text) {
				return;
			}
		};
		timer = new CustomCountDownTimer(walkTimerMapActivity);
	}

	@SmallTest
	public void testIsRunningValid() {
		timer.setCounterState(CustomCountDownTimer.CounterState.RUNNING);
		assertEquals(true, timer.isRunning());
	}

	@SmallTest
	public void testIsRunningInvalid() {
		timer.setCounterState(CustomCountDownTimer.CounterState.STOPPED);
		assertEquals(false, timer.isRunning());
	}

	@SmallTest
	public void testIsStoppedValid() {
		timer.setCounterState(CustomCountDownTimer.CounterState.STOPPED);
		assertEquals(true, timer.isStopped());
	}

	@SmallTest
	public void testIsStoppedInvalid() {
		timer.setCounterState(CustomCountDownTimer.CounterState.RUNNING);
		assertEquals(false, timer.isStopped());
	}
}
