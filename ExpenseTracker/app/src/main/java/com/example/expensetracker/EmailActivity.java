package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EmailActivity extends AppCompatActivity {

    private Button cont;
    private EditText email,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email);

        cont=(Button) findViewById(R.id.email_select);
        email=(EditText) findViewById(R.id.first_email);
        year=(EditText)findViewById(R.id.year);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String emaill=email.getText().toString();
                String Year=year.getText().toString();
                if(emaill.isEmpty())
                {
                    Toast.makeText(EmailActivity.this,"Please Enter Valid Email",Toast.LENGTH_SHORT).show();
                }
                else if(Year.isEmpty())
                {
                    Toast.makeText(EmailActivity.this,"Enter Year",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        FileOutputStream op=openFileOutput("email.txt", Context.MODE_PRIVATE),op2;
                        op.write(emaill.getBytes());
                        op.write("\n".getBytes());
                        op.close();
                        op2=openFileOutput("year.txt",Context.MODE_PRIVATE);
                        op2.write(Year.getBytes());
                        op2.write("\n".getBytes());
                        op2.close();
                        Intent intent=new Intent(EmailActivity.this, MainActivity.class);
                        Toast.makeText(EmailActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();
                    } catch (FileNotFoundException e) {

                        Toast.makeText(EmailActivity.this,"File is not Found",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(EmailActivity.this,"Fail to Store Data",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}