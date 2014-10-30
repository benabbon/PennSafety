package com.android.pennaed.walkTimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.pennaed.R;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

/**
 * walk timer map activity
 * Created by nabilbenabbou1 on 10/15/14.
 */

public class WalkTimerMapActivity extends Activity {

	private CountDownTimer countDownTimer;

	WalkTimerMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.walk_timer_activity_map);
		map = new WalkTimerMap();
		map.setMap(this);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if ("DESTINATION_BASED".equals(extras.getString("TIMER_TYPE"))) {
				timerType = TimerType.DESTINATION;
				map.getTimerFromDestinationClick(this);
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle(R.string.destination_timer_instructions_title);
				alert.setMessage(R.string.destination_timer_instruction);
				alert.setCancelable(true);
				alert.show();
			} else {
				timerType = TimerType.MANUAL;
				int value = Integer.parseInt(extras.getString("TIMER_VALUE"));
				setTimer(value * 1000);
			}
		} else {
			setTimer(20000);
		}
	}

	protected void setTimer(long countdownInMs) {
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
				long secs = millisUntilFinished / 1000;
				changeTimerButtonText(secs / 60 + "m " + (secs % 60) + "s");
			}

		}.start();
	}

	public boolean isTimerOn() {
		return counterState == CounterState.RUNNING;
	}

	public void onClickStopTimer(View v) {
		countDownTimer.cancel();
		changeTimerButtonText("Timer stopped");
		counterState = CounterState.STOPPED;
	}

	public void changeTimerButtonText(String text) {
		if (counterState != CounterState.STOPPED) {
			Button walkTimerStartButton = (Button) findViewById(R.id.stop_timer_button);
			walkTimerStartButton.setText(text);
		}
	}

}