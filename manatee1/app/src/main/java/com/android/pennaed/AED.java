package com.android.pennaed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.plus.model.people.Person.Image;
import com.parse.ParseFile;
import com.parse.ParseObject;

public class AED implements Comparable {
	
	private final String id;
	private final int integerId;
	private final String name;

	private final double latitude;
	private final double longitude;
	private final String address;
	private final String inBuildingDirections;

	private final String contactName;
	private final int contactPhone;
	
	// true if normal, false if needs to be checked
	private Date updatedAt;
	private ParseFile photo;

	public AED(String id, String name, double latitude, double longitude, String address, String inBuildingDirection,
			String contactName, int contactPhone, Date updatedAt, ParseFile photo, int integerId) {
		this.id = id;
		this.name = name;
		
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.inBuildingDirections = inBuildingDirection;
		
		this.contactName = contactName;
		this.contactPhone = contactPhone;
				
		this.updatedAt = updatedAt;
		this.photo = photo;
		this.integerId = integerId;
	}
	
	public String getId(){
		return this.id;
	}
	
	public int getIntegerId() {
		return this.integerId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getLatitude(){
		return this.latitude;
	}
	
	public double getLongitude(){
		return this.longitude;
	}
	
	public String getInBuildingLocation(){
		return this.inBuildingDirections;
	}
	
	public String toString(){
		return "TO DO";
	}
	
	public boolean getStatus() {
		Long updatedTime = updatedAt.getTime();
		Long currentTime = System.currentTimeMillis();
		if((currentTime / 1000) - (updatedTime / 1000) > (long)2678400) {
			return false;
		}
		return true;
	}
	
	public String getUpdatedDate() {
		return this.updatedAt.toString();
	}
	
	public ParseFile getPhoto() {
		return photo;
	}

	@Override
	public int compareTo(Object another) {
		AED aed = (AED) another;
		return this.name.compareTo(aed.getName());
	}
}
