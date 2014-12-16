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
		Intent intent = new Intent(getInstrumentation().getTargetContext(), Info.class);
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

		int aedText5 = R.id.aed_insn_text5;
		assertNotNull(aedInstructionsActivity.findViewById(aedText5));

		int aedText6 = R.id.aed_insn_text6;
		assertNotNull(aedInstructionsActivity.findViewById(aedText6));

		int aedText7 = R.id.aed_insn_text7;
		assertNotNull(aedInstructionsActivity.findViewById(aedText7));

		int aedText8 = R.id.aed_insn_text8;
		assertNotNull(aedInstructionsActivity.findViewById(aedText8));

		int aedText9 = R.id.aed_insn_text9;
		assertNotNull(aedInstructionsActivity.findViewById(aedText9));

		int aedText10 = R.id.aed_insn_text10;
		assertNotNull(aedInstructionsActivity.findViewById(aedText10));

		int aedText11 = R.id.aed_insn_text11;
		assertNotNull(aedInstructionsActivity.findViewById(aedText11));

	}

	@SmallTest
	public void testTextMatch() {
		int aedText1 = R.id.aed_insn_text1;
		assertEquals("Take AED to patient", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText2 = R.id.aed_insn_text2;
		assertEquals("Open lid of AED", ((TextView)aedInstructionsActivity.findViewById(aedText2)).getText());

		int aedText3 = R.id.aed_insn_text1;
		assertEquals("Take AED to patient", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText4 = R.id.aed_insn_text1;
		assertEquals("Take AED to patient", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText4 = R.id.aed_insn_text1;
		assertEquals("Take AED to patient", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText4 = R.id.aed_insn_text1;
		assertEquals("Take AED to patient", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText4 = R.id.aed_insn_text1;
		assertEquals("Take AED to patient", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText4 = R.id.aed_insn_text1;
		assertEquals("Take AED to patient", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText4 = R.id.aed_insn_text1;
		assertEquals("Take AED to patient", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText4 = R.id.aed_insn_text1;
		assertEquals("Stand clear of patient if shock is advised", ((TextView)aedInstructionsActivity.findViewById(aedText1)).getText());

		int aedText11 = R.id.aed_insn_text11;
		assertEquals("After shock AED will prompt users to continue CPR", ((TextView)aedInstructionsActivity.findViewById(aedText11)).getText());

	}

}

