package com.google.se;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotfivationFragment extends android.support.v4.app.DialogFragment {
    Switch water,sleep,bcolori;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    final Calendar myCalendar = Calendar. getInstance () ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.notfication,container,false);
        water=view.findViewById(R.id.waters);
        bcolori=view.findViewById(R.id.bcoloris);

        if (water.isChecked()){
            scheduleNotification(getNotification("وقت آب خوردن",
                    "همین الان برای سلامتیت برو یه لیوان آب بخور"),0,16);
        }

        if (bcolori.isChecked()){
            scheduleNotification(getNotification("فعالیت روزانه",
                    "وقت ورزش کردن و فعالیته"),0,16);
        }
        return view;
    }


    private void scheduleNotification (Notification notification , long delay,int time) {
        Intent notificationIntent = new Intent(getContext(), MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog timePickerDialog=
                new com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, time);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Toast.makeText(getContext(), ""+calendar.getTimeInMillis(), Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_HOUR*4, pendingIntent);
    }



    private Notification getNotification(String contentTitle,String contet) {
        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle(contentTitle);
        builder.setContentText(contet);
        builder.setSmallIcon(R.drawable.icon);
        return builder.build();
    }
}
