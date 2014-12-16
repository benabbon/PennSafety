package com.android.pennaed.test.emergency.aedInstructionsActivityTest;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

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

	}

	/*
	    <TextView
        android:id="@+id/aed_insn_title"


    <TextView
        android:id="@+id/aed_insn_text1"


    <TextView
        android:id="@+id/aed_insn_text2"


    <TextView
        android:id="@+id/aed_insn_text3"


    <TextView
        android:id="@+id/aed_insn_text4"


    <TextView
        android:id="@+id/aed_insn_text5"


    <TextView
        android:id="@+id/aed_insn_text6"


    <TextView
        android:id="@+id/aed_insn_text7"


    <TextView
        android:id="@+id/aed_insn_text8"


    <TextView
        android:id="@+id/aed_insn_text9"


    <TextView
        android:id="@+id/aed_insn_text10"


    <TextView
        android:id="@+id/aed_insn_text11"
	 */
}

