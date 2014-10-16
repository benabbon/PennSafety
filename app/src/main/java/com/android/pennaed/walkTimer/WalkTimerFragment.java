package com.android.pennaed.walkTimer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
		Button walkTimerStartButton = (Button) view.findViewById(R.id.walk_timer_main_button);
		walkTimerStartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), WalkTimerMapActivity.class);
				startActivity(i);
			}
		});
		return view;
	}

}