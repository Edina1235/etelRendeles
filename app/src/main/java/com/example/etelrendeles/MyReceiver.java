package com.example.etelrendeles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        new MyNotification(context).send("Rendelesed megerkezett");
    }
}