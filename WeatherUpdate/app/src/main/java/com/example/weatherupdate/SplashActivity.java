package com.example.weatherupdate;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {

    private TextView load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        load=(TextView) findViewById(R.id.load);
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startapp();
            }
        });
        th.start();
    }
    public void doWork()
    {
        for(int p=1;p<=100;p=p+1) {
            try {
                Thread.sleep(20);
                final int pg = p;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String d = "Loading-" + pg + '%';
                        load.setText(d);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void startapp()
    {
        Intent intent=new Intent(SplashActivity.this,FindCityActivity2.class);
        startActivity(intent);
        finish();
    }
}