package com.google.se;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends AppCompatActivity implements SensorEventListener,NavigationView.OnNavigationItemSelectedListener {

    static TextView hi, Maincolori, date, showrate,useColori,burncolori;
    com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar burndprogressBar,burndprogressBar2;
    boolean b=false;
    static TextView water,point;
    public static final String TAG = "Hiii";
    String url = "http://healthcareassistantproject.ir/mySite/fetchwithid.php";
    String name;
    NavigationView navigationView;
    RelativeLayout searchfood,waterlayout;
    DrawerLayout drawerLayout;
    SignUpDBHelper signUpDBHelper;
    ImageView add, minus;
    ImageView heart, fab,pickimage;
    StepsDBHelper mStepsDBHelper;
    ArrayList<DateStepsModel> mStepCountList;
    static PowerManager.WakeLock mWakeLock;
    CircularProgressBar circulartime, circularstep, circuldistance;
    TextView mainstep, step, time, persent;
    static List<InformationModel> person = new ArrayList<>();
    SensorManager sensorManager;
    Button FoodAdd;
    DailyIDataDBHelper dailyIDataDBHelper;
    static TextView Sleepe;
    static TextView minutee;
    SleepTrackingService service = new SleepTrackingService();
    private IntentFilter lockFilter;
    private ScreenTimeBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.startForegroundService(new Intent(this, SleepTrackingService.class));
//        } else {
//            this.startService(new Intent(this, SleepTrackingService.class));
//        }
//        startService(new Intent(this,SleepTrackingService.class));
//        startForegroundService(new Intent(this,SleepTrackingService.class));
        lockFilter.addAction(Intent.ACTION_SCREEN_ON);
        lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, lockFilter);
        registerReceiver(broadcastReceiver, lockFilter);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page1);
        signUpDBHelper = new SignUpDBHelper(this);
        person = signUpDBHelper.GetInf();
        b=isStoragePermissionGranted();
        dailyIDataDBHelper=new DailyIDataDBHelper(this);
        init();
        loadFromFile();
        if (dailyIDataDBHelper.GetWater().size()==0){
        //    Toast.makeText(this, ""+dailyIDataDBHelper.GetWater().size(), Toast.LENGTH_SHORT).show();
            WaterModel waterModel=new WaterModel();
            waterModel.setGlass(0);
            waterModel.setTime(getdate());
            dailyIDataDBHelper.Insertwater(waterModel);
        }
        else {
            water.setText(String.valueOf(dailyIDataDBHelper.GetWater().get(dailyIDataDBHelper.GetWater().size()-1).getGlass()));
        }
        if (dailyIDataDBHelper.GetSleep().size()==0){

        }
        else {
            Sleepe.setText(String.valueOf(dailyIDataDBHelper.GetSleep().get(dailyIDataDBHelper.GetSleep().size()-1).getSleep()));
            String tmp = String.valueOf(dailyIDataDBHelper.GetSleep().get(dailyIDataDBHelper.GetSleep().size()-1).getSleepM())+"min";
            minutee.setText(tmp);
            burndprogressBar2.setProgress(Integer.parseInt(Sleepe.getText().toString())*60+Integer.parseInt(dailyIDataDBHelper.GetSleep().get(dailyIDataDBHelper.GetSleep().size()-1).getSleepM()));
        }
        if (dailyIDataDBHelper.GetPoints().size()==0){
            PointModel pointModel=new PointModel();
            pointModel.setPoint(0);
            pointModel.setDate(getdate());
            dailyIDataDBHelper.InsertPoint(pointModel);
        }
        else {
            point.setText(String.valueOf(dailyIDataDBHelper.GetPoints().get(dailyIDataDBHelper.GetPoints().size()-1).getPoint()));
        }
        File f = new File(Environment.getExternalStorageDirectory().toString() + "/saved");
        if (b==false|| !f.exists()){
            pickimage.setImageResource(R.drawable.pp);
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Intent mStepsIntent = new Intent(this, StepsService.class);
        startService(mStepsIntent);
        FoodAdd = findViewById(R.id.AddFood);
        FoodAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,FoodInfo.class));
            }
        });
    //    Toast.makeText(this, "" + person.size(), Toast.LENGTH_SHORT).show();
        if (person.size() == 0) {
            burndprogressBar.setProgress(0);
            GetFromHost(MainActivity.person.get(0).getId());
            person = signUpDBHelper.GetInf();
        }


        if (person.size() != 0) {
            hi.setText(" سلام " + person.get(0).getName());
            double c = calculatingColori(person.get(0).getSex());
            Maincolori.setText(String.valueOf((int) c));

            PersianCalendar pc = new PersianCalendar();
            date.setText(pc.getPersianLongDate());

            int addd = Integer.parseInt(water.getText().toString());
            if (addd == 0) {
                minus.animate().alpha(0.5f);
            }

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int addd = Integer.parseInt(water.getText().toString());
                    addd++;
                    water.setText(String.valueOf(addd));
                    WaterModel waterModel=new WaterModel();
                    waterModel.setGlass(addd);
                    waterModel.setTime(HomePage.getdate());
                    dailyIDataDBHelper.Insertwater(waterModel);
                    calPoint(10,HomePage.this);

                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int addd = Integer.parseInt(water.getText().toString());
                    if (addd != 0) {
                        addd = addd - 1;
                        water.setText(String.valueOf(addd));
                        WaterModel waterModel=new WaterModel();
                        waterModel.setGlass(addd);
                        waterModel.setTime(HomePage.getdate());
                        dailyIDataDBHelper.Insertwater(waterModel);
                        calPoint(-10,HomePage.this);
                    }
                }
            });
            circularstep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForChangeStepts();
                }
            });

        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, null,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    void init() {
        minutee = findViewById(R.id.minutes);
        Sleepe = findViewById(R.id.Sleep);
        hi = findViewById(R.id.hi);
        Maincolori = findViewById(R.id.MainColori);
        date = findViewById(R.id.date);
        add = findViewById(R.id.add);
        minus = findViewById(R.id.minus);
        point = findViewById(R.id.point);
        water = findViewById(R.id.water);
        circularstep = findViewById(R.id.circularstep);
        circulartime = findViewById(R.id.circultime);
        mainstep = findViewById(R.id.Mainstepcounter);
        step = findViewById(R.id.stepcounter);
        time = findViewById(R.id.timecounter);
        useColori=findViewById(R.id.useColori);
        persent = findViewById(R.id.persent);
        heart = findViewById(R.id.heart);
        showrate = findViewById(R.id.showrate);
        fab=findViewById(R.id.fab);
        burncolori=findViewById(R.id.burnColori);
        burndprogressBar=findViewById(R.id.progressBar2);
        burndprogressBar2=findViewById(R.id.progressBar);
        burndprogressBar2.setMax(480);
        burndprogressBar.setMax(5000);
        searchfood=findViewById(R.id.searchfood);
        waterlayout=findViewById(R.id.waterLayout);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(Gravity.RIGHT);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        pickimage=header.findViewById(R.id.pickImage);
        pickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (b==true){
                    getImageFromAlbum();
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        circuldistance = findViewById(R.id.circuldistance);
        circularstep.setProgressMax(Integer.parseInt(mainstep.getText().toString()));
        useColori.setText(String.valueOf((int)usedColori()));
        float distance = 0;
        if (person.size() != 0) {
            if (person.get(0).getSex().equals("man")) {
                distance = Integer.parseInt(mainstep.getText().toString()) * 78 / (float) 100;
            }
            if (person.get(0).getSex().equals("woman")) {
                distance = Integer.parseInt(mainstep.getText().toString()) * 70 / (float) 100;
            }
            circuldistance.setProgressMax(distance);
            circulartime.setProgressMax(100);
        }

        searchfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,FoodPage.class));
            }
        });

        waterlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                WaterFragment waterFragment = new WaterFragment();
                waterFragment.show(HomePage.this.getSupportFragmentManager(), "show");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        useColori.setText(String.valueOf((int)usedColori()));
        drawerLayout.closeDrawer(Gravity.RIGHT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dailyIDataDBHelper=new DailyIDataDBHelper(this);
        if (dailyIDataDBHelper.GetSleep().size()==0){

        }
        else {
            Sleepe.setText(String.valueOf(dailyIDataDBHelper.GetSleep().get(dailyIDataDBHelper.GetSleep().size()-1).getSleep()));
            String tmp = String.valueOf(dailyIDataDBHelper.GetSleep().get(dailyIDataDBHelper.GetSleep().size()-1).getSleepM())+"min";
            minutee.setText(tmp);
            burndprogressBar2.setProgress(Integer.parseInt(Sleepe.getText().toString())*60+Integer.parseInt(dailyIDataDBHelper.GetSleep().get(dailyIDataDBHelper.GetSleep().size()-1).getSleepM()));
        }
        useColori.setText(String.valueOf((int)usedColori()));
        Sensor countsteps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, countsteps,
                SensorManager.SENSOR_DELAY_NORMAL);


        if (countsteps != null) {
            sensorManager.registerListener(this, countsteps, SensorManager.SENSOR_DELAY_UI);
        }
        drawerLayout.closeDrawer(Gravity.RIGHT);
