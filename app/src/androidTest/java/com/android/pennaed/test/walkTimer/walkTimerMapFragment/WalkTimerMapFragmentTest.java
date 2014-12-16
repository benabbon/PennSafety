package com.android.pennaed.test.walkTimer.walkTimerMapFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.android.pennaed.MainNavigation;
import com.android.pennaed.contacts.Info;
import com.android.pennaed.walkTimer.WalkTimerFragment;

/**
 * Created by sruthi on 12/16/14.
 */
public class WalkTimerMapFragmentTest extends ActivityUnitTestCase<MainNavigation> {

	private Activity parentActivity;
	private FragmentManager fragmentManager;
	private WalkTimerFragment walkTimerFragment;

	public WalkTimerMapFragmentTest() {
		super(MainNavigation.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), Info.class);
		startActivity(new Intent(Intent.ACTION_MAIN), null, null);
		fragmentManager = getActivity().getFragmentManager();
		walkTimerFragment = new WalkTimerFragment();
	}

	public void testFragment() throws Exception {
		fragmentManager.beginTransaction()
				.replace(android.R.id.content, new WalkTimerFragment())
				.commit();
		fragmentManager.executePendingTransactions();

		walkTimerFragment = (WalkTimerFragment) fragmentManager.findFragmentById(android.R.id.content);

		assertNotNull(walkTimerFragment);

		getInstrumentation().callActivityOnStart(getActivity());
		getInstrumentation().callActivityOnResume(getActivity());
		fragmentManager.executePendingTransactions();

		/*
		assertTrue(walkTimerFragment.created);
		assertTrue(walkTimerFragment.started);
		assertTrue(walkTimerFragment.viewCreated);
		*/
	}
}
