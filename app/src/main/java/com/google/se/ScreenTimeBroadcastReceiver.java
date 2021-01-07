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
    String hr,min;
    private long ScreenOnTimerStart;
    DailyIDataDBHelper dailyIDataDBHelper;
    private long ScreenOnTimerEnd;
    public String getSleep(){
        return this.Sleep;
    }
    public String getMinute(){
        return this.minute;
    }
    public void onReceive(Context context, Intent intent) {
        dailyIDataDBHelper  = new DailyIDataDBHelper(context);
        if (dailyIDataDBHelper.GetSleep().size()==0){

        }
        else {
            hr = String.valueOf(dailyIDataDBHelper.GetSleep().get(dailyIDataDBHelper.GetSleep().size()-1).getSleep());
            min = String.valueOf(dailyIDataDBHelper.GetSleep().get(dailyIDataDBHelper.GetSleep().size()-1).getSleepM());

        }
//        System.out.println("Broadcast");
        Toast.makeText(context, min,Toast.LENGTH_LONG).show();

        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            startTimer = System.currentTimeMillis();
            ScreenOnTimerEnd = System.currentTimeMillis();
            screenOnTime = ScreenOnTimerStart - ScreenOnTimerEnd;
            int minutes = (int) ((screenOnTime  / (1000*60)) % 60);
            if (sleepFlag && minutes > 1) {
                sleepFlag = false;
                SleepTime =  screenOffTime ;

                int hours2;
                if (hr != null){
                    hours2 = (int) ((SleepTime / (1000*60*60)) % 24) + Integer.parseInt(hr);
                }
                else{
                    hours2 = (int) ((SleepTime / (1000*60*60)) % 24);

                }

                int minutes2;
                if (min != null){
                    minutes2 = (int) ((SleepTime / (1000*60)) % 60) +  Integer.parseInt(min);
                }
                else{
                    minutes2 = (int) ((SleepTime / (1000*60)) % 60) ;

                }
                String minutesStr = minutes2 - hours2*60 + "";
                minute=minutesStr;

                String SleepTrack =  hours2 + "";
                Toast.makeText(context, "SleepFlag",Toast.LENGTH_LONG).show();
                Sleep=SleepTrack;


            }else {
                if(sleepFlag){
                    SleepTime =  screenOffTime + screenOnTime ;
                    int hours2;
                    if (hr != null){
                        hours2 = (int) ((SleepTime / (1000*60*60)) % 24) + Integer.parseInt(hr);
                    }
                    else{
                        hours2 = (int) ((SleepTime / (1000*60*60)) % 24);

                    }

                    int minutes2;
                    if (min != null){
                        minutes2 = (int) ((SleepTime / (1000*60)) % 60) +  Integer.parseInt(min);
                    }
                    else{
                        minutes2 = (int) ((SleepTime / (1000*60)) % 60) ;

                    }
                    String minutesStr = minutes2 - hours2*60 + "";
                    minute=minutesStr;

                    String SleepTrack =  hours2 + "";
                    Toast.makeText(context, "SleepFlag",Toast.LENGTH_LONG).show();
                    Sleep=SleepTrack;
                }
            }
            Toast.makeText(context, "Timer Started "+startTimer,Toast.LENGTH_LONG).show();

        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            endTimer = System.currentTimeMillis();
            ScreenOnTimerStart = System.currentTimeMillis();
            screenOffTime = endTimer - startTimer;

            Toast.makeText(context, "Timer Ended "+endTimer,Toast.LENGTH_LONG).show();
            Toast.makeText(context, "Screen Off Time Is: " + screenOffTime,Toast.LENGTH_LONG).show();
            int minutes = (int) ((screenOffTime / (1000*60)) % 60);
            int hours   = (int) ((screenOffTime / (1000*60*60)) % 24);
            if(!sleepFlag && minutes > 1) {

                sleepFlag = true;
                SleepTime =  screenOffTime;
                int hours2;
                if (hr != null){
                    hours2 = (int) ((SleepTime / (1000*60*60)) % 24) + Integer.parseInt(hr);
                }
                else{
                    hours2 = (int) ((SleepTime / (1000*60*60)) % 24);

                }

                int minutes2;
                if (min != null){
                    minutes2 = (int) ((SleepTime / (1000*60)) % 60) +  Integer.parseInt(min);
                }
                else{
                    minutes2 = (int) ((SleepTime / (1000*60)) % 60) ;

                }
                String minutesStr = minutes2 - hours2*60 + "";
                minute=minutesStr;

                String SleepTrack =  hours2 + "";
                Toast.makeText(context, "SleepFlag",Toast.LENGTH_LONG).show();
                Sleep=SleepTrack;

            }
            if (sleepFlag){
                SleepTime = screenOffTime;
                int hours2;
                if (hr != null){
                    hours2 = (int) ((SleepTime / (1000*60*60)) % 24) + Integer.parseInt(hr);
                }
                else{
                    hours2 = (int) ((SleepTime / (1000*60*60)) % 24);

                }

                int minutes2;
                if (min != null){
                    minutes2 = (int) ((SleepTime / (1000*60)) % 60) +  Integer.parseInt(min);
                }
                else{
                    minutes2 = (int) ((SleepTime / (1000*60)) % 60) ;

                }
                String minutesStr = minutes2 - hours2*60 + "";
                minute=minutesStr;

                String SleepTrack =  hours2 + "";
                Toast.makeText(context, "SleepFlag",Toast.LENGTH_LONG).show();
                Sleep=SleepTrack;

            }

        }
        Toast.makeText(context, minute,Toast.LENGTH_LONG).show();

        if (Sleep != null) {

            SleepModel sleepTrack = new SleepModel();
            sleepTrack.setSleep(Sleep);
            sleepTrack.setSleepM(minute);
            sleepTrack.setDate(getdate());
            dailyIDataDBHelper.InsertSleep(sleepTrack);

        }
    }
}