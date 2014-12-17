package com.android.pennaed.test.walkTimer.customCountDownTimer;

import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.walkTimer.CustomCountDownTimer;
import com.android.pennaed.walkTimer.WalkTimerMapActivity;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * Created by sruthi on 12/16/14.
 */
public class StartTimerTest extends TestCase {

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
	public void testStartedTimer() {
		timer.startTimer(100000);
		assertEquals(CustomCountDownTimer.CounterState.RUNNING, timer.getCounterState());
	}
}
