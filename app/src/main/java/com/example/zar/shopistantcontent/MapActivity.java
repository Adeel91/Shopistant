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
                Intent intent=new Intent(MapActivity.this,ShelfsActivity.class);
                intent.putExtra("AISLE","a01");
                startActivity(intent);
                break;
            case R.id.a_02:
                Intent intentA02=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA02.putExtra("AISLE","a02");
                startActivity(intentA02);
                break;
            case R.id.a_03:
                Intent intentA03=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA03.putExtra("AISLE","a03");
                startActivity(intentA03);
                break;
            case R.id.a_04:
                Intent intentA04=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA04.putExtra("AISLE","a04");
                startActivity(intentA04);
                break;
            case R.id.a_05:
                Intent intentA05=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA05.putExtra("AISLE","a05");
                startActivity(intentA05);
                break;
            case R.id.a_06:
                Intent intentA06=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA06.putExtra("AISLE","a06");
                startActivity(intentA06);
                break;
            case R.id.a_07:
                Intent intentA07=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA07.putExtra("AISLE","a07");
                startActivity(intentA07);
                break;
            case R.id.a_08:
                Intent intentA08=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA08.putExtra("AISLE","a08");
                startActivity(intentA08);
                break;
            case R.id.a_09:
                Intent intentA09=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA09.putExtra("AISLE","a09");
                startActivity(intentA09);
                break;
            case  R.id.a_10:
                Intent intentA10=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA10.putExtra("AISLE","a10");
                startActivity(intentA10);
                break;
            case R.id.a_11:
                Intent intentA11=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA11.putExtra("AISLE","a11");
                startActivity(intentA11);
                break;
            case R.id.a_12:
                Intent intentA12=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA12.putExtra("AISLE","a12");
                startActivity(intentA12);
                break;
            case R.id.a_13:
                Intent intentA13=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA13.putExtra("AISLE","a13");
                startActivity(intentA13);
                break;
            case R.id.a_14:
                Intent intentA14=new Intent(MapActivity.this,ShelfsActivity.class);
                intentA14.putExtra("AISLE","a14");
                startActivity(intentA14);
                break;
        }
    }

}
