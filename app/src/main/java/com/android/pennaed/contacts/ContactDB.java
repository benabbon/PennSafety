package com.android.pennaed.contacts;

import com.android.pennaed.R;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
 * ContactDB instances represent contacts added by the user that are stored
 * in the Parse database.
 */
@ParseClassName("Contact")
public class ContactDB extends ParseObject implements Contact {

	public ContactDB() {
	}

	public ContactDB(String aName, long aNumber, String aDescription) {
		setName(aName);
		setNumber(aNumber);
		setDescription(aDescription);
		setInfo("No Info Yet");
		setImageID(R.drawable.android);
		setBitmapUri(0);
	}

	@Override
	public long getNumber() {
		return getLong("number");
	}

	@Override
	public void setNumber(long number) {
		put("number", number);
	}

	@Override
	public String getName() {
		return getString("name");
	}

	@Override
	public void setName(String n) {
		put("name", n);
	}

	@Override
	public String getInfo() {
		return "";
	}

	@Override
	public void setInfo(String anInfo) {
	}

	@Override
	public String getDescription() {
		return getString("description");
	}

	@Override
	public void setDescription(String description) {
		put("description", description);
	}

	@Override
	public int getImageID() {
		return getInt("image_id");
	}

	@Override
	public void setImageID(int id) {
		put("image_id", id);
	}

	@Override
	public long getBitmapUri() {
		return getLong("bitmap");
	}

	@Override
	public void setBitmapUri(long path) {
		put("bitmap", path);
	}

	@Override
	public boolean isAvailable() {
		return true; // added contacts are always available
	}
	
	/*
	 * ContactDB does not support location checking
	 */

	@Override
	public void setRange(double[] lat_array, double[] lng_array) {
	}

	@Override
	public boolean isInRange(double latitude, double longitude) {
		return false;
	}

	@Override
	public boolean hasRange() {
		return false;
	}
}
