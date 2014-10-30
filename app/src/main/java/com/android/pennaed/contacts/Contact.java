package com.android.pennaed.contacts;

/*
 * Interface that lists all methods that need to be implemented by 
 * Contact items
 */
public interface Contact {

	public abstract long getNumber();

	public abstract void setNumber(long number);

	public abstract String getName();

	public abstract void setName(String n);

	public abstract String getInfo();

	public abstract void setInfo(String anInfo);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract int getImageID();

	public abstract void setImageID(int id);

	public abstract long getBitmapUri();

	public abstract void setBitmapUri(long path);

	public abstract boolean isAvailable();

	public abstract void setRange(double[] lat_array, double[] lng_array);

	public abstract boolean isInRange(double latitude, double longitude);

	public boolean hasRange();

}