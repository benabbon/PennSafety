package com.android.pennaed.test.emergency.cprInstructionsActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

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
		Intent intent = new Intent(getInstrumentation().getTargetContext(), CPRInstructionsActivity.class);
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

	@SmallTest
	public void testTextMatch() {
		int cprText1Id = R.id.cpr_insn_text1;
		assertEquals("Place one hand on top of the other in center of chest", ((TextView)cprInstructionsActivity.findViewById(cprText1Id)).getText());

		int cprText2Id = R.id.cpr_insn_text2;
		assertEquals("Push downward hard and fast", ((TextView)cprInstructionsActivity.findViewById(cprText2Id)).getText());

		int cprText3Id = R.id.cpr_insn_text3;
		assertEquals("100 compressions per minute", ((TextView)cprInstructionsActivity.findViewById(cprText3Id)).getText());

		int cprText4Id = R.id.cpr_insn_text4;
		assertEquals("Continue until help arrives", ((TextView)cprInstructionsActivity.findViewById(cprText4Id)).getText());

	}

	@SmallTest
	public void testButtonText() {
		int aedButtonId = R.id.aed_button;
		assertEquals("Click here for AED locations", ((Button)cprInstructionsActivity.findViewById(aedButtonId)).getText());

	}

}
