package com.android.pennaed.contacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.pennaed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabilbenabbou1 on 10/14/14.
 */

/*
 * this is the main page of the part containing all the
 * emergency contacts.
 */
public class ContactsFragment extends Fragment implements LocationListener {

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
	private SetupContacts setupContacts;

	public ContactsFragment(){
		setupContacts = new SetupContacts(contactList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contacts_main, container,
				false);
//		initContacts();
		setupContacts.initContacts();
		initLocation();
		adapter = new ContactAdapter(contactList, getActivity());
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.android);
		setupContacts.updateData(adapter);
		ListView lv = (ListView) view.findViewById(R.id.listView);
		lv.setAdapter(adapter);
		setListener(lv);
		registerForContextMenu(lv);

		//add_emergency_contact_button
		Button addContactButton = (Button) view.findViewById(R.id.add_emergency_contact_button);
		addContactButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addContact(v);
			}
		});
		return view;
	}

	private void initLocation() {
		LocationManager service = (LocationManager) getActivity().
				getSystemService(getActivity().LOCATION_SERVICE);
		gpsEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
		networkEnabled = service
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		// Check if enabled, if not send to the GPS settings. Dialogue box
		// appears if app is opened for first time

		if (!gpsEnabled) {

			AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

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

		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

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
								getActivity(),
								c.getName()
										+ " is out of range, redirecting to DPS...",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + Long.parseLong(dps)));
						startActivity(intent);

					}
				} else {
					Toast.makeText(
							getActivity(),
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
	                                ContextMenu.ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

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
		AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Contact c = contactList.get(aInfo.position);
		switch (itemId) {
			case 1: // about button
				Intent intent = new Intent(getActivity(), Info.class);
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
	 * Method that gets triggered when user clicks on "add contact" button at
	 * bottom of the view
	 */
	public void addContact(View view) {
		final Dialog d = new Dialog(getActivity());
		d.setContentView(R.layout.contacts_dialog);
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
					Toast.makeText(getActivity(),
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

					contactList.add(c);
					adapter.notifyDataSetChanged();
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == getActivity().RESULT_OK) {
			Uri targetUri = data.getData();
			try {
				selectedImageUri = ContentUris.parseId(targetUri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// -------------------------Location Methods-------------------------

	@Override
	public void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 1000, 1, this);
	}

	@Override
	public void onPause() {
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
		Toast.makeText(getActivity(), "Disabled provider " + provider,
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
		Toast.makeText(getActivity(), "Enabled new provider " + provider,
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