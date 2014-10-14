package com.android.pennaed.campussafety;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.android.pennaed.R;

/*
 * This Activity represents the screen that opens when user clicks on "About"
 * button for any preloaded emergency contacts
 */
public class Info extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.info_layout);
		
		String title = getIntent().getStringExtra("Title");
		String info = getIntent().getStringExtra("Info");
		
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText(title);
		TextView tvInfo = (TextView) findViewById(R.id.tvInfo);
		tvInfo.setText(info);
		tvInfo.setMovementMethod(new ScrollingMovementMethod());
	}

}
