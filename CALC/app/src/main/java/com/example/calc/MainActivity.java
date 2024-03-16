package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button[] bt= new Button[22];
    private TextView Exp,Result;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt[0]=(Button) findViewById(R.id.bt0);
        bt[1]=(Button) findViewById(R.id.bt1);
        bt[2]=(Button) findViewById(R.id.bt2);
        bt[3]=(Button) findViewById(R.id.bt3);
        bt[4]=(Button) findViewById(R.id.bt4);
        bt[5]=(Button) findViewById(R.id.bt5);
        bt[6]=(Button) findViewById(R.id.bt6);
        bt[7]=(Button) findViewById(R.id.bt7);
        bt[8]=(Button) findViewById(R.id.bt8);
        bt[9]=(Button) findViewById(R.id.bt9);
        bt[10]=(Button) findViewById(R.id.bt10);
        bt[11]=(Button) findViewById(R.id.bt11);
        bt[12]=(Button) findViewById(R.id.bt_div);
        bt[13]=(Button) findViewById(R.id.bt_mul);
        bt[14]=(Button) findViewById(R.id.bt_plus);
        bt[15]=(Button) findViewById(R.id.bt_minus);
        bt[16]=(Button) findViewById(R.id.bt_back);
        bt[17]=(Button) findViewById(R.id.bt_cancel);
        bt[18]=(Button) findViewById(R.id.bt_equal);
        bt[19]=(Button) findViewById(R.id.bt_dot);
        bt[20]=(Button) findViewById(R.id.bt_mod);
        bt[21]=(Button) findViewById(R.id.bt_expo);
        bt[0].setOnClickListener(this);
        bt[1].setOnClickListener(this);
        bt[2].setOnClickListener(this);
        bt[3].setOnClickListener(this);
        bt[4].setOnClickListener(this);
        bt[5].setOnClickListener(this);
        bt[6].setOnClickListener(this);
        bt[7].setOnClickListener(this);
        bt[8].setOnClickListener(this);
        bt[9].setOnClickListener(this);
        bt[10].setOnClickListener(this);
        bt[11].setOnClickListener(this);
        bt[12].setOnClickListener(this);
        bt[13].setOnClickListener(this);
        bt[14].setOnClickListener(this);
        bt[15].setOnClickListener(this);
        bt[16].setOnClickListener(this);
        bt[17].setOnClickListener(this);
        bt[18].setOnClickListener(this);
        bt[19].setOnClickListener(this);
        bt[20].setOnClickListener(this);
        bt[21].setOnClickListener(this);
        Exp=(TextView) findViewById(R.id.values);
        Result=(TextView) findViewById(R.id.result);
    }
    private int prec(char k)
    {
        if(k=='+'||k=='-')
        {
            return 0;
        }
        else if (k=='*'||k=='/'||k=='%')
        {
            return 1;
        }
        else if(k=='^')
        {
            return 2;
        }
        return -2;
    }
    private Double math(char k,Double d1,Double d2)
    {
        if (k== '+') {
            return (d2 + d1);
        }
        else if (k == '-') {
            return (d2 - d1);
        }
        else if (k == '*') {
            return (d2 * d1);
        }
        else if (k == '/') {
            if (d1==0)
            {
                Toast.makeText(MainActivity.this,"Invalid Argument",Toast.LENGTH_SHORT).show();
            }
            else {
                return (d2 / d1);
            }
        }
        else if(k=='%')
        {
            if (d1==0)
            {
                Toast.makeText(MainActivity.this,"Invalid Argument",Toast.LENGTH_SHORT).show();
            }
            else return d2%d1;
        }
        else if (k=='^')
        {
            if (d1==0&&d2==0)
            {
                Toast.makeText(MainActivity.this,"Invalid Argument",Toast.LENGTH_SHORT).show();
            }
            else{
            Double x=1.0;
            for(int i=1;i<=d1;i++)
            {
                x*=d2;
            }
            return x;}
        }
        return 0.0;
    }
    private String Calculate(String St)
    {
        String r = "",s="(0"+St+')';
        s=s.replace("(-","(0-");
        s=s.replace("--","+");
        s=s.replace("+-","-");
        s=s.replace("-+","-");
        Stack<Character> st = new Stack<>();
        Stack<Double> std = new Stack<>();
        Double d1=0.0,d2=0.0;
        for (int i = 0; i < s.length(); i++) {
            char k = s.charAt(i);
            if (k >= '0' && k <= '9' || k == '.')
            {
                r += k;
            }
            else
            {
                if (r.length() != 0)
                {
                    Double d = Double.parseDouble(r);
                    std.push(d);
                    r = "";
                }
                if (k == '(' || st.empty() || prec(st.peek()) <= prec(k) && k != ')') {
                    st.push(k);
                } else if (k == ')') {
                    while (!st.empty() && st.peek() != '(') {
                        try{
                        d1 = std.peek();
                        std.pop();
                        d2 = std.peek();
                        std.pop();}
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this,"Invalid Argument",Toast.LENGTH_SHORT).show();
                            return "";
                        }
                        std.push(math(st.peek(),d1,d2));
                        st.pop();
                    }
                    st.pop();

                } else if (prec(st.peek()) > prec(k)) {
                    while (!st.empty() && prec(st.peek()) > prec(k)) {
                        try{
                        d1 = std.peek();
                        std.pop();
                        d2 = std.peek();
                        std.pop();}
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this,"Invalid Argument",Toast.LENGTH_SHORT).show();
                            return "";
                        }
                        std.push(math(st.peek(),d1,d2));
                        st.pop();
                    }
                    st.push(k);

                }
            }
        }
        Double M=std.peek();
        String F=M.toString();
        return F;
    }
    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.bt0)
        {
            String r=(String) Exp.getText(),rr=r+'0';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt1)
        {
            String r=(String) Exp.getText(),rr=r+'1';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt2)
        {
            String r=(String) Exp.getText(),rr=r+'2';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt3)
        {
            String r=(String) Exp.getText(),rr=r+'3';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt4)
        {
            String r=(String) Exp.getText(),rr=r+'4';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt5)
        {
            String r=(String) Exp.getText(),rr=r+'5';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt6)
        {
            String r=(String) Exp.getText(),rr=r+'6';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt7)
        {
            String r=(String) Exp.getText(),rr=r+'7';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt8)
        {
            String r=(String) Exp.getText(),rr=r+'8';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt9)
        {
            String r=(String) Exp.getText(),rr=r+'9';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt10)
        {
            String r=(String) Exp.getText(),rr=r+'(';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt11)
        {
            String r=(String) Exp.getText(),rr=r+')';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt_cancel)
        {
            Exp.setText("");
            Result.setText("");
        }
        else if(v.getId()==R.id.bt_div)
        {
            String r=(String) Exp.getText(),rr=r+'/';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt_mul)
        {
            String r=(String) Exp.getText(),rr=r+'*';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt_plus)
        {
            String r=(String) Exp.getText(),rr=r+'+';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt_minus)
        {
            String r=(String) Exp.getText(),rr=r+'-';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt_equal)
        {
            String r=(String) Exp.getText();
            Result.setText(Calculate(r));
        }
        else if(v.getId()==R.id.bt_back)
        {
            try
            {
                String r=(String) Exp.getText(),rr=r.substring(0,r.length()-1);
                Exp.setText(rr);
            }
            catch (Exception e)
            {
                Toast.makeText(MainActivity.this,"No Argument",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()==R.id.bt_dot)
        {
            String r=(String) Exp.getText(),rr=r+'.';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt_mod)
        {
            String r=(String) Exp.getText(),rr=r+'%';
            Exp.setText(rr);
        }
        else if(v.getId()==R.id.bt_expo)
        {
            String r=(String) Exp.getText(),rr=r+'^';
            Exp.setText(rr);
        }
    }
}