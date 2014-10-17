package com.android.pennaed.walkTimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.pennaed.R;

/**
 * walk timer map activity
 * Created by nabilbenabbou1 on 10/15/14.
 */

public class WalkTimerMapActivity extends Activity {

	private CountDownTimer countDownTimer;
	public enum CounterState {RUNNING,STOPPED}
	CounterState counterState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.walk_timer_activity_map);
		WalkTimerMap map = new WalkTimerMap();
		map.setMap(this);
		setTimer(20000);
	}

	private void setTimer(long countdownInMs) {
		counterState = CounterState.RUNNING;
		countDownTimer = new CountDownTimer(countdownInMs, 1000) {

			@Override
			public void onFinish() {
				Toast.makeText(getApplicationContext(), "End of timer", Toast.LENGTH_SHORT).show();
				changeTimerButtonText("Timer ended");
				counterState = CounterState.STOPPED;
			}

			@Override
			public void onTick(long millisUntilFinished) {
				changeTimerButtonText("" + Math.round(millisUntilFinished / 1000));
			}

		}.start();
	}

	public void onClickStopTimer(View v) {
		countDownTimer.cancel();
		changeTimerButtonText("Timer stopped");
		counterState = CounterState.STOPPED;
	}

	public void changeTimerButtonText(String text) {
		if(counterState != CounterState.STOPPED) {
			Button walkTimerStartButton = (Button) findViewById(R.id.stop_timer_button);
			walkTimerStartButton.setText(text);
		}
	}

}