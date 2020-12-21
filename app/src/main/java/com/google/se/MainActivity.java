package com.google.se;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SignUpDBHelper signUpDBHelper;
    String url = "http://healthcareassistantproject.ir/mySite/fetch.php";
    static List<Signup> peaple=new ArrayList<>();
    static List<Signup> person=new ArrayList<>();
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    final Calendar myCalendar = Calendar. getInstance () ;
    String datetxt;
    Button btnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpDBHelper=new SignUpDBHelper(this);
        person=signUpDBHelper.GetAll();
        btnDate=findViewById(R.id.button2);
        loadProducts();
     //   Toast.makeText(this, ""+peaple.size(), Toast.LENGTH_SHORT).show();
        if (person.size()==0){
            startActivity(new Intent(MainActivity.this,StartActivity.class));
        }
        else {
           startActivity(new Intent(MainActivity.this,HomePage.class));
        }
        scheduleNotification(getNotification("همین الان برای سلامتیت برو یه لیوان آب بخور"),0);

    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                Signup person=new Signup();
                                JSONObject product = array.getJSONObject(i);
                                person.setId(product.getString("Id"));
                                person.setPassword(product.getString("Password"));
                                peaple.add(person);
                       //         Toast.makeText(MainActivity.this, ""+peaple.size(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void scheduleNotification (Notification notification , long delay) {
            Intent notificationIntent = new Intent(this, MyNotificationPublisher.class);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog timePickerDialog=
                new com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog();

           Calendar calendar = Calendar.getInstance();
           calendar.set(Calendar.HOUR_OF_DAY, 16);
           calendar.set(Calendar.MINUTE, 00);
           calendar.set(Calendar.SECOND, 00);
            Toast.makeText(this, ""+calendar.getTimeInMillis(), Toast.LENGTH_SHORT).show();
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_HOUR*4, pendingIntent);
        }



    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("وقت آب خوردنه");
        builder.setContentText("همین الان برای سلامتیت برو یه لیوان آب بخور");
        builder.setSmallIcon(R.drawable.icon);
        return builder.build();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet (DatePicker view , int year , int monthOfYear , int dayOfMonth) {
            myCalendar .set(Calendar. YEAR , year) ;
            myCalendar .set(Calendar. MONTH , monthOfYear) ;
            myCalendar .set(Calendar. DAY_OF_MONTH , dayOfMonth) ;
            updateLabel() ;
        }
    } ;


    public void setDate (View view) {
        new DatePickerDialog(
                MainActivity. this, date ,
                myCalendar .get(Calendar. YEAR ) ,
                myCalendar .get(Calendar. MONTH ) ,
                myCalendar .get(Calendar. DAY_OF_MONTH )
        ).show() ;
    }

    private void updateLabel () {
        Toast.makeText(this, "sgavsckb", Toast.LENGTH_SHORT).show();
        String myFormat = "21/12/2020" ; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
        Date date = myCalendar .getTime() ;
        btnDate .setText(sdf.format(date)) ;
        datetxt=sdf.format(date);
        scheduleNotification(getNotification( "mio mio"), 0) ;
    }



}
