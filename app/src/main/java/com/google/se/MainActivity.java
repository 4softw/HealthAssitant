package com.google.se;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SignUpDBHelper signUpDBHelper;
    String url = "http://healthcareassistantproject.ir/mySite/fetch.php";
    static List<Signup> peaple=new ArrayList<>();
    static List<Signup> person=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpDBHelper=new SignUpDBHelper(this);
        person=signUpDBHelper.GetAll();
        loadProducts();
        Toast.makeText(this, ""+peaple.size(), Toast.LENGTH_SHORT).show();
        if (person.size()==0){
            startActivity(new Intent(MainActivity.this,StartActivity.class));
        }
        else {
            startActivity(new Intent(MainActivity.this,HomePage.class));
        }

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
                                Toast.makeText(MainActivity.this, ""+peaple.size(), Toast.LENGTH_SHORT).show();
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



}
