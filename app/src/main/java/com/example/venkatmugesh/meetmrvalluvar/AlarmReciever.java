package com.example.venkatmugesh.meetmrvalluvar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver {
    int notifyId = 0;
    String channelId;
    @Override
    public void onReceive(Context context, Intent intent) {

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, ViewKural.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Log.i("reached" , "here");

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context ,channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Mr.Valluvar is Waiting")
                .setContentText("Another day Another Kural....!!").setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        notificationManager.notify(notifyId, mNotifyBuilder.build());
        notifyId++;

    }

}

