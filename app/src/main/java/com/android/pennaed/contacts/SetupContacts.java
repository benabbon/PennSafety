package com.android.pennaed.contacts;

import android.app.Activity;

import com.android.pennaed.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaitalisg on 10/15/14.
 */
public class SetupContacts {

	List<Contact> contactList;
	ContactInformation ci;

	public SetupContacts(List<Contact> contactList){
		this.contactList = contactList;
	}

	/*
	 * Makes a query to load contacts from database into the listview
	 */
	public void updateData(final ContactAdapter adapter) {
		ParseQuery<ContactDB> query = ParseQuery.getQuery(ContactDB.class);
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		query.findInBackground(new FindCallback<ContactDB>() {

			@Override
			public void done(List<ContactDB> contacts, ParseException error) {
				if (contacts != null) {
					adapter.addAll(contacts);
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	/*
 * Create the preloaded contact objects and add them to list view
 */
	protected void initContacts() {

		// Set the valid location range for emergency organizations to:
		// 43rd to 30th, market to baltimore
		double[] lat_temp = {39.954844, 39.958002, 39.952491, 39.951472,
				39.949449, 39.949523, 39.949869, 39.949260, 39.949663,
				39.951571};
		double[] lng_temp = {-75.183274, -75.208213, -75.209388, -75.209398,
				-75.209291, -75.207167, -75.201309, -75.184550, -75.184057,
				-75.182544};

		// DPS
		Contact DPS = new ContactStatic("DPS", Long.parseLong("2155733333"),
				"215-573-3333\nAvailable 24/7");

		DPS.setImageID(R.drawable.penn_dps);
		DPS.setInfo(StringConstants.DPSInfo);
		contactList.add(DPS);

		// MERT
		Contact MERT = new ContactStatic("Medical Emergency (MERT)",
				Long.parseLong("2155733333"),
				"215-573-3333\nMon - Sun: 5pm - 7am", getScheduleMERT());

		MERT.setImageID(R.drawable.penn_mert);
		MERT.setInfo(StringConstants.MERTInfo);
		MERT.setRange(lat_temp, lng_temp);
		contactList.add(MERT);

		// SHS
		Contact SHS = new ContactStatic("Walk-in Medical Emergencies: SHS",
				Long.parseLong("2157463535"), "215-746-3535\n"
				+ "Mon - Wed: 8am - 7:30pm; "
				+ "Thurs: 10am - 5:30pm; " + "Fri: 8am - 5:30pm; "
				+ "Sat: 11am - 4:30pm", getScheduleSHS());

		SHS.setImageID(R.drawable.shsbutton);
		SHS.setInfo(StringConstants.SHSInfo);
		contactList.add(SHS);

		// CAPS
		// 215-349-5490 between 5p.m. and 9 a.m. for emergencies after hour
		Contact CAPS = new ContactStatic("Emergency Counseling: CAPS",
				Long.parseLong("2158987021"), "215-898-7021\n"
				+ "Mon, Tues, Fri: 9am - 5pm; "
				+ "Wed-Thurs: 9am - 7pm", getScheduleCAPS());

		CAPS.setImageID(R.drawable.penn_logo);
		CAPS.setInfo(StringConstants.CAPSInfo);
		contactList.add(CAPS);

		// Suicide Prevention
		Contact SPH = new ContactStatic("Suicide Prevention Hotline",
				Long.parseLong("2156864420"), "215-686-4420\nAvailable 24/7");

		SPH.setImageID(R.drawable.penn_logo);
		contactList.add(SPH);

		// Penn Walk
		Contact PennWalk = new ContactStatic("Penn Walk",
				Long.parseLong("2158987297"), "215-898-WALK\nAvailable 24/7");

		PennWalk.setImageID(R.drawable.penn_escorts);
		PennWalk.setInfo(StringConstants.PennWalkInfo);
		PennWalk.setRange(lat_temp, lng_temp);
		contactList.add(PennWalk);

		// Penn Transit
		Contact PennTransit = new ContactStatic(
				"Penn Transit",
				Long.parseLong("2158987433"),
				"215-898-RIDE\n"
						+ "Mon - Wed: 6pm - 3am; Limited on-call service, 3am - 7am",
				getSchedulePennTransit());

		PennTransit.setImageID(R.drawable.penn_logo);
		PennTransit.setInfo(StringConstants.PennTransitInfo);
		PennTransit.setRange(lat_temp, lng_temp);
		contactList.add(PennTransit);

	}

	/*
	 * Helper that returns MERT's hours
	 */
	private Map<ContactStatic.Day, ContactStatic.Hours> getScheduleMERT() {

		// Map open times to closing times
		ContactStatic.Hours hours = new ContactStatic.Hours(17, 7);

		// Map days of the week to hours
		Map<ContactStatic.Day, ContactStatic.Hours> schedule = new HashMap<ContactStatic.Day, ContactStatic.Hours>();
		schedule.put(ContactStatic.Day.MONDAY, hours);
		schedule.put(ContactStatic.Day.TUESDAY, hours);
		schedule.put(ContactStatic.Day.WEDNESDAY, hours);
		schedule.put(ContactStatic.Day.THURSDAY, hours);
		schedule.put(ContactStatic.Day.FRIDAY, hours);
		schedule.put(ContactStatic.Day.SATURDAY, hours);
		schedule.put(ContactStatic.Day.SUNDAY, hours);

		return schedule;
	}

	/*
	 * Helper that returns SHS's hours
	 */
	private Map<ContactStatic.Day, ContactStatic.Hours> getScheduleSHS() {

		// Map open times to closing times
		ContactStatic.Hours monWedHours = new ContactStatic.Hours(8, 19.5);
		ContactStatic.Hours thursHours = new ContactStatic.Hours(10, 17.5);
		ContactStatic.Hours friHours = new ContactStatic.Hours(8, 17.5);
		ContactStatic.Hours satHours = new ContactStatic.Hours(11, 16.5);
		ContactStatic.Hours sunHours = new ContactStatic.Hours(-1, -1);

		// Map days of the week to hours
		Map<ContactStatic.Day, ContactStatic.Hours> schedule = new HashMap<ContactStatic.Day, ContactStatic.Hours>();
		schedule.put(ContactStatic.Day.MONDAY, monWedHours);
		schedule.put(ContactStatic.Day.TUESDAY, monWedHours);
		schedule.put(ContactStatic.Day.WEDNESDAY, monWedHours);
		schedule.put(ContactStatic.Day.THURSDAY, thursHours);
		schedule.put(ContactStatic.Day.FRIDAY, friHours);
		schedule.put(ContactStatic.Day.SATURDAY, satHours);
		schedule.put(ContactStatic.Day.SUNDAY, sunHours);
		return schedule;
	}

	/*
	 * Helper that returns CAPS's hours
	 */
	private Map<ContactStatic.Day, ContactStatic.Hours> getScheduleCAPS() {

		// Map open times to closing times
		ContactStatic.Hours MTFHours = new ContactStatic.Hours(9, 17);
		ContactStatic.Hours WThHours = new ContactStatic.Hours(10, 19);
		ContactStatic.Hours weekendHours = new ContactStatic.Hours(-1, -1);

		// Map days of the week to hours
		Map<ContactStatic.Day, ContactStatic.Hours> schedule = new HashMap<ContactStatic.Day, ContactStatic.Hours>();
		schedule.put(ContactStatic.Day.MONDAY, MTFHours);
		schedule.put(ContactStatic.Day.TUESDAY, MTFHours);
		schedule.put(ContactStatic.Day.WEDNESDAY, WThHours);
		schedule.put(ContactStatic.Day.THURSDAY, WThHours);
		schedule.put(ContactStatic.Day.FRIDAY, MTFHours);
		schedule.put(ContactStatic.Day.SATURDAY, weekendHours);
		schedule.put(ContactStatic.Day.SUNDAY, weekendHours);

		return schedule;
	}

	/*
	 * Helper that returns PennTransit's schedule
	 */
	private Map<ContactStatic.Day, ContactStatic.Hours> getSchedulePennTransit() {
		// Map open times to closing times
		ContactStatic.Hours MTWHours = new ContactStatic.Hours(18, 7);
		ContactStatic.Hours hours = new ContactStatic.Hours(3, 7);

		// Map days of the week to hours
		Map<ContactStatic.Day, ContactStatic.Hours> schedule = new HashMap<ContactStatic.Day, ContactStatic.Hours>();
		schedule.put(ContactStatic.Day.MONDAY, MTWHours);
		schedule.put(ContactStatic.Day.TUESDAY, MTWHours);
		schedule.put(ContactStatic.Day.WEDNESDAY, MTWHours);
		schedule.put(ContactStatic.Day.THURSDAY, hours);
		schedule.put(ContactStatic.Day.FRIDAY, hours);
		schedule.put(ContactStatic.Day.SATURDAY, hours);
		schedule.put(ContactStatic.Day.SUNDAY, hours);

		return schedule;
	}

}
