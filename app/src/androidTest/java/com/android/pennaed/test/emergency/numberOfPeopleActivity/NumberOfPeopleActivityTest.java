package com.android.pennaed.test.emergency.numberOfPeopleActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

import com.android.pennaed.R;
import com.android.pennaed.contacts.Info;
import com.android.pennaed.emergency.NumberOfPeopleActivity;

/**
 * Created by sruthi on 12/16/14.
 */
public class NumberOfPeopleActivityTest extends ActivityUnitTestCase<NumberOfPeopleActivity> {

	private NumberOfPeopleActivity numberOfPeopleActivity;
	public NumberOfPeopleActivityTest() {
		super(NumberOfPeopleActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), NumberOfPeopleActivity.class);
		startActivity(intent, null, null);
		numberOfPeopleActivity = getActivity();
	}

	@SmallTest
	public void testNonNullViews() {
		int emergencyStartButtonIdText = R.id.emergency_start_button_text;
		assertNotNull(numberOfPeopleActivity.findViewById(emergencyStartButtonIdText));
		int onePersonButtonId = R.id.one_person_button;
		assertNotNull(numberOfPeopleActivity.findViewById(onePersonButtonId));
		int moreThanOnePersonButtonId = R.id.more_than_one_person_button;
		assertNotNull(numberOfPeopleActivity.findViewById(moreThanOnePersonButtonId));
	}

	@SmallTest
	public void testTextMatch() {
		int emergencyStartButtonIdText = R.id.emergency_start_button_text;
		assertEquals("How many people are at the scene?", ((TextView)numberOfPeopleActivity.findViewById(emergencyStartButtonIdText)).getText());
	}

	@SmallTest
	public void testButtonTextMatch() {
		int onePersonButtonId = R.id.one_person_button;
		assertEquals("1", ((Button)numberOfPeopleActivity.findViewById(onePersonButtonId)).getText());
		int moreThanOnePersonButtonId = R.id.more_than_one_person_button;
		assertEquals("2 or more", ((Button) numberOfPeopleActivity.findViewById(moreThanOnePersonButtonId)).getText());
	}

}
