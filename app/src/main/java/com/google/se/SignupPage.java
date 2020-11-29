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
import com.android.volley.toolbox.HttpResponse;
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
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPage extends AppCompatActivity {

    EditText email, pass1, pass2;
    Button ok;
    ImageView google;
    TextView login;
    SignUpDBHelper signUpDBHelper;
    static String ID,Pass;
    String url = "http://www.healthcareassistantproject.ir/mySite/index.php";
    GoogleSignInClient mGoogleSignInClient;
    boolean be = false, bp1 = false, bp2 = false, bg=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        signUpDBHelper = new SignUpDBHelper(SignupPage.this);
        Init();
        CheckEmail();
        Passwords();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SignupPage.this, "" + bp1 + bp2 + be, Toast.LENGTH_SHORT).show();

                if (be == true && bp1 == true && bp2 == true) {
                    for (int i=0;i<MainActivity.peaple.size();i++){
                        if (email.getText().toString().equals(MainActivity.peaple.get(i).getId())){
                            // startActivity(new Intent(SignupPage.this,InformationPage.class));
                            bg=true;
                            //  finish();
                        }
                    }
                    if (bg==false){
                        ID=email.getText().toString().trim();
                        Pass=pass1.getText().toString().trim();

                        startActivity(new Intent(SignupPage.this,InformationPage.class));
                        finish();
                    }

                 if (bg==true){
                     Toast.makeText(SignupPage.this, "ایمیل قبلا ثبت شده است وارد شوید", Toast.LENGTH_SHORT).show();
                 }

                }
            }
        });
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignupPage.this, loginPage.class));
                finish();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                signIn();
            }
        });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            for (int i=0;i<MainActivity.peaple.size();i++){
                if (account.getEmail().toString().equals(MainActivity.peaple.get(i).getId())){
                   // startActivity(new Intent(SignupPage.this,InformationPage.class));
                    bg=true;
                  //  finish();
                }
            }
            if (bg==false){
               // Toast.makeText(this, "ایمیل ثبت نام نشده است", Toast.LENGTH_SHORT).show();
                ID=account.getEmail().toString();
                Pass=ID;
                Signup person=new Signup();
                person.setId(ID);
                person.setPassword(ID);
                signUpDBHelper.Insert(person);
                NameAndLastnamwFragment.namestr=account.getGivenName();
                NameAndLastnamwFragment.lastnamestr=account.getFamilyName();
                startActivity(new Intent(SignupPage.this,InformationPage.class));
                finish();
            }
            if (bg==true){
                Toast.makeText(this, "ایمیل ثبت  شده است وارد شوید", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, account.getEmail()+"", Toast.LENGTH_SHORT).show();

        } catch (ApiException e) {
            Toast.makeText(this, e.getMessage()+"اینترنت متصل نیست.", Toast.LENGTH_SHORT).show();

        }
    }


    public void Init() {
        email = findViewById(R.id.email);
        pass1 = findViewById(R.id.pass1);
        pass2 = findViewById(R.id.pass2);
        ok = findViewById(R.id.ok);
        google = findViewById(R.id.google);
        login = findViewById(R.id.login);
        email.requestFocus();
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
                        email.setError("این ایمیل قبلا ثبت نام شده است لطفا وارد شوید.");
                        be=false;
                    }
                }

            }
        });
    }

    public void Passwords() {
        pass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pass1.getText().length() < 8) {
                    pass1.setError("لطفا پسورد حداقل 8 کاراکتر وارد کنید");
                    bp1 = false;
                } else {
                    bp1 = true;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //   Toast.makeText(SignupPage.this, pass1.getText().toString(), Toast.LENGTH_SHORT).show();
                if (!pass1.getText().toString().equals(s.toString())) {
                    pass2.setError("پسورد ها مطابقت ندارند");
                    bp2 = false;
                } else {
                    bp2 = true;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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






    public void InsertData(final String name, final String email){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String NameHolder = name ;
                String EmailHolder = email ;

                List<BasicNameValuePair> nameValuePairs = new ArrayList<>();

                nameValuePairs.add(new BasicNameValuePair("Id", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("Password", EmailHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(url);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    org.apache.http.HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(SignupPage.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(name, email);
    }






}

