package com.android.pennaed.test.emergency.needCPRActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.R;
import com.android.pennaed.emergency.NeedCPRActivity;
import com.android.pennaed.emergency.WaitForHelpActivity;

/**
 * Created by sruthi on 12/16/14.
 */
public class NeedCPRActivityTest extends ActivityUnitTestCase<NeedCPRActivity> {

	NeedCPRActivity needCPRActivity;
	public NeedCPRActivityTest(){
		super(NeedCPRActivity.class);
		needCPRActivity = new NeedCPRActivity();
	}

	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), NeedCPRActivity.class);
		startActivity(intent, null, null);
		needCPRActivity = getActivity();
	}

	@SmallTest
	public void testNonNullViews() {

		int cprQuestionText = R.id.cpr_question_text;
		assertNotNull(needCPRActivity.findViewById(cprQuestionText));
	}

}
