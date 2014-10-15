package com.android.pennaed.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pennaed.R;

import java.util.List;

/*
 * ContactAdapter extends ArrayAdapter and is used to populate the ListView
 * in ContactsFragment
 */
public class ContactAdapter extends ArrayAdapter<Contact> {
	private List<Contact> contactList;
	private Context context;

	public ContactAdapter(List<Contact> contactList, Context ctx) {
		super(ctx, R.layout.contacts_row_layout, contactList);
		this.contactList = contactList;
		this.context = ctx;
	}

	public int getCount() {
		return contactList.size();
	}

	public Contact getItem(int position) {
		return contactList.get(position);
	}

	public long getItemId(int position) {
		return contactList.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ContactHolder holder = new ContactHolder();

		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.contacts_row_layout, null);
			// Now we can fill the layout with the right values
			TextView name = (TextView) v.findViewById(R.id.contactName);
			TextView info = (TextView) v.findViewById(R.id.contactInfo);
			ImageView img = (ImageView) v.findViewById(R.id.img);

			holder.tvContactName = name;
			holder.tvContactInfo = info;
			holder.img = img;

			v.setTag(holder);
		} else {
			holder = (ContactHolder) v.getTag();
		}

		Contact p = contactList.get(position);
		holder.tvContactName.setText(p.getName());
		holder.tvContactInfo.setText(p.getDescription());
		if (p.getBitmapUri() != 0L) {
			Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(
					context.getContentResolver(), p.getBitmapUri(),
					MediaStore.Images.Thumbnails.MICRO_KIND, null);
			holder.img.setImageBitmap(bm);
		} else {
			holder.img.setImageResource(p.getImageID());
		}
		return v;
	}

	/*
	 * For faster access to the text and image views, we will store them here
	 */
	private static class ContactHolder {
		public TextView tvContactName;
		public TextView tvContactInfo;
		public ImageView img;
	}

}
