package com.google.se;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginPage extends AppCompatActivity {

    EditText email, pass1;
    Button ok;
    TextView signup;
    static String ID;
    ImageView google;
    SignUpDBHelper signUpDBHelper;
    String url = "http://www.healthcareassistantproject.ir/mySite/login.php";
    boolean be = false, bp1 = false, bp2 = false;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Init();
        CheckEmail();
        email.requestFocus();
        signUpDBHelper=new SignUpDBHelper(loginPage.this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AddToServer();
                    if (be==true){
                        Signup signup=new Signup();
                        signup.setId(email.getText().toString().trim());
                        signup.setPassword(pass1.getText().toString().trim());
                        MainActivity.person.add(signup);
                        signUpDBHelper.Insert(signup);
                        startActivity(new Intent(loginPage.this,HomePage.class));
                        finish();
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                ID=email.getText().toString().trim();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginPage.this,SignupPage.class));
                finish();
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Login();
            }
        });
    }


    public void Init() {
        email = findViewById(R.id.email);
        pass1 = findViewById(R.id.pass1);
        ok = findViewById(R.id.ok);
        signup = findViewById(R.id.signup);
        google=findViewById(R.id.google);
    }



    private void Login() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 2) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //AddToServer1(account.getEmail(),account.getEmail());

            for (int i=0;i<MainActivity.peaple.size();i++){
                if (account.getEmail().toString().equals(MainActivity.peaple.get(i).getId())){
                    be=true;
                    Signup signup=new Signup();
                    signup.setId(account.getEmail());
                    signup.setPassword(account.getEmail());
                    MainActivity.person.add(signup);
                    signUpDBHelper.Insert(signup);
                    startActivity(new Intent(loginPage.this,HomePage.class));
                    finish();
                }
            }
            if (be==false){
                Toast.makeText(this, "ایمیل ثبت نام نشده است", Toast.LENGTH_SHORT).show();
            }

        } catch (ApiException e) {
            Toast.makeText(this, "اینترنت متصل نیست", Toast.LENGTH_SHORT).show();

        }
    }

    public void CheckEmail() {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    boolean check = isEmailValid(s.toString());
                    if (check == true) {
                        be = true;
                    } else {
                        email.setError("ایمیل صحیح نیست.");
                        be = false;
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                for (int i=0;i<MainActivity.peaple.size();i++){
                    if (email.getText().toString().equals(MainActivity.peaple.get(i).getId())){
                     //   email.setError("این ایمیل قبلا ثبت نام شده است لطفا وارد شوید.");
                        be=true;
                    }
                    if (be==false){
                        email.setError("شما با این ایمیل ثبت نام نکرده اید لطفا ابتدا ثبت نام کنید");
                    }
                }

            }
        });
    }



    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    private void AddToServer() throws UnsupportedEncodingException {
        RequestQueue queue = Volley.newRequestQueue(loginPage.this);
        StringRequest request = new StringRequest(Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
//                loginDBHelper loginDBHelper=new loginDBHelper(getApplicationContext());


                for (int i=0;i<MainActivity.peaple.size();i++){
                    if (email.getText().toString().equals(MainActivity.peaple.get(i).getId())){
                        Signup signup=new Signup();
                        signup.setId(email.getText().toString().trim());
                        signup.setPassword(pass1.getText().toString().trim());
                        MainActivity.person.add(signup);
                        be=true;
              //          startActivity(new Intent(loginPage.this,HomePage.class));
                  //      finish();
                    }
                }
                if (be==false){
                    Toast.makeText(getApplicationContext(), "ایمیل یا پسورد اشتباه است", Toast.LENGTH_SHORT).show();
                }



            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {


                Toast.makeText(loginPage.this, "my error :" + error, Toast.LENGTH_LONG).show();

                Log.i("My error", "" + error);


            }

        }) {

            @Override

            protected Map<String, String> getParams() {


                Map<String, String> map = new HashMap<String, String>();

                map.put("Id", email.getText().toString().trim());

                map.put("Password", pass1.getText().toString().trim());

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

