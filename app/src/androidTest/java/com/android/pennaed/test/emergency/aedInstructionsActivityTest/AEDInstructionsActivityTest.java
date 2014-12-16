package com.android.pennaed.test.emergency.aedInstructionsActivityTest;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.TextView;

import com.android.pennaed.R;
import com.android.pennaed.contacts.Info;
import com.android.pennaed.emergency.AEDInstructionsActivity;
import com.android.pennaed.emergency.AEDMapActivity;

/**
 * Created by chaitalisg on 12/16/14.
 */
public class AEDInstructionsActivityTest extends ActivityUnitTestCase<AEDInstructionsActivity> {

	AEDInstructionsActivity aedInstructionsActivity;
	AEDMapActivity aedMapActivity;

	public AEDInstructionsActivityTest(){
		super(AEDInstructionsActivity.class);
		aedMapActivity = new AEDMapActivity();
	}

	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), AEDInstructionsActivityTest.class);
		startActivity(intent, null, null);
		aedInstructionsActivity = getActivity();
	}

	@SmallTest
	public void testNonNullViews() {
		int aedTitleId = R.id.aed_insn_title;
		assertNotNull(aedInstructionsActivity.findViewById(aedTitleId));

		int aedText1 = R.id.aed_insn_text1;
		assertNotNull(aedInstructionsActivity.findViewById(aedText1));

		int aedText2 = R.id.aed_insn_text2;
		assertNotNull(aedInstructionsActivity.findViewById(aedText2));

		int aedText3 = R.id.aed_insn_text3;
		assertNotNull(aedInstructionsActivity.findViewById(aedText3));

		int aedText4 = R.id.aed_insn_text4;
		assertNotNull(aedInstructionsActivity.findViewById(aedText4));

	}

	@SmallTest
	public void testTextMatch() {
		int aedText1 = R.id.aed_insn_text1;
		assertEquals("Take AED to patient", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText2 = R.id.aed_insn_text2;
		assertEquals("Open lid of AED", ((TextView)aedInstructionsActivity.findViewById(aedText2)).getText());

		int aedText3 = R.id.aed_insn_text3;
		assertEquals("AED will voice prompt use instructions", ((TextView)aedInstructionsActivity.findViewById(aedText3)).getText());

		int aedText4 = R.id.aed_insn_text4;
		assertEquals("Follow instructions closely", ((TextView)aedInstructionsActivity.findViewById(aedText4)).getText());

	}

}

