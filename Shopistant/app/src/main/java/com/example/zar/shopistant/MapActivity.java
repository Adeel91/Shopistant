package com.example.zar.shopistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.zar.shopistant.Utils.Utils;
import com.example.zar.shopistant.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    private ArrayList<Product> mShoppingList ;
    private Utils mUtils;
    private static final String TAG = "MapActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initComponent();
        mShoppingList=mUtils.getArrayListFromSf();

        for (int i=0; i<mShoppingList.size(); i++){
            String aisle=mShoppingList.get(i).getAislePosition();
            switch (aisle){
                case "a01":
                    ImageView a01= (ImageView) findViewById(R.id.pin_a01);
                    a01.setVisibility(View.VISIBLE);
                    break;
                case "a02":
                    ImageView a02= (ImageView) findViewById(R.id.pin_a02);
                    a02.setVisibility(View.VISIBLE);
                    break;
                case "a03":
                    ImageView a03= (ImageView) findViewById(R.id.pin_a03);
                    a03.setVisibility(View.VISIBLE);
                    break;
                case "a04":
                    ImageView a04= (ImageView) findViewById(R.id.pin_a04);
                    a04.setVisibility(View.VISIBLE);
                    break;
                case "a05":
                    ImageView a05= (ImageView) findViewById(R.id.pin_a05);
                    a05.setVisibility(View.VISIBLE);
                    break;
                case "a06":
                    ImageView a06= (ImageView) findViewById(R.id.pin_a06);
                    a06.setVisibility(View.VISIBLE);
                    break;
                case "a07":
                    ImageView a07= (ImageView) findViewById(R.id.pin_a07);
                    a07.setVisibility(View.VISIBLE);
                    break;
                case "a08":
                    ImageView a08= (ImageView) findViewById(R.id.pin_a08);
                    a08.setVisibility(View.VISIBLE);
                    break;
                case "a09":
                    ImageView a09= (ImageView) findViewById(R.id.pin_a09);
                    a09.setVisibility(View.VISIBLE);
                    break;
                case "a10":
                    ImageView a10= (ImageView) findViewById(R.id.pin_a10);
                    a10.setVisibility(View.VISIBLE);
                    break;
                case "a11":
                    ImageView a11= (ImageView) findViewById(R.id.pin_a11);
                    a11.setVisibility(View.VISIBLE);
                    break;
                case "a12":
                    ImageView a12= (ImageView) findViewById(R.id.pin_a12);
                    a12.setVisibility(View.VISIBLE);
                    break;
                case "a13":
                    ImageView a13= (ImageView) findViewById(R.id.pin_a13);
                    a13.setVisibility(View.VISIBLE);
                    break;
                case "a14":
                    ImageView a14= (ImageView) findViewById(R.id.pin_a14);
                    a14.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }

    public void initComponent() {
        mShoppingList=new ArrayList<>();
        mUtils=new Utils(this);
    }


}
