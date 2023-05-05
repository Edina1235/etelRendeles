package com.example.etelrendeles;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


public class MyNotification {
    private static final String CHANNEL_ID = "rendeles_notification_channel";
    private final int NOTIFICATION_ID = 0;

    private NotificationManager nManager;
    private Context context;


    public MyNotification(Context context) {
        this.context = context;
        this.nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel channel = new NotificationChannel
                (CHANNEL_ID, "Rendeles Notification", NotificationManager.IMPORTANCE_HIGH);

        channel.enableVibration(true);
        channel.setDescription("Notifications from Rendeles application");

        nManager.createNotificationChannel(channel);
        Log.d(WaitingActivity.class.getName(), "channel");
    }

    public void send(String message) {
        Log.d(WaitingActivity.class.getName(), "send");
        Intent intent = new Intent(context, EtelekActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Rendeles")
                .setContentText(message)
                .setSmallIcon(R.drawable.rendel)
                .setContentIntent(pendingIntent);

        nManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void cancel() {
        Log.d(WaitingActivity.class.getName(), "cancel");
        nManager.cancel(NOTIFICATION_ID);
    }
}
