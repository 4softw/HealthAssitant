package com.google.se;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity {

    TextView hi;
    String url = "http://healthcareassistantproject.ir/mySite/fetchwithid.php";
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        hi = findViewById(R.id.hi);
        hi.setText("name is "+name);

    }


    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        //    Toast.makeText(turbine.this, "تبریک", Toast.LENGTH_LONG).show();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                InformationModel model=new InformationModel();
                                JSONObject product = array.getJSONObject(i);
                                model.setName(product.getString("name"));
                                name=product.getString("name");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //         Toast.makeText(turbine.this, "خطای اتصال به شبکه", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", MainActivity.person.get(0).getId());
                Toast.makeText(HomePage.this, MainActivity.person.get(0).getId(), Toast.LENGTH_SHORT).show();
                return params;
            }

        };
    }
}
