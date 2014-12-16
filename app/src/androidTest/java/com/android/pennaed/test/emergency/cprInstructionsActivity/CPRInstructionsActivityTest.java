package com.android.pennaed.test.emergency.cprInstructionsActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.R;
import com.android.pennaed.contacts.Info;
import com.android.pennaed.emergency.CPRInstructionsActivity;

/**
 * Created by sruthi on 12/16/14.
 */
public class CPRInstructionsActivityTest extends ActivityUnitTestCase<CPRInstructionsActivity> {

	private CPRInstructionsActivity cprInstructionsActivity;

	public CPRInstructionsActivityTest() { super(CPRInstructionsActivity.class); }


	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), Info.class);
		startActivity(intent, null, null);
		cprInstructionsActivity = getActivity();
	}

	@SmallTest
	public void testNonNullViews() {
		int cprTitleId = R.id.cpr_insn_title;
		assertNotNull(cprInstructionsActivity.findViewById(cprTitleId));

		int cprText1Id = R.id.cpr_insn_text1;
		assertNotNull(cprInstructionsActivity.findViewById(cprText1Id));

		int cprText2Id = R.id.cpr_insn_text2;
		assertNotNull(cprInstructionsActivity.findViewById(cprText2Id));

		int cprText3Id = R.id.cpr_insn_text3;
		assertNotNull(cprInstructionsActivity.findViewById(cprText3Id));

		int cprText4Id = R.id.cpr_insn_text4;
		assertNotNull(cprInstructionsActivity.findViewById(cprText4Id));

		int cprImgId = R.id.cpr_insn_image;
		assertNotNull(cprInstructionsActivity.findViewById(cprImgId));

		int aedButtonId = R.id.aed_button;
		assertNotNull(cprInstructionsActivity.findViewById(aedButtonId));

	}


}
