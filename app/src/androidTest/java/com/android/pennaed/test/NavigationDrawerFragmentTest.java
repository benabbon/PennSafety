package com.android.pennaed.test;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.android.pennaed.MainNavigation;
import com.android.pennaed.NavigationDrawerFragment;
import com.android.pennaed.contacts.Info;

/**
 * Created by sruthi on 12/16/14.
 */
public class NavigationDrawerFragmentTest extends ActivityUnitTestCase<MainNavigation> {
	private Activity parentActivity;
	private NavigationDrawerFragment navigationDrawerFragment;
	private FragmentManager fragmentManager;

	public NavigationDrawerFragmentTest() {
		super(MainNavigation.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), Info.class);
		startActivity(new Intent(Intent.ACTION_MAIN), null, null);
		fragmentManager = getActivity().getFragmentManager();
	}

	public void testFragment() throws Exception {
		fragmentManager.beginTransaction()
				.replace(android.R.id.content, new NavigationDrawerFragment())
				.commit();
		fragmentManager.executePendingTransactions();

		navigationDrawerFragment = (NavigationDrawerFragment) fragmentManager.findFragmentById(android.R.id.content);

		assertNotNull(navigationDrawerFragment);

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
