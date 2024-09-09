package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Personal_infoActivity extends AppCompatActivity {

    ArrayList<String> arr=new ArrayList<>();
    private EditText user,year,email,cur_pass,new_pass,sure_pass;
    private Button cng_pass,sub,show_pass,Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_info);

        user=(EditText) findViewById(R.id.username);
        year=(EditText) findViewById(R.id.expenditure);
        email=(EditText) findViewById(R.id.Email);
        cur_pass=(EditText) findViewById(R.id.pass1);
        new_pass=(EditText) findViewById(R.id.pass2);
        sure_pass=(EditText) findViewById(R.id.pass3);

        cng_pass=(Button) findViewById(R.id.cng_pass);
        sub=(Button) findViewById(R.id.submit);
        show_pass=(Button) findViewById(R.id.show_pass);
        Back=(Button) findViewById(R.id.back);


        file_khulo("save_info.txt");
        user.setText(arr.get(0));
        cur_pass.setText(arr.get(1));
        arr.clear();
        file_khulo("email.txt");
        email.setText(arr.get(0));
        arr.clear();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            year.setText(bundle.getString("year"));
        }
        show_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_pass.getText().toString().equals("Show Password"))
                {
                    cur_pass.setVisibility(View.VISIBLE);
                    cur_pass.setInputType(InputType.TYPE_CLASS_TEXT);
                    show_pass.setText("Block Password");
                }
                else
                {

                    cur_pass.setVisibility(View.INVISIBLE);
                    show_pass.setText("Show Password");
                }

            }
        });
        cng_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cng_pass.getText().toString().equals("Change Password"))
                {
                    cng_pass.setText("Same Password");
                    new_pass.setVisibility(View.VISIBLE);
                    sure_pass.setVisibility(View.VISIBLE);
                }
                else
                {
                    cng_pass.setText("Change Password");
                    new_pass.setVisibility(View.INVISIBLE);
                    sure_pass.setVisibility(View.INVISIBLE);
                }

            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cng_pass.getText().toString().equals("Same Password"))
                {
                    String pass1=new_pass.getText().toString();
                    String pass2=sure_pass.getText().toString();
                    if(pass2.isEmpty())
                    {
                        Toast.makeText(Personal_infoActivity.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                    }
                    else if(pass2.equals(pass1))
                    {
                        cur_pass.setText(pass1);
                        save_koro();
                    }
                    else
                    {
                        Toast.makeText(Personal_infoActivity.this,"Password not Matched",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    save_koro();
                }

            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_page();
            }
        });

    }
    public void file_khulo(String k)
    {
        try {
            FileInputStream ip=openFileInput(k);
            InputStreamReader is=new InputStreamReader(ip);
            BufferedReader br=new BufferedReader(is);
            String line;
            while((line=br.readLine())!=null)
            {
                arr.add(line);
            }
            ip.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(Personal_infoActivity.this,"file nai",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(Personal_infoActivity.this,"kichu hoeche",Toast.LENGTH_SHORT).show();
        }
    }
    public void next_page()
    {
        Intent intent=new Intent(Personal_infoActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void save_koro()
    {
        try {
            FileOutputStream os=openFileOutput("save_info.txt", Context.MODE_PRIVATE);
            os.write("".getBytes());
            os.write(user.getText().toString().getBytes());
            os.write("\n".getBytes());
            os.write(cur_pass.getText().toString().getBytes());
            os.write("\n".getBytes());
            os.close();
            os=openFileOutput("temp_info.txt", Context.MODE_PRIVATE);
            os.write("".getBytes());
            os.write(user.getText().toString().getBytes());
            os.write("\n".getBytes());
            os.write(cur_pass.getText().toString().getBytes());
            os.write("\n".getBytes());
            os.close();
            os=openFileOutput("email.txt", Context.MODE_PRIVATE);
            os.write("".getBytes());
            os.write(email.getText().toString().getBytes());
            os.close();
            os=openFileOutput("year.txt", Context.MODE_PRIVATE);
            os.write("".getBytes());
            os.write(year.getText().toString().getBytes());
            os.close();
            Toast.makeText(Personal_infoActivity.this,"Value Updated Successfully",Toast.LENGTH_SHORT).show();
            next_page();
        } catch (IOException e) {
            Toast.makeText(Personal_infoActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }

}