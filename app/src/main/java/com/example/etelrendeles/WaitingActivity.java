package com.example.etelrendeles;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class WaitingActivity extends AppCompatActivity {

    MyNotification notification;

    MediaPlayer mp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_activity);
        mp = MediaPlayer.create(this, R.raw.motor);

        Intent intent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long aktualis = System.currentTimeMillis();
        long fiveMinute = 5*1000;

        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, aktualis + fiveMinute, pendingIntent);
        notification = new MyNotification(this);
        uzen();
    }


    public void uzen() {
        mp.start();
        Toast.makeText(this, "5 percen belül megérkezik a rendelésed", Toast.LENGTH_LONG).show();


        final Timer myt = new Timer();
        myt.schedule(new TimerTask() {

            @Override
            public void run() {
                notification.send("Rendelésed megérkezett");

                try {
                    Intent intent = new Intent(WaitingActivity.this, EtelekActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d(WaitingActivity.class.getName(), e.getMessage());
                }

                myt.cancel();
            }
        }, 300000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notification.cancel();
    }
}
