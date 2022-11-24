package com.example.zakatapps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText gWeight, gValue;
    Button calcBTN, resetBTN;
    TextView tvOutput1, tvOutput2, tvOutput3;
    RadioButton radiokeep, radiowear;
    float gweight;
    float gvalue;


    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;
    //private Menu menu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gWeight = (EditText) findViewById(R.id.goldWeight);
        gValue = (EditText) findViewById(R.id.goldValue);
        radiokeep = (RadioButton)findViewById(R.id.radiokeep) ;
        radiowear = (RadioButton) findViewById(R.id.radiowear);
        tvOutput1 = (TextView) findViewById(R.id.totalGold);
        tvOutput2 = (TextView) findViewById(R.id.zakatPayable);
        tvOutput3 = (TextView) findViewById(R.id.totalZakat);
        calcBTN = (Button) findViewById(R.id.btnCalc);
        resetBTN = (Button) findViewById(R.id.btnReset);

        calcBTN.setOnClickListener(this);
        resetBTN.setOnClickListener(this);

        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gweight = sharedPref.getFloat("weight", 0.0F);

        sharedPref2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        gvalue = sharedPref2.getFloat("value", 0.0F);

        gWeight.setText(""+gweight);
        gValue.setText(""+gvalue);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {

                case R.id.btnCalc:
                    calc();
                    break;

                case R.id.btnReset:
                    gWeight.setText("");
                    gValue.setText("");
                    tvOutput1.setText("");
                    tvOutput2.setText("");
                    tvOutput3.setText("");

                    break;

            }
        } catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Please input your weight of gold and the value of gold above", Toast.LENGTH_SHORT).show();

        } catch (Exception exp) {
            Toast.makeText(this,"Unknown Exception" + exp.getMessage(),Toast.LENGTH_SHORT).show();

            Log.d("Exception",exp.getMessage());

        }
    }

    public void calc(){
        DecimalFormat df = new DecimalFormat("##.00");
        float gweight = Float.parseFloat(gWeight.getText().toString());
        float gvalue = Float.parseFloat(gValue.getText().toString());
        double totGvalue;
        double uruf;
        double Zakatpayable;
        double totZakat;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("weight", gweight);
        editor.apply();
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", gvalue);
        editor2.apply();


        if (radiokeep.isChecked()){
            totGvalue= gweight * gvalue;
            uruf= gweight - 85;

            if(uruf>=0.0) {
                Zakatpayable = uruf * gvalue;
                totZakat = Zakatpayable * 0.025;
            }

            else{
                Zakatpayable = 0.0;
                totZakat = Zakatpayable * 0.025;

            }

            tvOutput1.setText("Total value of the gold: RM"+ df.format(totGvalue));
            tvOutput2.setText("Zakat payable: RM"+ df.format(Zakatpayable));
            tvOutput3.setText("Total Zakat: RM"+ df.format(totZakat));
        }

        else if(radiowear.isChecked()){
            totGvalue= gweight * gvalue;
            uruf= gweight - 200;

            if(uruf>=0.0) {
                Zakatpayable = uruf * gvalue;
                totZakat = Zakatpayable * 0.025;
            }

            else{
                Zakatpayable = 0.0;
                totZakat = Zakatpayable * 0.025;

            }

            tvOutput1.setText("Total value of the gold: RM"+ df.format(totGvalue));
            tvOutput2.setText("Zakat payable: RM"+ df.format(Zakatpayable));
            tvOutput3.setText("Total Zakat: RM"+ df.format(totZakat));

        }

    }


}