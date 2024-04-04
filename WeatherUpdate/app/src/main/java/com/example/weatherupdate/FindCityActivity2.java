package com.example.weatherupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FindCityActivity2 extends AppCompatActivity {

    EditText city;
    Button select;
    String Text;
    String key="21b0d6f6c7efbe3d591b5f65dde24167";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_city2);

        city=(EditText) findViewById(R.id.c_name);
        select=(Button) findViewById(R.id.select);

        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Text=city.getText().toString();
                setTemp();
            }
        });
    }
    public void setTemp() {

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + Text + "&appid=" + key;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent=new Intent(FindCityActivity2.this,MainActivity.class);
                        intent.putExtra("tag",Text);
                        startActivity(intent);
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError)
                {
                    // Handle no connection error
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError)
                {
                    // Handle invalid input error
                    city.setText("");
                    Toast.makeText(getApplicationContext(), "Invalid City Name", Toast.LENGTH_SHORT).show();
                } else
                {
                    // Handle other errors
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }
}