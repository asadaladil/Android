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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Update_Expense_Activity extends AppCompatActivity {

    private Button cost,income,search,change,cont,Back;
    private TextView section,amount,info,result;
    private EditText sec_name,amt_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_expense);

        cont=(Button) findViewById(R.id.add_money);
        income=(Button) findViewById(R.id.tap_income);
        search=(Button) findViewById(R.id.search);
        change=(Button) findViewById(R.id.cng_value);
        cost=(Button) findViewById(R.id.tap_cost);
        Back=(Button)findViewById(R.id.back);

        section=(TextView) findViewById(R.id.section_text);
        amount=(TextView) findViewById(R.id.amount_text);
        info=(TextView) findViewById(R.id.currency);
        result=(TextView) findViewById(R.id.result);

        sec_name=(EditText) findViewById(R.id.section);
        amt_name=(EditText) findViewById(R.id.value);

        cost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                samne_ano();
                Map<String,Integer>costt=file_khulo("cost_saves.txt");
                search.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String a=sec_name.getText().toString();
                        if(a.isEmpty())
                        {
                            Toast.makeText(Update_Expense_Activity.this,"Please Fill up the box",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(costt.containsKey(a))
                            {
                                result.setText(a+"-> "+costt.get(a));
                                result.setVisibility(View.VISIBLE);
                                change.setVisibility(View.VISIBLE);
                                change.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        amount.setVisibility(View.VISIBLE);
                                        amt_name.setVisibility(View.VISIBLE);
                                        info.setVisibility(View.VISIBLE);
                                        cont.setVisibility(View.VISIBLE);
                                        cont.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String k=amt_name.getText().toString();
                                                if(k.isEmpty())
                                                {
                                                    Toast.makeText(Update_Expense_Activity.this,"Please Fill up the box",Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    if(Integer.parseInt(k)==0)
                                                    {
                                                        costt.remove(a);
                                                    }
                                                    else
                                                    {
                                                        costt.put(a,Integer.parseInt(k));
                                                    }

                                                    Toast.makeText(Update_Expense_Activity.this,"Value Updated",Toast.LENGTH_SHORT).show();
                                                    file_bondo_koro("cost_saves.txt",costt);
                                                    gorte_dukao();

                                                }
                                            }
                                        });
                                    }
                                });
                            }
                            else
                            {
                                result.setText("No Data Found");
                                result.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        });

        income.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                samne_ano();
                Map<String,Integer>Income=file_khulo("income_saves.txt");
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a=sec_name.getText().toString();
                        if(a.isEmpty())
                        {
                            Toast.makeText(Update_Expense_Activity.this,"Please Fill up the box",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(Income.containsKey(a))
                            {
                                result.setText(a+"-> "+Income.get(a));
                                result.setVisibility(View.VISIBLE);
                                change.setVisibility(View.VISIBLE);
                                change.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        amount.setVisibility(View.VISIBLE);
                                        amt_name.setVisibility(View.VISIBLE);
                                        info.setVisibility(View.VISIBLE);
                                        cont.setVisibility(View.VISIBLE);
                                        cont.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String k=amt_name.getText().toString();
                                                if(k.isEmpty())
                                                {
                                                    Toast.makeText(Update_Expense_Activity.this,"Please Fill up the box",Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    if(Integer.parseInt(k)==0)
                                                    {
                                                        Income.remove(a);
                                                    }
                                                    else
                                                    {
                                                        Income.put(a,Integer.parseInt(k));
                                                    }
                                                    Toast.makeText(Update_Expense_Activity.this,"Value Updated",Toast.LENGTH_SHORT).show();
                                                    file_bondo_koro("income_saves.txt",Income);
                                                    gorte_dukao();

                                                }
                                            }
                                        });
                                    }
                                });
                            }
                            else
                            {
                                result.setText("No Data Found");
                                result.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        });
        Back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                next_page();
            }
        });
    }

    public void samne_ano()
    {
        section.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);
        sec_name.setVisibility(View.VISIBLE);

    }
    public void gorte_dukao()
    {
        section.setVisibility(View.INVISIBLE);
        amount.setVisibility(View.INVISIBLE);
        info.setVisibility(View.INVISIBLE);
        result.setVisibility(View.INVISIBLE);
        search.setVisibility(View.INVISIBLE);
        change.setVisibility(View.INVISIBLE);
        cont.setVisibility(View.INVISIBLE);
        sec_name.setVisibility(View.INVISIBLE);
        amt_name.setVisibility(View.INVISIBLE);
        amt_name.setText("");
        sec_name.setText("");
    }
    public Map<String,Integer> file_khulo(String k)
    {
        try {
            FileInputStream ip=openFileInput(k);
            InputStreamReader is=new InputStreamReader(ip);
            BufferedReader br=new BufferedReader(is);
            String line;
            Map<String,Integer>mp=new HashMap<>();
            int i=1;
            while ((line=br.readLine())!=null)
            {
                if(i%2==1)
                {
                    k=line;
                }
                else
                {
                    mp.put(k,Integer.parseInt(line));
                }
                i++;
            }
            ip.close();
            return mp;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void file_bondo_koro(String k,Map<String,Integer>mp)
    {
        try {
            FileOutputStream os=openFileOutput(k,Context.MODE_PRIVATE);
            os.write("".getBytes());
            for(Map.Entry<String,Integer>mm:mp.entrySet())
            {
                os.write(mm.getKey().getBytes());
                os.write("\n".getBytes());
                os.write(mm.getValue().toString().getBytes());
                os.write("\n".getBytes());
            }
            os.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void next_page()
    {
        Intent intent=new Intent(Update_Expense_Activity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}