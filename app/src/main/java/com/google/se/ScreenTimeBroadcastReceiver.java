package com.google.se;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import static com.google.se.HomePage.getdate;

public class ScreenTimeBroadcastReceiver extends WakefulBroadcastReceiver {
    String Sleep;
    String minute;
    private long startTimer;
    private long endTimer;
    private long screenOnTimeSingle;
    private long screenOnTime;
    private final long TIME_ERROR = 1000;
    IntentFilter lockFilter = new IntentFilter();
    private long screenOffTime;
    private long SleepTime = 0;
    boolean sleepFlag = false;
    private long ScreenOnTimerStart;
    private long ScreenOnTimerEnd;
    public String getSleep(){
        return this.Sleep;
    }
    public String getMinute(){
        return this.minute;
    }
    public void onReceive(Context context, Intent intent) {

//        System.out.println("Broadcast");
//        Toast.makeText(context, "Broadcast Received",Toast.LENGTH_LONG).show();

        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            startTimer = System.currentTimeMillis();
            ScreenOnTimerEnd = System.currentTimeMillis();
            screenOnTime = ScreenOnTimerStart - ScreenOnTimerEnd;
            int minutes = (int) ((screenOnTime  / (1000*60)) % 60);
            if (sleepFlag && minutes > 15) {
                sleepFlag = false;
                SleepTime = SleepTime + screenOffTime ;

                int minutes2 = (int) ((SleepTime / (1000*60)) % 60);
                int hours2   = (int) ((SleepTime / (1000*60*60)) % 24);
                String minutesStr = minutes2 + "min";
                minute=minutesStr;

                String SleepTrack =  hours2 + "";
//                    Toast.makeText(context, SleepTrack.charAt(0),Toast.LENGTH_LONG).show();
                Sleep=SleepTrack;
                DailyIDataDBHelper dailyIDataDBHelper = new DailyIDataDBHelper(context);
                SleepModel sleepTrack = new SleepModel();
                sleepTrack.setSleep(Sleep);
                sleepTrack.setSleepM(minute);
                sleepTrack.setDate(getdate());
                dailyIDataDBHelper.InsertSleep(sleepTrack);

            }else {
                if(sleepFlag){
                    SleepTime = SleepTime + screenOffTime + screenOnTime ;
                    int minutes2 = (int) ((SleepTime / (1000*60)) % 60);
                    int hours2   = (int) ((SleepTime / (1000*60*60)) % 24);
                    String minutesStr = minutes2 + "min";
                    minute=minutesStr;

                    String SleepTrack =  hours2 + "";
//                    Toast.makeText(context, SleepTrack.charAt(0),Toast.LENGTH_LONG).show();
                    Sleep=SleepTrack;
                }
            }
//            Toast.makeText(context, "Timer Started "+startTimer,Toast.LENGTH_LONG).show();

        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            endTimer = System.currentTimeMillis();
            ScreenOnTimerStart = System.currentTimeMillis();
            screenOffTime = endTimer - startTimer;

//            Toast.makeText(context, "Timer Ended "+endTimer,Toast.LENGTH_LONG).show();
//            Toast.makeText(context, "Screen Off Time Is: " + screenOffTime,Toast.LENGTH_LONG).show();
            int minutes = (int) ((screenOffTime / (1000*60)) % 60);
            int hours   = (int) ((screenOffTime / (1000*60*60)) % 24);
            if( minutes > 30 && hours > 3) {

                sleepFlag = true;
                SleepTime = SleepTime + screenOffTime;

                int minutes2 = (int) ((SleepTime / (1000*60)) % 60);
                int hours2   = (int) ((SleepTime / (1000*60*60)) % 24);
                String minutesStr = minutes2 + "min";
                minute=minutesStr;

                String SleepTrack =  hours2 + "";
                //                    Toast.makeText(context, SleepTrack.charAt(0),Toast.LENGTH_LONG).show();
                Sleep=SleepTrack;

            }
            if (sleepFlag){
                SleepTime = SleepTime + screenOffTime;
                int minutes2 = (int) ((SleepTime / (1000*60)) % 60);
                int hours2   = (int) ((SleepTime / (1000*60*60)) % 24);
                String minutesStr = minutes2 + "min";
                minute=minutesStr;

                String SleepTrack =  hours2 + "";
                //                    Toast.makeText(context, SleepTrack.charAt(0),Toast.LENGTH_LONG).show();
                Sleep=SleepTrack;
            }

        }
        if (Sleep != null) {
            HomePage.Sleepe.setText(String.valueOf(Sleep));
            HomePage.minutee.setText(String.valueOf(minute));

        }
    }
}