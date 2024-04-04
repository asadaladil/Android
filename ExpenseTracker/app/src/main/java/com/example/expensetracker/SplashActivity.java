package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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
                Thread.sleep(25);
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
        Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}