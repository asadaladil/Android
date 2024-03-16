package com.example.myfirstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextView Name,Name2;
    private Button bt;
    private int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name= (TextView) findViewById(R.id.text1);
        Name.setText("welcome Adil");
        bt=(Button) findViewById(R.id.button1);
        Name2=(TextView)findViewById(R.id.text2);

//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                c++;
//                Name2.setText("Login Button is clicked "+c+" times");
//            }
//        });
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button1)
        {
            c++;
//            Name2.setText("Login Button is clicked "+c+" times");
            Toast.makeText(MainActivity.this,"Login Button is clicked "+c+" times",Toast.LENGTH_SHORT).show();
        }
    }
}

