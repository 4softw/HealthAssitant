package com.google.se;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

public class SleepTrackingService extends Service {
    IntentFilter lockFilter = new IntentFilter();
    String Sleep;
    String minute;

    public String getSleep(){
        return this.Sleep;
    }
    public String getMinute(){
        return this.minute;
    }
    ScreenTimeBroadcastReceiver broadcastReceiver = new ScreenTimeBroadcastReceiver();



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startService(new Intent(this,SleepTrackingService.class));
        lockFilter.addAction(Intent.ACTION_SCREEN_ON);
        lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, lockFilter);
        registerReceiver(broadcastReceiver, lockFilter);
        Sleep = broadcastReceiver.getSleep();
        minute = broadcastReceiver.getMinute();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}