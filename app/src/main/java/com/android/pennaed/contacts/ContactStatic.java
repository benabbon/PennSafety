package com.android.pennaed.contacts;

import com.android.pennaed.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/*
 * ContactStatic instances represent pre-loaded emergency contacts.
 * Data is stored locally for quick access and to ensure that every
 * user has these contacts and cannot remove them.
 */
public class ContactStatic implements Contact {

	private String name;
	private String info;
	private String description;
	private long number;
	private int imageID;
	private double[] lat_array;
	private double[] lng_array;


	// empty map = available 24/7
	private Map<Day, Hours> schedule;

	public ContactStatic(String aName, long aNumber, String aDescription) {
		this.name = aName;
		this.number = aNumber;
		this.description = aDescription;
		this.info = "No Info Yet";
		this.imageID = R.drawable.android;
		setScheduleAlwaysAvailable(); // set availability to 24/7
		this.lat_array = null;
		this.lng_array = null;

	}

	public ContactStatic(String aName, long aNumber, String aDescription,
	                     Map<Day, Hours> aMap) {
		this.name = aName;
		this.number = aNumber;
		this.description = aDescription;
		this.info = "No Info Yet";
		this.imageID = R.drawable.android;
		this.schedule = aMap;
	}

	public void setScheduleAlwaysAvailable() {
		schedule = new HashMap<Day, Hours>();
		Hours hours = new Hours(0, 24);
		schedule.put(Day.SUNDAY, hours);
		schedule.put(Day.MONDAY, hours);
		schedule.put(Day.TUESDAY, hours);
		schedule.put(Day.WEDNESDAY, hours);
		schedule.put(Day.THURSDAY, hours);
		schedule.put(Day.FRIDAY, hours);
		schedule.put(Day.SATURDAY, hours);
	}

	/*
	 * returns whether organization is available at the current time
	 */
	public boolean isAvailable() {
		if (this.schedule.isEmpty()) {
			return true;
		}
		GregorianCalendar correctTime = new GregorianCalendar();
		int day = correctTime.get(Calendar.DAY_OF_WEEK);
		int hour = correctTime.get(Calendar.HOUR_OF_DAY);
		int minute = correctTime.get(Calendar.MINUTE);

		return isAvailableNow(convertIntToDay(day), hour, minute);
	}

	/*
	 * helper function for isAvailable
	 */
	private boolean isAvailableNow(Day day, int hour, int minute) {
		double minFraction = minute / 60.0;
		double hourMin = hour + minFraction;
		double open = schedule.get(day).getStartTime();
		double closed = schedule.get(day).getEndTime();

		if (Math.abs(closed - open) <= 0.001) {
			return false;
		}

		if (closed < open) {
			if ((hourMin <= 24 && hourMin >= open)
					|| (hourMin >= 0 && hourMin <= closed)) {
				return true;
			}
		}

		if (closed > open) {
			if (open <= hourMin && hourMin <= closed) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Converts int representation of day to enum for easier understanding
	 */
	private Day convertIntToDay(int day) {
		switch (day) {
			case 1:
				return Day.SUNDAY;
			case 2:
				return Day.MONDAY;
			case 3:
				return Day.TUESDAY;
			case 4:
				return Day.WEDNESDAY;
			case 5:
				return Day.THURSDAY;
			case 6:
				return Day.FRIDAY;
			case 7:
				return Day.SATURDAY;
			default:
				return null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		name = aName;
	}

	public long getNumber() {
		return this.number;
	}

	public void setNumber(long aNumber) {
		number = aNumber;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String anInfo) {
		info = anInfo;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String aDescription) {
		description = aDescription;
	}

	public int getImageID() {
		return this.imageID;
	}

	public void setImageID(int id) {
		this.imageID = id;
	}

	@Override
	public long getBitmapUri() {
		return 0L;
	}

	@Override
	public void setBitmapUri(long path) {
	}

	//--------------------Location Methods--------------------------
	@Override
	public void setRange(double[] lat_array, double[] lng_array) {
		this.lat_array = lat_array;
		this.lng_array = lng_array;
	}

	@Override
	public boolean isInRange(double latitude, double longitude) {

		PointInPolygon p = new PointInPolygon(lat_array, lng_array);
		if (lat_array != null && lng_array != null) {
			return p.coordinate_is_inside_polygon(latitude, longitude);
		}

		return false;
	}

	@Override
	public boolean hasRange() {
		return (lat_array != null && lng_array != null);
	}

	public enum Day {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
	}

	/*
	 * Inner class Hour that represents the opening and closing hours of an
	 * organization in an array of size 2
	 */
	public static class Hours {
		private double[] hours;

		public Hours(double start, double end) {
			hours = new double[2];
			hours[0] = start;
			hours[1] = end;
		}

		public double getStartTime() {
			return hours[0];
		}

		public double getEndTime() {
			return hours[1];
		}
	}
}
