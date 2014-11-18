package com.android.pennaed.walkTimer;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.pennaed.R;
import com.android.pennaed.emergency.AppVars;

/**
 * Created by nabilbenabbou1 on 10/15/14.
 */

public class WalkTimerFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_walk_timer_main, container,
				false);
		Button walkTimerStartButton = (Button) view.findViewById(R.id.manual_walk_timer_button);
		Button walkTimerByDestinationStartButton = (Button) view.findViewById(R.id.dest_walk_timer_button);
		walkTimerByDestinationStartButton.setOnClickListener(
				DestinationTimer.getStartTimerByDesination(getActivity()));
		walkTimerStartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle(R.string.manual_timer_title);
				alert.setMessage(R.string.manual_timer_message);

				LayoutInflater layoutInflater = getActivity().getLayoutInflater();
				final View dialogView = layoutInflater.inflate(R.layout.walk_timer_dialog, null);
				alert.setView(dialogView);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						EditText minInput = (EditText) dialogView.findViewById(R.id.min_textbox);
						EditText secInput = (EditText) dialogView.findViewById(R.id.sec_textbox);
						int time = getTimeInSeconds(minInput.getText().toString(), secInput.getText().toString());
						Intent intent = new Intent(getActivity(), WalkTimerMapActivity.class);
						intent.putExtra("TIMER_VALUE", "" + time);
						startActivity(intent);

					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
			}
		});
		return view;
	}

	public int getTimeInSeconds(String minutes, String seconds) {
		int min = 0;
		int sec = 0;
		if (minutes != null && !minutes.isEmpty())
			min = Integer.parseInt(minutes);
		if (seconds != null && !seconds.isEmpty())
			sec = Integer.parseInt(seconds);
		sec += min * 60;

		if (sec <= 0)
			sec = 60; //default of 60

		return sec;
	}

	@Override
	public void onResume() {
		super.onResume();
		AppVars.getInstance().enableLocation(getActivity());
	}

}