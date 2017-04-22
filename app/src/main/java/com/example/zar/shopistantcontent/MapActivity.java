package com.example.zar.shopistantcontent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapActivity extends AppCompatActivity implements View.OnClickListener{
    Button a01,a02,a03,a04,a05,a06,a07,a08,a09,a10,a11,a12,a13,a14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initComponent();
        a01.setOnClickListener(this);
        a02.setOnClickListener(this);
        a03.setOnClickListener(this);
        a04.setOnClickListener(this);
        a05.setOnClickListener(this);
        a06.setOnClickListener(this);
        a07.setOnClickListener(this);
        a08.setOnClickListener(this);
        a09.setOnClickListener(this);
        a10.setOnClickListener(this);
        a11.setOnClickListener(this);
        a12.setOnClickListener(this);
        a13.setOnClickListener(this);
        a14.setOnClickListener(this);
    }
    public void initComponent(){
        a01= (Button) findViewById(R.id.a_01);
        a02= (Button) findViewById(R.id.a_02);
        a03= (Button) findViewById(R.id.a_03);
        a04= (Button) findViewById(R.id.a_04);
        a05= (Button) findViewById(R.id.a_05);
        a06= (Button) findViewById(R.id.a_06);
        a07= (Button) findViewById(R.id.a_07);
        a08= (Button) findViewById(R.id.a_08);
        a09= (Button) findViewById(R.id.a_09);
        a10= (Button) findViewById(R.id.a_10);
        a11= (Button) findViewById(R.id.a_11);
        a12= (Button) findViewById(R.id.a_12);
        a13= (Button) findViewById(R.id.a_13);
        a14= (Button) findViewById(R.id.a_14);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.a_01:
                intent("a01");
                break;
            case R.id.a_02:
                intent("a02");
                break;
            case R.id.a_03:
                intent("a03");
                break;
            case R.id.a_04:
                intent("a04");
                break;
            case R.id.a_05:
                intent("a05");
                break;
            case R.id.a_06:
                intent("a06");
                break;
            case R.id.a_07:
                intent("a07");
                break;
            case R.id.a_08:
                intent("a08");
                break;
            case R.id.a_09:
                intent("a09");
                break;
            case  R.id.a_10:
                intent("a10");
                break;
            case R.id.a_11:
                intent("a11");
                break;
            case R.id.a_12:
                intent("a12");
                break;
            case R.id.a_13:
                intent("a13");
                break;
            case R.id.a_14:
                intent("a14");
                break;
        }
    }

    public void intent(String data){
        Intent intent=new Intent(MapActivity.this,ShelfsActivity.class);
        intent.putExtra("AISLE",data);
        startActivity(intent);
    }


}