//        registerReceiver(broadcastReceiver, lockFilter);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

    public void GetFromHost(final String id) {
        RequestQueue queue = Volley.newRequestQueue(HomePage.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SignUpDBHelper signUpDBHelper = new SignUpDBHelper(HomePage.this);
                try {
                    JSONArray array = new JSONArray(response);
                    InformationModel model = new InformationModel();
                    JSONObject product = array.getJSONObject(0);
                    model.setName(product.getString("name"));
                    model.setAge(product.getString("age"));
                    model.setHeight(product.getString("height"));
                    model.setWeight(product.getString("weight"));
                    model.setId(product.getString("id"));
                    model.setSex(product.getString("sex"));
                    model.setLastname(product.getString("lastname"));
                    signUpDBHelper.InsertInf(model);
             //       Toast.makeText(HomePage.this, "" + person.size(), Toast.LENGTH_SHORT).show();
                    person.add(model);
                    hi.setText(" سلام " + person.get(0).getName());
                    double c = calculatingColori(person.get(0).getSex());
                    Maincolori.setText(String.valueOf((int) c));
                 //   Toast.makeText(HomePage.this, "" + person.size(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(HomePage.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", id);
                return map;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(request);
    }


    static double calculatingColori(String sex) {
        double result = 0;
        if (sex.equals("woman")) {
            double part1 = (Float.parseFloat(person.get(0).getWeight()) * 9.563) + 655.1;
            double part2 = (Float.parseFloat(person.get(0).getHeight()) * 1.850);
            double part3 = (Float.parseFloat(person.get(0).getAge()) * 4.676);
            result = part1 + part2 - part3;
        }

        if (sex.equals("man")) {
            double part1 = (Float.parseFloat(person.get(0).getWeight()) * 13.75) + 66.47;
            double part2 = (Float.parseFloat(person.get(0).getHeight()) * 5.003);
            double part3 = (Float.parseFloat(person.get(0).getAge()) * 6.755);
            result = part1 + part2 - part3;
        }
        return result;
    }

    double usedColori(){
        double colori = 0;
        List<FoodModel> list;
        list=dailyIDataDBHelper.GetTodayFood("");
        for (int i=0;i<list.size();i++){
            colori=colori+list.get(i).getColorie();
        }
        return colori;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        mStepsDBHelper = new StepsDBHelper(this);
        mStepCountList = mStepsDBHelper.readStepsEntries(getApplicationContext());
        circularstep.setProgress((float)mStepCountList.size());
        step.setText(String.valueOf(mStepCountList.size()));
        float distance = 0;

            if (person.size() != 0) {
                if (person.get(0).getSex().equals("man")) {
                    distance = (float) ((int) mStepCountList.size() * 78) / (float) 100;
                }
                if (person.get(0).getSex().equals("woman")) {
                    distance = (float) ((int)mStepCountList.size() * 70) / (float) 100;
                }
            }
            persent.setText(String.valueOf((int) distance));
            circuldistance.setProgress(distance);
            if (Integer.parseInt(persent.getText().toString()) % 50 == 0) {
                calPoint(1,HomePage.this);
            }

            float time1 = (float) ((int) mStepCountList.size()* 100) / (float) 10000;
            circulartime.setProgress(time1);
            time.setText(String.valueOf((int) time1));
            if (person.size()!=0){
                burnedColori(time1);
                burndprogressBar.setProgress((int)burnedColori(time1));
                burncolori.setText(String.valueOf((int)burnedColori(time1)));
            }
    }

    void FirstStepinitial(){
        mStepsDBHelper = new StepsDBHelper(this);
        mStepCountList = mStepsDBHelper.readStepsEntries(getApplicationContext());
        circularstep.setProgress((float)mStepCountList.size());
        step.setText(String.valueOf(mStepCountList.size()));
        float distance = 0;

        if (person.size() != 0) {
            if (person.get(0).getSex().equals("man")) {
                distance = (float) ((int) mStepCountList.size() * 78) / (float) 100;
            }
            if (person.get(0).getSex().equals("woman")) {
                distance = (float) ((int)mStepCountList.size() * 70) / (float) 100;
            }
        }
        persent.setText(String.valueOf((int) distance));
        circuldistance.setProgress(distance);

        float time1 = (float) ((int) mStepCountList.size()* 100) / (float) 10000;
        circulartime.setProgress(time1);
        time.setText(String.valueOf((int) time1));
        if (person.size()!=0){
            burnedColori(time1);
            burndprogressBar.setProgress((int)burnedColori(time1));
            burncolori.setText(String.valueOf((int)burnedColori(time1)));
        }
    }

    public void first() {
        mStepsDBHelper = new StepsDBHelper(this);
        mStepCountList = mStepsDBHelper.readStepsEntries(getApplicationContext());
        circularstep.setProgress((float)mStepCountList.size());
        step.setText(String.valueOf(mStepCountList.size()));
        //      Toast.makeText(this, ""+mStepCountList.size(), Toast.LENGTH_SHORT).show();
        float distance = 0;

        if (person.size() != 0) {
            if (person.get(0).getSex().equals("man")) {
                distance = (float) ((int) mStepCountList.size() * 78) / (float) 100;
            }
            if (person.get(0).getSex().equals("woman")) {
                distance = (float) ((int)mStepCountList.size() * 70) / (float) 100;
            }
        }
        persent.setText(String.valueOf((int) distance));
        circuldistance.setProgress(distance);

        float time1 = (float) ((int) mStepCountList.size()* 100) / (float) 10000;
        circulartime.setProgress(time1);
        time.setText(String.valueOf((int) time1));
    }


    public void DialogForChangeStepts() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomePage.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle("تعداد قدم های کل را وارد کنید");
        final EditText text = new EditText(HomePage.this);
        text.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        text.setLayoutParams(lp);
        dialog.setView(text);



        dialog.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainstep.setText(text.getText().toString());
                dialog.cancel();
            }
        });
        dialog.setNegativeButton("لغو",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        dialog.show();
    }

    @Override
    protected void onRestart() {
   //     Toast.makeText(this, "hiiiiiiiiiii", Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit:
                startActivity(new Intent(HomePage.this,EditProfile.class));
                checkOpenDrawer();
                break;
            case R.id.setting:
                startActivity(new Intent(HomePage.this,Settings.class));
                checkOpenDrawer();
                break;
        }

        return true;
    }

    public void checkOpenDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START, true);
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                pickimage.setImageBitmap(selectedImage);
                SaveImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
               Toast.makeText(HomePage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(HomePage.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void getImageFromAlbum(){
        try{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 13);
        }catch(Exception exp){
            Log.i("Error",exp.toString());
        }
    }

    double  burnedColori(float time){
       int weight = Integer.parseInt(person.get(0).getWeight());
       double onehour=weight*25/7;
       double BurnedColori=time*onehour/60;
       return BurnedColori;
    }

    private void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved");
        myDir.mkdirs();
        String fname = "Image.jpg";
        File file = new File (myDir, fname);
        try {
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  Bitmap loadFromFile() {
        String root = Environment.getExternalStorageDirectory().toString();
        try {
            File f = new File(root + "/saved");
            if (!f.exists()) { return null; }
            Bitmap tmp = BitmapFactory.decodeFile(root + "/saved/Image.jpg");
            pickimage.setImageBitmap(tmp);
            return tmp;

        } catch (Exception e) {
            Toast.makeText(HomePage.this, "Ok nashod", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(HomePage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public static String getdate(){
        Calendar mCalendar = Calendar.getInstance();
        String todayDate =
                String.valueOf(mCalendar.get(Calendar.MONTH)) + "/" +
                        String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(mCalendar.get(Calendar.YEAR));
        return todayDate;
    }

    public static void calPoint(int newpoint,Context context){
        int po=Integer.parseInt(HomePage.point.getText().toString());
        po=po+newpoint;
        DailyIDataDBHelper dailyIDataDBHelper=new DailyIDataDBHelper(context);
        HomePage.point.setText(String.valueOf(po));
        PointModel pointModel=new PointModel();
        pointModel.setPoint(po);
        pointModel.setDate(getdate());
        dailyIDataDBHelper.InsertPoint(pointModel);
    }



}
