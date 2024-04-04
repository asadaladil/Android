package com.example.myfirstproject;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wb=(WebView) findViewById(R.id.web);
        WebSettings wvs=wb.getSettings();
        wvs.setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl("https://www.ruet.ac.bd");
    }
    public void onBackPressed()
    {
        if(wb.canGoBack())
        {
            wb.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }
}


