package com.android.pennaed.walkTimer;

/**
 * Created by sruthi on 10/28/14.
 */
public class CountDownTimer extends android.os.CountDownTimer {

	public enum CounterState {RUNNING, STOPPED}
	public enum TimerType {DESTINATION, MANUAL}

	private CounterState counterState;
	private TimerType timerType;

	public boolean isStopped() {
		return counterState==CounterState.STOPPED;
	}

	public boolean isRunning() {
		return counterState==CounterState.RUNNING;
	}


}
