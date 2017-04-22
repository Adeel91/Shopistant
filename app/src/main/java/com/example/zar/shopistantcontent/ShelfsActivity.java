package com.example.zar.shopistantcontent;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class ShelfsActivity extends AppCompatActivity {
    private static final String TAG = "ShelfsActivity";
    ListView productsList;
    private String aislePosition;
    private int quantityInt=0;
    private DatabaseReference databaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelfs);
        Intent intent=getIntent();
        aislePosition=intent.getStringExtra("AISLE");
        initComponent();
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        Log.e(TAG,aislePosition);
        final ProductsListAdapter adapter=new ProductsListAdapter(this,Product.class,R.layout.product_list_item,databaseRef);
        productsList.setAdapter(adapter);
        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product=adapter.getItem(position);
                String itemId=adapter.getRef(position).getKey();
                if (product!=null)
                {
                  Bundle bundle=new Bundle();
                    bundle.putString("NAME",product.getName());
                    bundle.putString("PRICE",product.getPrice());
                    bundle.putInt("QUANTITY",product.getQuantity());
                    bundle.putString("ID",itemId);
                    bundle.putString("AISLE",aislePosition);
                    DetailsFragment fragment=new DetailsFragment();
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment).commit();

                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });
    }

    public void initComponent(){
        productsList= (ListView) findViewById(R.id.list_item);
        databaseRef= FirebaseDatabase.getInstance().getReference().child("productsByPosition").child(aislePosition);
    }

    public void addProduct(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage("Add Product");
        LayoutInflater inflater=this.getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.add_product_dialog,null);
        alertDialog.setView(dialogView);

        final EditText name= (EditText) dialogView.findViewById(R.id.add_dialog_edt_product_name);
        final EditText quantity= (EditText) dialogView.findViewById(R.id.add_dialog_edt_product_qunatitiy);
        final EditText price= (EditText) dialogView.findViewById(R.id.add_dialog_edt_product_price);
        Button  increase= (Button) dialogView.findViewById(R.id.add_dialog_btn_increase);
        Button decrease= (Button) dialogView.findViewById(R.id.add_dialog_btn_decrease);
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityInt<=10){
                    quantityInt++;
                    quantity.setText(""+quantityInt);
                }
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityInt>0){
                    quantityInt--;
                    quantity.setText(""+quantityInt);
                }
            }
        });


        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!name.getText().toString().equals("")&&!price.getText().toString().equals("")){
                    Product product=new Product(name.getText().toString(),quantityInt,aislePosition,price.getText().toString());
                    DatabaseReference pushRef=databaseRef.push();
                    String key=pushRef.getKey();
                    DatabaseReference allRef=FirebaseDatabase.getInstance().getReference().child("products").child(key);
                    allRef.setValue(product);
                    pushRef.setValue(product);
                }

            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });
        alertDialog.show();
    }
}
