package com.android.pennaed.walkTimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.pennaed.R;

/**
 * walk timer map activity
 * Created by nabilbenabbou1 on 10/15/14.
 */

public class WalkTimerMapActivity extends Activity {

	private static final String TAG = "WalkTimerMapActivity";
	WalkTimerMap map;
	private CustomCountDownTimer countDownTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.walk_timer_activity_map);
		map = new WalkTimerMap();
		if (!map.setMap(this)) {
			// In case the location provider is not available, we don't allow
			// the user to view the map
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle(R.string.walk_timer_map_unavailable);
			alert.setMessage(R.string.walk_timer_map_unavailable_details);
			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							onBackPressed();
						}
					});
			alert.setCancelable(false);
			alert.show();
		} else {
			Bundle extras = getIntent().getExtras();

			//create Timer
			countDownTimer = new CustomCountDownTimer(this);

			if (extras != null) {
				if ("DESTINATION_BASED".equals(extras.getString("TIMER_TYPE"))) {
					countDownTimer.setType(CustomCountDownTimer.TimerType.DESTINATION);
					map.getTimerFromDestinationClick(this);
					AlertDialog.Builder alert = new AlertDialog.Builder(this);
					alert.setTitle(R.string.destination_timer_instructions_title);
					alert.setMessage(R.string.destination_timer_instruction);
					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();
								}
							});
					alert.setCancelable(true);
					alert.show();
				} else {
					countDownTimer.setType(CustomCountDownTimer.TimerType.MANUAL);
					int value = Integer.parseInt(extras.getString("TIMER_VALUE"));
					setTimer(value * 1000);
				}
			} else {
				setTimer(20000);
			}
		}
	}

	protected void setTimer(long countdownInMs) {
		if (countDownTimer == null) {
			countDownTimer = new CustomCountDownTimer(this);
		} else {
			countDownTimer.stopTimer();
		}
		countDownTimer.startTimer(countdownInMs);
	}

	public void onClickStopTimer(View v) {
		changeTimerButtonText("Timer stopped");
		countDownTimer.stopTimer();
	}

	public void changeTimerButtonText(String text) {
		if (countDownTimer.isRunning()) {
			Button walkTimerStartButton = (Button) findViewById(R.id.stop_timer_button);
			walkTimerStartButton.setText(text);
		}
	}

	public boolean isTimerOn() {
		return countDownTimer.isRunning();
	}

}