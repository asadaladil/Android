package com.example.expensetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout login;
    EditText user_name,pass_word,Email;
    Button cancel,submit,OTP;
    TextView click;
    String User,Pass;
    private AlertDialog.Builder alert;
    ArrayList<String>sb=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        login=(LinearLayout)findViewById(R.id.login);
        login.setVisibility(View.INVISIBLE);
        user_name=(EditText)findViewById(R.id.username);
        pass_word=(EditText)findViewById(R.id.pass);
        cancel=(Button)findViewById(R.id.cancel);
        submit=(Button)findViewById(R.id.submit);
        click=(TextView)findViewById(R.id.recover);
        OTP=(Button)findViewById(R.id.otp);
        Email=(EditText)findViewById(R.id.email);
        try {
//            FileOutputStream os=openFileOutput("temp_info.txt",Context.MODE_PRIVATE);
//            os.write("".getBytes());
//            FileOutputStream os1=openFileOutput("save_info.txt",Context.MODE_PRIVATE);
//            os1.write("".getBytes());
//            FileOutputStream os2=openFileOutput("email.txt",Context.MODE_PRIVATE);
//            os2.write("".getBytes());
            FileInputStream ip=openFileInput("temp_info.txt");
            InputStreamReader is=new InputStreamReader(ip);
            BufferedReader br=new BufferedReader(is);
            String line;
            while((line=br.readLine())!=null)
            {
                sb.add(line);
            }
            ip.close();
            if(!sb.isEmpty())
            {
                next_page();
            }
            else
            {
                login.setVisibility(View.VISIBLE);
                submit.setOnClickListener(this);
                cancel.setOnClickListener(this);
                click.setOnClickListener(this);
            }
        } catch (IOException e) {
            //Toast.makeText(LoginActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
            FileOutputStream os= null;
            try {
                os = openFileOutput("temp_info.txt", Context.MODE_PRIVATE);
                os.write("".getBytes());
                FileOutputStream os1=openFileOutput("save_info.txt",Context.MODE_PRIVATE);
                os1.write("".getBytes());
                FileOutputStream os2=openFileOutput("email.txt",Context.MODE_PRIVATE);
                os2.write("".getBytes());
                os2=openFileOutput("year.txt",Context.MODE_PRIVATE);
                os2.write("".getBytes());
                os2=openFileOutput("income_saves.txt",Context.MODE_PRIVATE);
                os2.write("".getBytes());
                os2=openFileOutput("cost_saves.txt",Context.MODE_PRIVATE);
                os2.write("".getBytes());
                os2=openFileOutput("monthly_income.txt",Context.MODE_PRIVATE);
                os2.write("".getBytes());
                os2=openFileOutput("monthly_cost.txt",Context.MODE_PRIVATE);
                os2.write("".getBytes());
                somossa_tai_ekhane();
            } catch (IOException ex) {
                Toast.makeText(LoginActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void next_page()
    {

        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
        finish();
    }
    public void somossa_tai_ekhane()
    {
        FileInputStream ip= null;
        try {
            ip = openFileInput("temp_info.txt");
            InputStreamReader is=new InputStreamReader(ip);
            BufferedReader br=new BufferedReader(is);
            String line;
            while((line=br.readLine())!=null)
            {
                sb.add(line);
            }
            ip.close();
            if(!sb.isEmpty())
            {
                next_page();
            }
            else
            {
                login.setVisibility(View.VISIBLE);
                submit.setOnClickListener(this);
                cancel.setOnClickListener(this);
                click.setOnClickListener(this);
            }
        } catch (IOException e) {
            Toast.makeText(LoginActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onClick(View v)
    {
        User = user_name.getText().toString();
        Pass = pass_word.getText().toString();
        if (v.getId() == R.id.submit)
        {
            try {
                FileInputStream ip = openFileInput("save_info.txt");
                InputStreamReader is = new InputStreamReader(ip);
                BufferedReader br = new BufferedReader(is);
                String line;
                ArrayList<String> setto = new ArrayList<>();
                while ((line = br.readLine()) != null)
                {
                    setto.add(line);
                }
                if (User.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Username is Empty", Toast.LENGTH_SHORT).show();
                }
                else if (Pass.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Password is Empty", Toast.LENGTH_SHORT).show();
                }
                else if (setto.isEmpty())
                {
                    try {
                        FileOutputStream op1 = openFileOutput("save_info.txt", Context.MODE_PRIVATE);
                        FileOutputStream op2 = openFileOutput("temp_info.txt", Context.MODE_PRIVATE);
                        op1.write(User.getBytes());
                        op1.write("\n".getBytes());
                        op1.write(Pass.getBytes());
                        op1.write("\n".getBytes());
                        op2.write(User.getBytes());
                        op2.write("\n".getBytes());
                        op2.write(Pass.getBytes());
                        op2.write("\n".getBytes());
                        op1.close();
                        op2.close();
                        Intent intent = new Intent(LoginActivity.this, EmailActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(LoginActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(LoginActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                    ip.close();
                }
                else {
                    String get_user = setto.get(0);
                    String get_pass = setto.get(1);
                    if (get_user.equals(User) && get_pass.equals(Pass)) {
                        FileOutputStream op2 = openFileOutput("temp_info.txt", Context.MODE_PRIVATE);
                        op2.write(get_user.getBytes());
                        op2.write(get_pass.getBytes());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        alert = new AlertDialog.Builder(LoginActivity.this);
                        alert.setTitle("Error");
                        alert.setMessage("Invalid Username or Password");
                        alert.setCancelable(false);
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog ad = alert.create();
                        ad.show();
                    }
                }
            }
            catch (IOException e)
            {
                Toast.makeText(LoginActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()==R.id.cancel)
        {
            user_name.setText("");
            pass_word.setText("");
        }
        else if(v.getId()==R.id.recover)
        {
            try {
                FileInputStream ip=openFileInput("email.txt");
                InputStreamReader is=new InputStreamReader(ip);
                BufferedReader br=new BufferedReader(is);
                String line;
                ArrayList<String>sb=new ArrayList<>();
                while((line=br.readLine())!=null)
                {
                    sb.add(line);
                }
                br.close();
                if(sb.size()!=0)
                {
                    Email.setText("");
                    Email.setHint("Enter Your Email");
                    Email.setInputType(InputType.TYPE_CLASS_TEXT);
                    OTP.setText("Send OTP");
                    Email.setVisibility(View.VISIBLE);
                    OTP.setVisibility(View.VISIBLE);
                    OTP.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            String email=sb.get(0);
                            String get_email=Email.getText().toString();
                            if(email.equals(get_email))
                            {
                                alert=new AlertDialog.Builder(LoginActivity.this);
                                alert.setTitle("OTP");
                                String otp_create=otp_maker();
                                alert.setMessage("Your OTP is "+otp_create);
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Email.setText("");
                                        Email.setHint("");
                                        Email.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        OTP.setText("Continue");
                                    }
                                });
                                AlertDialog ad=alert.create();
                                ad.show();
                                OTP.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        String get_otp=Email.getText().toString();
                                        if(get_otp.equals(otp_create))
                                        {
                                            next_page();
                                        }
                                        else
                                        {
                                            alert=new AlertDialog.Builder(LoginActivity.this);
                                            alert.setTitle("Error");
                                            alert.setMessage("OTP is not matched.TRY AGAIN");
                                            alert.setCancelable(false);
                                            alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    Email.setVisibility(View.INVISIBLE);
                                                    OTP.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                            AlertDialog ad=alert.create();
                                            ad.show();


                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,"Email is not matched",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(LoginActivity.this,"First Create Account",Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                Toast.makeText(LoginActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String otp_maker()
    {
        int min=100000,max=999999;
        Random random=new Random();
        int rand=min+random.nextInt(max-min);
        return Integer.toString(rand);
    }
}