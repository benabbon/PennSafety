package com.android.pennaed.emergency;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.pennaed.R;

/**
 * Created by nabilbenabbou1 on 10/14/14.
 */

public class EmergencyFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_navigation, container,
				false);
		Button startEmergencyButton = (Button) view.findViewById(R.id.emergency_start_button);
		startEmergencyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NumberOfPeopleActivity.class);
				startActivity(i);
			}
		});
		return view;
	}

}