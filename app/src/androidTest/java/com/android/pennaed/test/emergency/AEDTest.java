package com.android.pennaed.test.emergency;

import android.test.AndroidTestCase;

import android.test.suitebuilder.annotation.SmallTest;

import com.android.pennaed.emergency.AED;
import com.parse.ParseFile;

import junit.framework.TestCase;

import java.util.Date;
import java.util.Random;

import static junit.framework.Assert.assertEquals;

/**
 * Created by chaitalisg on 12/16/14.
 */
public class AEDTest extends TestCase{
	public AEDTest() {
		super();
	}

	AED aed;

	public void setUp(){
		byte[] b = new byte[20];
		new Random().nextBytes(b);
		aed = new AED("1", "test", 39.954844, -75.183274, "test address", "lobby", "testname", 215483, new Date(System.currentTimeMillis()), new ParseFile(b), 1);
		//(String id, String name, double latitude, double longitude, String address, String inBuildingDirection,
//				String contactName, int contactPhone, Date updatedAt, ParseFile photo, int integerId));
	}

	@SmallTest
	public void testAED(){

		assertEquals(aed.getIntegerId(), 1);
		assertEquals(aed.getLatitude(), 39.954844);
		assertEquals(aed.getLongitude(), -75.183274);
		assertEquals(aed.getInBuildingLocation(), "lobby");
		assertEquals(aed.getName(), "test");

	}
}
