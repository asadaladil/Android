package com.example.weatherupdate;

import static java.lang.Math.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramSocket;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private EditText edit;
    private Button bt;
    String url;
    private TextView Temp,City,max,min,humidity,wind,sunrise,sunset,day,date;
    private TextView condition,pressure;
    private LinearLayout ll;
    private ImageView iv;
    String key="21b0d6f6c7efbe3d591b5f65dde24167";
    String weekday;

    public String Temperature(String k)
    {
        double d=Double.parseDouble(k)-273.15;
        int a=(int)round(d);
        return String.valueOf(a)+"°C";
    }
    public String TimeFormat(String k,String x)
    {
        // Assuming you've converted your strings to longs as shown earlier
        long timestamp = Long.parseLong(k);
        long timezoneOffset = Long.parseLong(x); // For example, UTC+6 hours
        // Convert Unix timestamp to an Instant
        Instant instant = Instant.ofEpochSecond(timestamp);
        // Convert Instant to LocalDateTime at the UTC timezone
        LocalDateTime dateTimeUtc = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        // Apply the timezone offset
        LocalDateTime dateTimeWithOffset = dateTimeUtc.plusSeconds(timezoneOffset);
        // Optionally, format the LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy   HH:mm:ss");
        String formattedDateWithOffset = dateTimeWithOffset.format(formatter);
        weekday = dateTimeWithOffset.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

        return formattedDateWithOffset;
    }
    public String Hour(String k,String x)
    {
        // Assuming you've converted your strings to longs as shown earlier
        long timestamp = Long.parseLong(k);
        long timezoneOffset = Long.parseLong(x); // For example, UTC+6 hours
        // Convert Unix timestamp to an Instant
        Instant instant = Instant.ofEpochSecond(timestamp);
        // Convert Instant to LocalDateTime at the UTC timezone
        LocalDateTime dateTimeUtc = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        // Apply the timezone offset
        LocalDateTime dateTimeWithOffset = dateTimeUtc.plusSeconds(timezoneOffset);
        // Optionally, format the LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDateWithOffset = dateTimeWithOffset.format(formatter);

        return formattedDateWithOffset;
    }
    public String Pressure(String k)
    {
        Double d= Double.parseDouble(k);
        d*=0.75;
        return String.valueOf(d);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit=(EditText) findViewById(R.id.edit);
        bt=(Button) findViewById(R.id.search);
        Temp=(TextView)findViewById(R.id.temp);
        City=(TextView)findViewById(R.id.city);
        max=(TextView)findViewById(R.id.max);
        min=(TextView)findViewById(R.id.min);
        humidity=(TextView) findViewById(R.id.humidity);
        sunrise=(TextView) findViewById(R.id.sunrise);
        sunset=(TextView) findViewById(R.id.sunset);
        ll=(LinearLayout) findViewById(R.id.layout);
        iv=(ImageView) findViewById(R.id.view);
        day=(TextView) findViewById(R.id.day);
        date=(TextView) findViewById(R.id.date);
        wind=(TextView)findViewById(R.id.air);
        condition=(TextView)findViewById(R.id.condition);
        pressure=(TextView)findViewById(R.id.pressure);
        Bundle bundle=getIntent().getExtras();
        String val=bundle.getString("tag");
        edit.setText(val);
        setTemp();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setTemp();
            }
        });
    }
    public void Weather(String k)
    {
        condition.setText(k);
        if(k.equals("Rain"))
        {
            ll.setBackgroundResource(R.drawable.raining_back2);
            iv.setImageDrawable(getDrawable(R.drawable.raining));
        }
        else if(k.equals("Snow"))
        {
            ll.setBackgroundResource(R.drawable.snowy_back);
            iv.setImageDrawable(getDrawable(R.drawable.snowy));
        }
        else if(k.equals("Clear")||k.equals("Sunny")||k.equals("Haze")||k.equals("Partly sunny"))
        {
            ll.setBackgroundResource(R.drawable.sunny_back);
            iv.setImageDrawable(getDrawable(R.drawable.sunny));
        }
        else if(k.equals("Clouds"))
        {
            ll.setBackgroundResource(R.drawable.rain_cloudy_back);
            iv.setImageDrawable(getDrawable(R.drawable.cloudy));
        }
        else if(k.equals("Storm"))
        {
            ll.setBackgroundResource(R.drawable.stormy_back);
            iv.setImageDrawable(getDrawable(R.drawable.storm));
        }
    }
    public void setTemp()
    {

        String city=edit.getText().toString().toUpperCase();
        url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+key;
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {

                            String set=response.getJSONObject("main").getString("temp");
                            String max_temp=response.getJSONObject("main").getString("temp_max");
                            String min_temp=response.getJSONObject("main").getString("temp_min");
                            String Humi=response.getJSONObject("main").getString("humidity");
                            String Wind=response.getJSONObject("wind").getString("speed");
                            String Date=response.getString("dt");
                            String zone=response.getString("timezone");
                            String rise=response.getJSONObject("sys").getString("sunrise");
                            String Set=response.getJSONObject("sys").getString("sunset");
                            String weather=response.getJSONArray("weather").getJSONObject(0).getString("main");
                            String press=response.getJSONObject("main").getString("pressure");
                            Weather(weather);
                            pressure.setText(Pressure(press)+" mmHg");
                            City.setText(city);
                            sunrise.setText(Hour(rise,zone));
                            sunset.setText(Hour(Set,zone));
                            Date=TimeFormat(Date,zone);
                            date.setText(Date);
                            day.setText(weekday);
                            wind.setText(Wind+"m/s");
                            humidity.setText(Humi+'%');
                            Temp.setText(Temperature(set));
                            max.setText("Max Temp: "+Temperature(max_temp));
                            min.setText("Min temp: "+Temperature(min_temp));
                            iv.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,"Error Occur",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Temp.setText("");
                valueshow();
                Toast.makeText(MainActivity.this,"Invalid City",Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }
    public void valueshow()
    {
        pressure.setText("");
        City.setText("");
        sunrise.setText("");
        sunset.setText("");
        date.setText("");
        day.setText("");
        wind.setText("");
        humidity.setText("");
        Temp.setText("");
        max.setText("Max Temp: ");
        min.setText("Min temp: ");
        condition.setText("");
        iv.setVisibility(View.INVISIBLE);
    }
}