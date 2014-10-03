package com.android.pennaed;

import com.parse.Parse;

import android.app.Application;

public class PennAEDApp extends Application {
	
	public String aedLocations;
	AppVars _appVars;

	@Override
	public void onCreate(){
		super.onCreate();
		
	    Parse.initialize(this, "w6XxsgYz0uaJ9LK3CEZrTum4VgxU3Il9nOmrQKAl", "xP6JGXRijjHm7GXYYA1W4uda8KZD2KfXLVpvkVvk");  
		//BugSenseHandler.initAndStartSession(this, "");
	    setPreferences();
	    initSingletons();
	    
	    cacheAEDLocations();
	  }
	
	private void setPreferences(){
		
	}
	
	private void initSingletons(){
		_appVars = AppVars.getInstance();		
	}
	
	private void cacheAEDLocations(){
		
	}
	
}


