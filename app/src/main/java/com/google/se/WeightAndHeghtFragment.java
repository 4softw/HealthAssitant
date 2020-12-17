package com.google.se;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WeightAndHeghtFragment extends android.support.v4.app.Fragment {

    com.crystal.crystalrangeseekbar.widgets.BubbleThumbSeekbar weight, height;
    static String wstr, hstr;
    Button ok;
    TextView wmin, wmax, hmin, hmax;
    SignUpDBHelper signUpDBHelper;
    ProgressDialog progressBar;
    String url = "http://healthcareassistantproject.ir/mySite/inf.php";
    String urlSignup = "http://www.healthcareassistantproject.ir/mySite/index.php";
    boolean wb = false, hb = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_information_page3, container, false);
        weight = view.findViewById(R.id.weight);
        height = view.findViewById(R.id.height);
        wmin = view.findViewById(R.id.wmin);
        wmax = view.findViewById(R.id.wmax);
        hmin = view.findViewById(R.id.hmin);
        hmax = view.findViewById(R.id.hmax);
        ok = view.findViewById(R.id.ok);
        signUpDBHelper = new SignUpDBHelper(getContext());
        progressBar=new ProgressDialog(getContext());
        progressBar.setCancelable(false);


        progressBar.setMessage("لطفا صبر کنید ...");

        weight.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                wmin.setText(String.valueOf(value));
                wstr = String.valueOf(value);
            }
        });

        height.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                hmin.setText(String.valueOf(value));
                hstr = String.valueOf(value);
            }
        });



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NameAndLastnamwFragment.namestr.equals("") && !NameAndLastnamwFragment.lastnamestr.equals("")
                        && !GenderFragment.gender.equals("") && !wstr.equals("") && !hstr.equals("") && !AgeFragment.agestr.equals("")) {
                    Toast.makeText(getContext(), SignupPage.ID + NameAndLastnamwFragment.namestr + NameAndLastnamwFragment.lastnamestr
                            + GenderFragment.gender + hstr + wstr + AgeFragment.agestr, Toast.LENGTH_SHORT).show();

                    InformationModel person = new InformationModel();
                    person.setId(SignupPage.ID);
                    person.setLastname(NameAndLastnamwFragment.lastnamestr);
                    person.setName(NameAndLastnamwFragment.namestr);
                    person.setWeight(wstr);
                    person.setHeight(hstr);
                    person.setSex(GenderFragment.gender);
                    person.setAge(AgeFragment.agestr);
                        AddToServer();
                        AddToServerSignup(SignupPage.ID,SignupPage.Pass);
                    signUpDBHelper.InsertInf(person);
                    Signup signup=new Signup();
                    signup.setId(SignupPage.ID);
                    signup.setPassword(SignupPage.Pass);
                    MainActivity.person.add(signup);
                    startActivity(new Intent(getContext(),HomePage.class));
                }

                else {
                    Toast.makeText(getContext(), "اطلاعات خود را کامل کنید...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }


    private void AddToServer()  {
        progressBar.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                progressBar.dismiss();
              ///  Toast.makeText(getContext(), "***********************" + response, Toast.LENGTH_LONG).show();



                Log.i("My success", "" + response);
            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(getContext(), "اینترنت متصل نیست" + error, Toast.LENGTH_LONG).show();

                Log.i("My error", "" + error);


            }

        }) {

            @Override

            protected Map<String, String> getParams() {


                Map<String, String> map = new HashMap<String, String>();

                map.put("id", SignupPage.ID);
                map.put("name", NameAndLastnamwFragment.namestr);
                map.put("lastname", NameAndLastnamwFragment.lastnamestr);
                map.put("age", AgeFragment.agestr);
                map.put("weight", wstr);
                map.put("height", hstr);
                map.put("sex", GenderFragment.gender);

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

    public void AddToServerSignup(final String id, final String pass)  {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, urlSignup, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                SignUpDBHelper signUpDBHelper=new SignUpDBHelper(getContext());


                //    Toast.makeText(SignupPage.this, "***********************" + response, Toast.LENGTH_LONG).show();

                Log.i("My success", "" + response);

                Signup signup=new Signup();
                signup.setPassword(pass);
                signup.setId(id);
                signUpDBHelper.Insert(signup);
            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getContext(), "اینترنت شما متصل نیست..." ,Toast.LENGTH_LONG).show();

                Log.i("My error", "" + error);


            }

        }) {

            @Override

            protected Map<String, String> getParams() {


                Map<String, String> map = new HashMap<String, String>();

                map.put("Id", id);

                map.put("Password", pass);

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
}
