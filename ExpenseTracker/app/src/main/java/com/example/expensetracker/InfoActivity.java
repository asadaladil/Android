package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button cost, income, ok,Back;
    private TextView sec, amt, cur;
    private EditText secc, amtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);

        cost = (Button) findViewById(R.id.tap_cost);
        income = (Button) findViewById(R.id.tap_income);
        ok = (Button) findViewById(R.id.add_money);
        Back=(Button)findViewById(R.id.back);

        sec = (TextView) findViewById(R.id.section_text);
        amt = (TextView) findViewById(R.id.amount_text);
        cur = (TextView) findViewById(R.id.currency);

        secc = (EditText) findViewById(R.id.section);
        amtt = (EditText) findViewById(R.id.value);
        kichu();
        cost.setOnClickListener(this);
        income.setOnClickListener(this);
        Back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        kichu_na();
        if (v.getId() == R.id.tap_cost) {
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String a=secc.getText().toString(),b=amtt.getText().toString();
                    if(!a.isEmpty()&&!b.isEmpty())
                    {
                        Intent intent=new Intent(InfoActivity.this,MainActivity.class);
                        intent.putExtra("position","cost");
                        intent.putExtra("section",a);
                        intent.putExtra("amount",b);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(InfoActivity.this,"Complete the boxes",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else if (v.getId() == R.id.tap_income) {
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String a=secc.getText().toString(),b=amtt.getText().toString();
                    if(!a.isEmpty()&&!b.isEmpty())
                    {
                        Intent intent=new Intent(InfoActivity.this,MainActivity.class);
                        intent.putExtra("position","income");
                        intent.putExtra("section",a);
                        intent.putExtra("amount",b);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(InfoActivity.this,"Complete the boxes",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if(v.getId()==R.id.back)
        {
            kichu();
            Intent intent=new Intent(InfoActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void kichu_na()
    {
        sec.setVisibility(View.VISIBLE);
        amt.setVisibility(View.VISIBLE);
        cur.setVisibility(View.VISIBLE);
        secc.setVisibility(View.VISIBLE);
        amtt.setVisibility(View.VISIBLE);
        ok.setVisibility(View.VISIBLE);
    }
    public void kichu()
    {
        sec.setVisibility(View.INVISIBLE);
        amt.setVisibility(View.INVISIBLE);
        cur.setVisibility(View.INVISIBLE);
        secc.setVisibility(View.INVISIBLE);
        amtt.setVisibility(View.INVISIBLE);
        ok.setVisibility(View.INVISIBLE);
    }
}