package com.google.se;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    EditText name,lastname;
    RadioButton man,woman;
    TextView agevalue,heightvalue,weightvalue;
    Button ok;
    String sex;
    ProgressDialog progressBar;
    SignUpDBHelper signUpDBHelper;
    String url = "http://healthcareassistantproject.ir/mySite/edit.php";
    com.crystal.crystalrangeseekbar.widgets.BubbleThumbSeekbar age,weight,height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name=findViewById(R.id.editname);
        lastname=findViewById(R.id.editlastname);
        age=findViewById(R.id.editage);
        height=findViewById(R.id.editheight);
        weight=findViewById(R.id.editweight);
        man=findViewById(R.id.editman);
        woman=findViewById(R.id.editwoman);
        weightvalue=findViewById(R.id.weightvalue);
        signUpDBHelper=new SignUpDBHelper(this);
        agevalue=findViewById(R.id.agevalue);
        ok=findViewById(R.id.ok);
        heightvalue=findViewById(R.id.heightvalue);
        agevalue.setText(HomePage.person.get(0).getAge());
        heightvalue.setText(HomePage.person.get(0).getHeight());
        weightvalue.setText(HomePage.person.get(0).getWeight());
        progressBar=new ProgressDialog(EditProfile.this);
        progressBar.setMessage("لطفا صبز کنید...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        name.setText(HomePage.person.get(0).getName());
        lastname.setText(HomePage.person.get(0).getLastname());
        if (HomePage.person.get(0).getSex().equals("man")){
            man.setChecked(true);
            sex="man";
        }
        if (HomePage.person.get(0).getSex().equals("woman")){
            woman.setChecked(true);
            sex="woman";
        }
        height.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                heightvalue.setText(String.valueOf(value));
            }
        });

        age.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                agevalue.setText(String.valueOf(value));
            }
        });

        weight.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                weightvalue.setText(String.valueOf(value));
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToServer();
                ContentValues values=new ContentValues();
                values.put("Id", HomePage.person.get(0).getId());
                values.put("Name", name.getText().toString().trim());
                values.put("LastName", lastname.getText().toString().trim());
                values.put("Age", agevalue.getText().toString().trim());
                values.put("Weight", weightvalue.getText().toString());
                values.put("Height", heightvalue.getText().toString());
                values.put("Sex", sex);
                signUpDBHelper.update(HomePage.person.get(0).getId(),values);
                startActivity(new Intent( EditProfile.this,MainActivity.class));
                finish();

            }
        });


    }



    private void AddToServer()  {
       progressBar.show();
        RequestQueue queue = Volley.newRequestQueue(EditProfile.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                progressBar.dismiss();
         //         Toast.makeText(EditProfile.this, "***********************" + response, Toast.LENGTH_LONG).show();

     //           Toast.makeText(getContext(), "Go To Home", Toast.LENGTH_SHORT).show();

                Log.i("My success", "" + response);
            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
               progressBar.dismiss();
                Toast.makeText(EditProfile.this, "اینترنت متصل نیست" + error, Toast.LENGTH_LONG).show();

                Log.i("My error", "" + error);


            }

        }) {

            @Override

            protected Map<String, String> getParams() {


                Map<String, String> map = new HashMap<String, String>();

                map.put("id", HomePage.person.get(0).getId());
                map.put("name", name.getText().toString().trim());
                map.put("lastname", lastname.getText().toString().trim());
                map.put("age", agevalue.getText().toString().trim());
                map.put("weight", weightvalue.getText().toString());
                map.put("height", heightvalue.getText().toString());
                map.put("sex", sex);

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
