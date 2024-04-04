package com.example.expensetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.ClientError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder alert;
    private TextView wel,balance,bochor,TB,TC,CP,SP,CS,LMC;
    private Button Add,profile,cost_hist,income_hist,logout,expense,doll;
    private Map<String,Integer>cost_mp=new HashMap<>();
    private Map<String,Integer>income_mp=new HashMap<>();
    private int cost_int=0,income_int=0;
    private String sign=" ৳";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        wel=(TextView) findViewById(R.id.user);
        balance=(TextView) findViewById(R.id.money);
        bochor=(TextView) findViewById(R.id.yearr);
        TB=(TextView) findViewById(R.id.total);
        TC=(TextView) findViewById(R.id.cost);
        CP=(TextView) findViewById(R.id.cost_per);
        SP=(TextView) findViewById(R.id.save_per);
        CS=(TextView) findViewById(R.id.save_cost);
        LMC=(TextView) findViewById(R.id.last_cost);

        Add=(Button) findViewById(R.id.add);
        profile=(Button)findViewById(R.id.profile);
        cost_hist=(Button)findViewById(R.id.cost_hist);
        income_hist=(Button)findViewById(R.id.income_hist);
        logout=(Button)findViewById(R.id.logout);
        expense=(Button)findViewById(R.id.upt_exp);
        doll=(Button)findViewById(R.id.dollar);

        //file_clear_koro();
        Add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                save_koro();
                Intent intent=new Intent(MainActivity.this, Personal_infoActivity.class);
                intent.putExtra("year",bochor.getText().toString());
                //Toast.makeText(MainActivity.this,"problem",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            }
        });
        cost_hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                show_history("Cost Details: ",cost_mp);
            }
        });
        income_hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_history("Income Details: ",income_mp);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_koro();
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Update_Expense_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        doll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doll.getText().toString().equals("Convert to $"))
                {
                    doll.setText("Convert to ৳");
                    Dollar_banao();
                }
                else
                {
                    doll.setText("Convert to $");
                    sign=" ৳";
                    income_int=0;cost_int=0;
                    file_khulo();
                }

            }
        });
        if(bochor.getText().equals(""))
        {
            sal_lagao();
        }
        Bundle bundle2=getIntent().getExtras();
        if(bundle2!=null)
        {
            String a=bundle2.getString("position");
            String b=bundle2.getString("section");
            String c=bundle2.getString("amount");
            //Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();
            file_khulo();
            taka_lagao(a,b, Integer.parseInt(c));
        }
        file_khulo();
    }
    public void sal_lagao()
    {
        try {
            FileInputStream ip=openFileInput("year.txt");
            InputStreamReader is=new InputStreamReader(ip);
            BufferedReader br=new BufferedReader(is);
            String line=br.readLine();
            bochor.setText(line);
            ip.close();
        } catch (FileNotFoundException e) {
            //Toast.makeText(MainActivity.this,"i am here",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Toast.makeText(MainActivity.this,"i am here",Toast.LENGTH_SHORT).show();
        }
    }
    public void taka_lagao(String set,String k, int a)
    {
        if(set.equals("cost"))
        {
            //Toast.makeText(MainActivity.this,"i am here also",Toast.LENGTH_SHORT).show();
            cost_mp.put(k,cost_mp.getOrDefault(k,0)+a);
        }
        else if(set.equals("income"))
        {
            income_mp.put(k,income_mp.getOrDefault(k,0)+a);
        }
        taka_lagao2();

    }
    public void taka_lagao2()
    {
        //Toast.makeText(MainActivity.this,"i am here also",Toast.LENGTH_SHORT).show();
        String k= String.valueOf(cost_int) +sign;
        TC.setText(k);
        k= String.valueOf(income_int) +sign;
        TB.setText(k);
        int x=income_int-cost_int;
        k= String.valueOf(x)+sign;
        balance.setText(k);
        set_extra();
    }
    public void set_extra()
    {
        //Toast.makeText(MainActivity.this,"i am here also 2",Toast.LENGTH_SHORT).show();
        if(income_int!=0)
        {
            double format=((double)cost_int/(double)income_int)*100;
            String formatted=String.format("%.2f %%",format);
            CP.setText(formatted);
            format=((double)income_int - (double)cost_int) / (double)income_int * 100;
            formatted=String.format("%.2f %%",format);
            SP.setText(formatted);
            format=(double)cost_int * 100 / ((double)income_int - (double)cost_int);
            formatted=String.format("%.2f %%",format);
            CS.setText(formatted);
        }
        if(sign.equals(" ৳"))
        {
            save_koro();
        }
    }
    public void file_khulo()
    {
        try {
            FileInputStream ip2=openFileInput("income_saves.txt");
            InputStreamReader is2=new InputStreamReader(ip2);
            BufferedReader br2=new BufferedReader(is2);
            String line;
            int i=1;
            String k="";
            while((line= br2.readLine())!=null)
            {
                if(i%2==1)
                {
                    k=line;
                }
                else
                {
                    income_mp.put(k,Integer.parseInt(line));
                    income_int+=income_mp.get(k);
                }
                i++;
            }
            ip2.close();
            FileInputStream ip3=openFileInput("cost_saves.txt");
            InputStreamReader is3=new InputStreamReader(ip3);
            BufferedReader br3=new BufferedReader(is3);
            i=1;
            k="";
                while((line= br3.readLine())!=null)
                {
                    if(i%2==1)
                    {
                        k=line;
                    }
                    else
                    {
                        cost_mp.put(k,Integer.parseInt(line));
                        cost_int+=cost_mp.get(k);
                    }
                    i++;
                }
            ip3.close();
            FileInputStream ip4=openFileInput("save_info.txt");
            InputStreamReader is4=new InputStreamReader(ip4);
            BufferedReader br4=new BufferedReader(is4);
            ArrayList<String>temp1=new ArrayList<>();
            while((line= br4.readLine())!=null)
            {
                temp1.add(line);
            }
            wel.setText("Welcome "+temp1.get(0));
            ip4.close();
            taka_lagao2();
        } catch (FileNotFoundException e) {
           // Toast.makeText(MainActivity.this,"i am here",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Toast.makeText(MainActivity.this,"i am here",Toast.LENGTH_SHORT).show();
        }
    }
    public void save_koro()
    {
        try {
            FileOutputStream os1=openFileOutput("income_saves.txt",Context.MODE_PRIVATE);
            for( Map.Entry<String, Integer> entry:income_mp.entrySet())
            {

                    os1.write(entry.getKey().getBytes());
                    os1.write("\n".getBytes());
                    os1.write(entry.getValue().toString().getBytes());
                    os1.write("\n".getBytes());

            }
            os1.close();
            FileOutputStream os2=openFileOutput("cost_saves.txt",Context.MODE_PRIVATE);
            for( Map.Entry<String, Integer> entry:cost_mp.entrySet())
            {

                    os2.write(entry.getKey().getBytes());
                    os2.write("\n".getBytes());
                    os2.write(entry.getValue().toString().getBytes());
                    os2.write("\n".getBytes());

            }
            os2.close();
            //Toast.makeText(MainActivity.this,"i am here also 3",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            //Toast.makeText(MainActivity.this,"i am here also",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Toast.makeText(MainActivity.this,"i am here also",Toast.LENGTH_SHORT).show();
        }
    }
    public void file_clear_koro()
    {
        try {
            FileOutputStream os=openFileOutput("cost_saves.txt",Context.MODE_PRIVATE);
            os.write("".getBytes());
            os.close();
            os=openFileOutput("income_saves.txt",Context.MODE_PRIVATE);
            os.write("".getBytes());
            os.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void show_history(String k,Map<String,Integer>map)
    {
        alert=new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(k);
        StringBuilder show= new StringBuilder();
        for(Map.Entry<String,Integer>mp:map.entrySet())
        {
            show.append(mp.getKey()).append(" - ").append(mp.getValue().toString()).append("\n");
        }
        alert.setMessage(show.toString());
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog ad=alert.create();
        ad.show();
    }
    public void logout_koro()
    {
        alert=new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Logout");
        alert.setMessage("Are you want to Logout");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                FileOutputStream os= null;
                try {
                    os = openFileOutput("temp_info.txt", Context.MODE_PRIVATE);
                    os.write("".getBytes());
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog ad=alert.create();
        ad.show();
    }

    public void Dollar_banao()
    {
        String url="https://openexchangerates.org/api/latest.json?app_id=ecc50d1bcaed4b0f9e1e196e5f8350f0";

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            String str=response.getJSONObject("rates").getString("BDT");
                            double val=Double.parseDouble(str);
                            sign=" $";
                            double format=((double)cost_int/val);
                            cost_int=(int)Math.round(format);
                            format=((double)income_int/val);
                            income_int=(int)Math.round(format);
                            taka_lagao2();

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,"Error Occur",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if (error instanceof NoConnectionError)
                {
                    // Handle no connection error
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Handle other errors
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }
}