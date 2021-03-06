package com.biijay.myweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;

    private RequestQueue requestQueue;

    //https://api.openweathermap.org/data/2.5/weather?q=Paris&appid=8f7f459f0fbe6c48ca5e89b8e90e6061

    String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=8f7f459f0fbe6c48ca5e89b8e90e6061";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        city = findViewById(R.id.getCity);
        result = findViewById(R.id.result);

        requestQueue = MySingleton.getInstance(this).getmRequestQueue();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myURL = baseURL + city.getText().toString() + API;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("JSON", "JSON: "+ jsonObject);

                        try {
                            String info = jsonObject.getString("weather");
                            Log.i("INFO", "INFO: "+ info);

                            JSONArray ar = new JSONArray(info);

                            for (int i = 0;i<ar.length();i++){
                                JSONObject parOj = ar.getJSONObject(i);

                                String myWeather = parOj.getString("main");
                                result.setText(myWeather);

                                Log.i("ID", "ID " + parOj.getString("id"));
                                Log.i("MAIN", "MAIN: " + parOj.getString("main"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", "Something went wrong" + error);

                    }
                });

                requestQueue.add(jsonObjectRequest);

            }
        });




    }
}
