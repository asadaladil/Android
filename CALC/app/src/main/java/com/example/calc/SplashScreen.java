package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar pb;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        pb=(ProgressBar)findViewById(R.id.pro);
        text=(TextView) findViewById(R.id.welcome);
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
        for(int p=20;p<=100;p=p+20)
        {
            try {
                Thread.sleep(1000);
                final int pg =p;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setProgress(pg);
                        String d = "Loading-" + pg + '%';
                        text.setText(d);
                    }
                });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
    public void startapp()
    {
        Intent intent=new Intent(SplashScreen.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}