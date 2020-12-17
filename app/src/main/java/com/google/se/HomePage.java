package com.google.se;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends AppCompatActivity implements SensorEventListener,NavigationView.OnNavigationItemSelectedListener {


    TextView hi, Maincolori, date, showrate,useColori;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page1);
        signUpDBHelper = new SignUpDBHelper(this);
        person = signUpDBHelper.GetInf();
        init();

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
        Toast.makeText(this, "" + person.size(), Toast.LENGTH_SHORT).show();
        if (person.size() == 0) {
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
                    int p = Integer.parseInt(point.getText().toString());
                    p = p + 10;
                    point.setText(String.valueOf(p));

                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int addd = Integer.parseInt(water.getText().toString());
                    if (addd != 0) {
                        addd = addd - 1;
                        water.setText(String.valueOf(addd));
                        int p = Integer.parseInt(point.getText().toString());
                        p = p - 10;
                        point.setText(String.valueOf(p));
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
        first();
    }


    void init() {
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
        searchfood=findViewById(R.id.searchfood);
        waterlayout=findViewById(R.id.waterLayout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        pickimage=header.findViewById(R.id.pickImage);
        pickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromAlbum();
            }
        });
        circuldistance = findViewById(R.id.circuldistance);
        circularstep.setProgressMax(Integer.parseInt(mainstep.getText().toString()));
        useColori.setText(String.valueOf((int)FoodPage.currentColoritxt));
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
    protected void onResume() {
        super.onResume();
        Sensor countsteps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        useColori.setText(String.valueOf((int)FoodPage.currentColoritxt));
        sensorManager.registerListener(this, countsteps,
                SensorManager.SENSOR_DELAY_NORMAL);


        if (countsteps != null) {
            sensorManager.registerListener(this, countsteps, SensorManager.SENSOR_DELAY_UI);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

    @Override
    protected void onPause() {
        super.onPause();
  //      sensorManager.unregisterListener(this);
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
                    Toast.makeText(HomePage.this, "" + person.size(), Toast.LENGTH_SHORT).show();
                    person.add(model);
                    hi.setText(" سلام " + person.get(0).getName());
                    double c = calculatingColori(person.get(0).getSex());
                    Maincolori.setText(String.valueOf((int) c));
                    Toast.makeText(HomePage.this, "" + person.size(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomePage.this, error.getMessage(), Toast.LENGTH_LONG).show();
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


    @Override
    public void onSensorChanged(SensorEvent event) {
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
            if (Integer.parseInt(persent.getText().toString()) % 50 == 0) {
                int pget = Integer.valueOf(point.getText().toString());
                pget = pget + 1;
                point.setText(String.valueOf(pget));
            }

            float time1 = (float) ((int) mStepCountList.size()* 100) / (float) 10000;
            circulartime.setProgress(time1);
            time.setText(String.valueOf((int) time1));
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
        if (Integer.parseInt(persent.getText().toString()) % 50 == 0) {
            int pget = Integer.valueOf(point.getText().toString());
            pget = pget + 1;
            point.setText(String.valueOf(pget));
        }

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

}
