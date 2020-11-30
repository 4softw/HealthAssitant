package com.google.se;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends AppCompatActivity {

    TextView hi,Maincolori,date,point,water;
    String url = "http://healthcareassistantproject.ir/mySite/fetchwithid.php";
    String name;
    SignUpDBHelper signUpDBHelper;
    ImageView add,minus;
    static List<InformationModel> person = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page1);
        signUpDBHelper = new SignUpDBHelper(this);
        person = signUpDBHelper.GetInf();
        hi = findViewById(R.id.hi);
        Maincolori=findViewById(R.id.MainColori);
        date=findViewById(R.id.date);
        add=findViewById(R.id.add);
        minus=findViewById(R.id.minus);
        point=findViewById(R.id.point);
        water=findViewById(R.id.water);
        if (person.size() == 0) {
           GetFromHost(MainActivity.person.get(0).getId());
           person=signUpDBHelper.GetInf();
          }

        if (person.size()!=0){
            hi.setText(" سلام "+person.get(0).getName());
            double c=calculatingColori(person.get(0).getSex());
            Maincolori.setText(String.valueOf((int)c));

            PersianCalendar pc = new PersianCalendar();
            date.setText(pc.getPersianLongDate());

            int addd=Integer.parseInt(water.getText().toString());
            if (addd==0){
                minus.animate().alpha(0.5f);
            }

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int addd=Integer.parseInt(water.getText().toString());
                    addd++;
                    water.setText(String.valueOf(addd));
                    int p=Integer.parseInt(point.getText().toString());
                    p=p+10;
                    point.setText(String.valueOf(p));

                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int addd=Integer.parseInt(water.getText().toString());
                    if (addd!=0){
                        addd=addd-1;
                        water.setText(String.valueOf(addd));
                        int p=Integer.parseInt(point.getText().toString());
                        p=p-10;
                        point.setText(String.valueOf(p));
                    }
                }
            });



        }
    }


    public void GetFromHost(final String id)  {
        RequestQueue queue = Volley.newRequestQueue(HomePage.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SignUpDBHelper signUpDBHelper=new SignUpDBHelper(HomePage.this);
                try {
                    JSONArray array = new JSONArray(response);
                        InformationModel model=new InformationModel();
                        JSONObject product = array.getJSONObject(0);
                        model.setName(product.getString("name"));
                        model.setAge(product.getString("age"));
                        model.setHeight(product.getString("height"));
                        model.setWeight(product.getString("weight"));
                        model.setId(product.getString("id"));
                        model.setSex(product.getString("sex"));
                        model.setLastname(product.getString("lastname"));
                        signUpDBHelper.InsertInf(model);
                        Toast.makeText(HomePage.this, ""+person.size(), Toast.LENGTH_SHORT).show();
                        person.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomePage.this, error.getMessage(),Toast.LENGTH_LONG).show();
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



    double calculatingColori(String sex){
        double result=0;
        if (sex.equals("woman")){
            double part1=(Float.parseFloat(person.get(0).getWeight())*9.563)+655.1;
            double part2=(Float.parseFloat(person.get(0).getHeight())*1.850);
            double part3=(Float.parseFloat(person.get(0).getAge())*4.676 );
             result=part1+part2-part3;
        }

        if (sex.equals("man")){
            double part1=(Float.parseFloat(person.get(0).getWeight())*13.75 )+66.47 ;
            double part2=(Float.parseFloat(person.get(0).getHeight())*5.003 );
            double part3=(Float.parseFloat(person.get(0).getAge())*6.755  );
            result=part1+part2-part3;
        }
        return result;
    }





}
