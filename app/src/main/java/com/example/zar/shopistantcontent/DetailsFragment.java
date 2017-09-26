package com.example.zar.shopistantcontent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Zar on 4/10/2017.
 */

public class DetailsFragment extends Fragment {
    private  int quantity=0;
    private  String itemId;
    private String aislePosition;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_products,container,false);
        String name=getArguments().getString("NAME");
        String price=getArguments().getString("PRICE");
        quantity=getArguments().getInt("QUANTITY");
        itemId=getArguments().getString("ID");
        aislePosition=getArguments().getString("AISLE");

        final EditText edtName= (EditText) view.findViewById(R.id.fragment_product_name);
        final EditText edtPrice= (EditText) view.findViewById(R.id.fragment_product_price);
        final EditText edtQun= (EditText) view.findViewById(R.id.fragment_edt_product_qunatitiy);
        Button btnInc= (Button) view.findViewById(R.id.fragment_btn_increase);
        Button btnDec= (Button) view.findViewById(R.id.fragment_btn_decrease);
        Button btnUpdate= (Button) view.findViewById(R.id.fragment_btn_update);

        if (!name.equals("") && !price.equals("")){
            edtName.setText(name);
            edtPrice.setText(price);
            edtQun.setText(""+quantity);
        }

        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity<=10){
                    quantity++;
                    edtQun.setText(""+quantity);
                }
            }
        });
        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>0){
                    quantity--;
                    edtQun.setText(""+quantity);
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> itemUpdated=new HashMap<String, Object>();
                itemUpdated.put("name",edtName.getText().toString());
                itemUpdated.put("price",edtPrice.getText().toString());
                itemUpdated.put("quantity",quantity);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("productsByPosition").child(aislePosition).child(itemId);
                DatabaseReference allRef=FirebaseDatabase.getInstance().getReference().child("products").child(itemId);
                reference.updateChildren(itemUpdated);
                allRef.updateChildren(itemUpdated);
            }
        });


        return view;
    }
}
