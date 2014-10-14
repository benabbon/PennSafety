package com.android.pennaed.campussafety;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.pennaed.R;
import com.android.pennaed.campussafety.ContactStatic.Day;
import com.android.pennaed.campussafety.ContactStatic.Hours;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * MainActivity is the main page of the application containing all the
 * emergency contacts.
 */
public class MainActivity extends Activity implements LocationListener {

	private final String dps = "5514866991";
	Bitmap bitmap;
	private List<Contact> contactList = new ArrayList<Contact>();
	private ContactAdapter adapter;
	private long selectedImageUri;

	// Location variables
	private double latitude;
	private double longitude;
	private LocationManager locationManager;
	private String provider;
	private Location location;
	private boolean gpsEnabled;
	private boolean networkEnabled;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		initContacts();

		initLocation();

		adapter = new ContactAdapter(contactList, this);
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.android);
		updateData();
		ListView lv = (ListView) findViewById(R.id.listView);
		lv.setAdapter(adapter);
		setListener(lv);
		registerForContextMenu(lv);
	}

	private void initLocation() {
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		gpsEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
		networkEnabled = service
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		// Check if enabled, if not send to the GPS settings. Dialogue box
		// appears if app is opened for first time

		if (!gpsEnabled) {

			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

			// Setting Dialog Title
			alertDialog.setTitle("GPS Disabled...");

			// Setting Dialog Message
			alertDialog.setMessage("This app can use the GPS to tell if you "
					+ "are in the range of area-limited services. "
					+ "Do you want to go to GPS settings? "
					+ "(Else network information will be used)");

			// Setting Positive "Yes" Button
			alertDialog.setPositiveButton("yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							// Activity transfer to wifi settings
							Intent intent = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(intent);
						}
					});

			// Setting Negative "NO" Button
			alertDialog.setNegativeButton("no",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							dialog.cancel();
						}
					});

			// Showing Alert Message
			alertDialog.show();
		}

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Define the criteria of how to select the location provider. This uses
		// the default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			System.out.println("Provider " + provider + "has been selected.");
			onLocationChanged(location);
		} else {
			latitude = 0;
			longitude = 0;
		}
	}

	/*
	 * Makes a query to load contacts from database into the listview
	 */
	public void updateData() {
		ParseQuery<ContactDB> query = ParseQuery.getQuery(ContactDB.class);
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
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
	 * Sets listeners for each emergency contact item.
	 */
	private void setListener(ListView lv) {
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parentAdapter, View view,
			                        int position, long id) {
				Contact c = (Contact) parentAdapter.getItemAtPosition(position);
				if (c.isAvailable()) {
					if (!c.hasRange()
							|| (c.hasRange() && c
							.isInRange(latitude, longitude))) {
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + c.getNumber()));
						startActivity(intent);
					} else {
						Toast.makeText(
								MainActivity.this,
								c.getName()
										+ " is out of range, redirecting to DPS...",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + Long.parseLong(dps)));
						startActivity(intent);

					}
				} else {
					Toast.makeText(
							MainActivity.this,
							c.getName()
									+ " is unavailable right now, redirecting to DPS...",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + Long.parseLong(dps)));
					startActivity(intent);
				}
			}
		});
	}

	/*
	 * Create options to learn more about an organization or to delete one from
	 * the listview when user long clicks on a contact
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;

		Contact contact = adapter.getItem(aInfo.position);

		menu.setHeaderTitle("Options for " + contact.getName());
		MenuItem details = menu.add(1, 1, 1, "About");
		MenuItem delete = menu.add(1, 2, 2, "Delete");

		if (contact instanceof ContactStatic) {
			delete.setEnabled(false); // cannot delete preloaded contacts
		}
		if (contact instanceof ContactDB) {
			details.setEnabled(false); // cannot read about added contacts
		}
	}

	/*
	 * This method is called when user clicks an Item in the Context menu
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Contact c = contactList.get(aInfo.position);
		switch (itemId) {
			case 1: // about button
				Intent intent = new Intent(this, Info.class);
				intent.putExtra("Info", c.getInfo());
				intent.putExtra("Title", c.getName());
				startActivity(intent);
				break;
			case 2: // delete button
				contactList.remove(aInfo.position);
				ContactDB c2 = (ContactDB) c;
				c2.deleteEventually();
				break;
		}
		adapter.notifyDataSetChanged();
		return true;
	}

	/*
	 * Create the preloaded contact objects and add them to list view
	 */
	private void initContacts() {

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
	private Map<Day, Hours> getScheduleMERT() {

		// Map open times to closing times
		Hours hours = new Hours(17, 7);

		// Map days of the week to hours
		Map<Day, Hours> schedule = new HashMap<Day, Hours>();
		schedule.put(Day.MONDAY, hours);
		schedule.put(Day.TUESDAY, hours);
		schedule.put(Day.WEDNESDAY, hours);
		schedule.put(Day.THURSDAY, hours);
		schedule.put(Day.FRIDAY, hours);
		schedule.put(Day.SATURDAY, hours);
		schedule.put(Day.SUNDAY, hours);

		return schedule;
	}

	/*
	 * Helper that returns SHS's hours
	 */
	private Map<Day, Hours> getScheduleSHS() {

		// Map open times to closing times
		Hours monWedHours = new Hours(8, 19.5);
		Hours thursHours = new Hours(10, 17.5);
		Hours friHours = new Hours(8, 17.5);
		Hours satHours = new Hours(11, 16.5);
		Hours sunHours = new Hours(-1, -1);

		// Map days of the week to hours
		Map<Day, Hours> schedule = new HashMap<Day, Hours>();
		schedule.put(Day.MONDAY, monWedHours);
		schedule.put(Day.TUESDAY, monWedHours);
		schedule.put(Day.WEDNESDAY, monWedHours);
		schedule.put(Day.THURSDAY, thursHours);
		schedule.put(Day.FRIDAY, friHours);
		schedule.put(Day.SATURDAY, satHours);
		schedule.put(Day.SUNDAY, sunHours);
		return schedule;
	}

	/*
	 * Helper that returns CAPS's hours
	 */
	private Map<Day, Hours> getScheduleCAPS() {

		// Map open times to closing times
		Hours MTFHours = new Hours(9, 17);
		Hours WThHours = new Hours(10, 19);
		Hours weekendHours = new Hours(-1, -1);

		// Map days of the week to hours
		Map<Day, Hours> schedule = new HashMap<Day, Hours>();
		schedule.put(Day.MONDAY, MTFHours);
		schedule.put(Day.TUESDAY, MTFHours);
		schedule.put(Day.WEDNESDAY, WThHours);
		schedule.put(Day.THURSDAY, WThHours);
		schedule.put(Day.FRIDAY, MTFHours);
		schedule.put(Day.SATURDAY, weekendHours);
		schedule.put(Day.SUNDAY, weekendHours);

		return schedule;
	}

	/*
	 * Helper that returns PennTransit's schedule
	 */
	private Map<Day, Hours> getSchedulePennTransit() {
		// Map open times to closing times
		Hours MTWHours = new Hours(18, 7);
		Hours hours = new Hours(3, 7);

		// Map days of the week to hours
		Map<Day, Hours> schedule = new HashMap<Day, Hours>();
		schedule.put(Day.MONDAY, MTWHours);
		schedule.put(Day.TUESDAY, MTWHours);
		schedule.put(Day.WEDNESDAY, MTWHours);
		schedule.put(Day.THURSDAY, hours);
		schedule.put(Day.FRIDAY, hours);
		schedule.put(Day.SATURDAY, hours);
		schedule.put(Day.SUNDAY, hours);

		return schedule;
	}

	/*
	 * Method that gets triggered when user clicks on "add contact" button at
	 * bottom of the view
	 */
	public void addContact(View view) {
		final Dialog d = new Dialog(this);
		d.setContentView(R.layout.dialog);
		d.setTitle("Add contact");
		d.setCancelable(true);

		final EditText newName = (EditText) d.findViewById(R.id.etContact);
		final EditText newNum = (EditText) d.findViewById(R.id.etNumber);
		final EditText newInfo = (EditText) d.findViewById(R.id.etInfo);
		Button bAdd = (Button) d.findViewById(R.id.bAdd);
		Button bLoadPic = (Button) d.findViewById(R.id.bLoadPic);

		/*
		 * Set listener for "load pic" button in the dialog
		 */
		bLoadPic.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, 0);
			}
		});

		/*
		 * Set Listener for "add" button at the bottom
		 */
		bAdd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String contactName = newName.getText().toString();
				String contactNum = newNum.getText().toString();
				String contactDescription = contactNum + "\n"
						+ newInfo.getText().toString();

				// don't let user add unless they give a number
				if (contactNum.isEmpty()) {
					Toast.makeText(MainActivity.this,
							"Please fill in the contact number",
							Toast.LENGTH_SHORT).show();
				} else {
					if (contactName.isEmpty()) {
						contactName = contactNum;
					}
					ContactDB c = new ContactDB(contactName, PhoneNumber
							.convertToNum(contactNum), contactDescription);
					c.setBitmapUri(selectedImageUri);
					c.saveEventually();

					MainActivity.this.contactList.add(c);
					MainActivity.this.adapter.notifyDataSetChanged();
					d.dismiss();
				}
			}
		});
		d.show();
	}

	/*
	 * Called after user chooses a photo from gallery to include with the newly
	 * added contact.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri targetUri = data.getData();
			try {
				selectedImageUri = ContentUris.parseId(targetUri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// -------------------------Location Methods-------------------------

	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 1000, 1, this);
	}

	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String location) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();

		// If the GPS is turned off, use the network data
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, this);
		} else {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, this);
		}

	}

	@Override
	public void onProviderEnabled(String location) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, this);
		} else {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, this);
		}

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}