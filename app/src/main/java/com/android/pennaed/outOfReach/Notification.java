package com.android.pennaed.outOfReach;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.android.pennaed.MainNavigation;
import com.android.pennaed.R;

/**
 * Created by nabilbenabbou1 on 11/1/14.
 */
public class Notification {

	public static void createOutOfReachNotification(Context context) {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		if (!preferences.getBoolean("allow_notifications", true)) {
			return;
		}

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(context)
						.setSmallIcon(R.drawable.penn_logo)
						.setContentTitle("Out of reach")
						.setContentText("You're now in a zone where penn safety can't reach you.");
		// Specifiy the call back when clicking on the notification.
		Intent resultIntent = new Intent(context, MainNavigation.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT
				);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// id allows to update the notification later on.
		mNotificationManager.notify(1, mBuilder.build());
	}
}
