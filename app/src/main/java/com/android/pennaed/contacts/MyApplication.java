package com.android.pennaed.contacts;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/*
 * We use the application class to configure Parse once so that it is
 * available across all activities
 */
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "cPnzg4beRc88vyyakrDrEWokv6cNnyiF0jEq35N2",
				"l71EVwL99xwrbeupB3IkhWA4hELdSfjHUYnkqsIG");
		ParseObject.registerSubclass(ContactDB.class);
	}
}
