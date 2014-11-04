package com.android.pennaed.walkTimer;

import android.app.Activity;
import android.widget.Toast;
import android.os.CountDownTimer;

/**
 * Created by sruthi on 10/28/14.
 */
public class CustomCountDownTimer{

	public enum CounterState {RUNNING, STOPPED}
	public enum TimerType {DESTINATION, MANUAL}

	private android.os.CountDownTimer countDownTimer;
	private CounterState counterState;
	private TimerType timerType;
	private WalkTimerMapActivity parentActivity;
	private long timeInMs;

	public CustomCountDownTimer(WalkTimerMapActivity parentActivity) {
		this.parentActivity = parentActivity;
		counterState = CounterState.STOPPED;
	}

	public void onTimerFinish() {
		Toast.makeText(parentActivity.getApplicationContext(), "End of timer", Toast.LENGTH_SHORT).show();
		parentActivity.changeTimerButtonText("Timer ended");
		counterState = CounterState.STOPPED;
		countDownTimer.cancel();
	}

	public void onTimerTick(long millisUntilFinished) {
		long secs = millisUntilFinished / 1000;
		parentActivity.changeTimerButtonText(secs / 60 + "m " + (secs % 60) + "s");
	}

	public void startTimer(long timeInMs) {
		this.timeInMs = timeInMs;
		counterState = CounterState.RUNNING;
		countDownTimer = new CountDownTimer(timeInMs, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				onTimerTick(millisUntilFinished);
			}

			@Override
			public void onFinish() {
				onTimerFinish();
			}
		}.start();
	}

	public void stopTimer() {
		if(counterState == CounterState.STOPPED) {
			return;
		}
		counterState = CounterState.STOPPED;
		countDownTimer.cancel();
	}

	public boolean isStopped() {
		return counterState==CounterState.STOPPED;
	}

	public boolean isRunning() {
		return counterState==CounterState.RUNNING;
	}

	public void setType(TimerType timerType) {
		this.timerType = timerType;
	}

}
