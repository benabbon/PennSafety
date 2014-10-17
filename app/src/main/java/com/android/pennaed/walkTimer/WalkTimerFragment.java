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
		walkTimerStartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle(R.string.manual_timer_title);
				alert.setMessage(R.string.manual_timer_message);

				final EditText timeInputField = new EditText(getActivity());
				alert.setView(timeInputField);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						int time = Integer.parseInt(timeInputField.getText().toString());
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

}