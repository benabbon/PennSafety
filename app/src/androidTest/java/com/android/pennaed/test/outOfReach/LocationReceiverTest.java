package com.android.pennaed.test.outOfReach;

import android.content.Intent;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.outOfReach.LocationReceiver;
import com.google.android.gms.location.LocationClient;

/**
 * Created by chaitalisg on 12/16/14.
 */
public class LocationReceiverTest extends AndroidTestCase {

	private LocationReceiver locationReceiver;
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		locationReceiver = new LocationReceiver();
	}

	@SmallTest
	public void testLocationReceiver()
	{
		Intent intent = new Intent(LocationClient.KEY_LOCATION_CHANGED);
		locationReceiver.onReceive(getContext(), intent);
		assertNotNull("point in polygon null", locationReceiver.getPointInPolygon());
	}

	@SmallTest
	public void testShowNotifications(){
		LocationReceiver.enableNotifications();
		assertTrue(locationReceiver.getShowNotifications());
		LocationReceiver.disableNotifications();
		assertFalse(locationReceiver.getShowNotifications());
	}

}
