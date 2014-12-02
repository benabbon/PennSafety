package com.android.pennaed.test.contacts.info;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.contacts.Info;
import com.android.pennaed.R;

/*
created by sruthi
 */

public class InfoTest extends ActivityUnitTestCase<Info> {
	private Info info;

	public InfoTest() {
		super(Info.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), Info.class);
		startActivity(intent, null, null);
		info = getActivity();
	}

	@SmallTest
	public void testNonNullViews() {
		int tvTitle = R.id.tvTitle;
		assertNotNull(info.findViewById(tvTitle));
		int tvInfo = R.id.tvInfo;
		assertNotNull(info.findViewById(tvInfo));
	}

}