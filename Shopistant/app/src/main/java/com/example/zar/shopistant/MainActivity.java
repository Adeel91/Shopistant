package com.example.zar.shopistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;
import com.example.zar.shopistant.Adapters.SearchListAdapter;
import com.example.zar.shopistant.Adapters.ShoppingListAdapter;
import com.example.zar.shopistant.Utils.Utils;
import com.example.zar.shopistant.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView mAutoCompleteTextView;
    private ListView mListView;
    private View mEmptyView;
    private Query mReference;
    private SearchListAdapter mSearchListAdapter;
    private ArrayList<Product> mShoppingList;
    private ArrayList<String> mKeys;
    private ShoppingListAdapter mShoppingListAdapter;
    private Utils mUtils;
    private double mAvgRating;
    private ProgressBar progressBar;
    private boolean flag;
    private double mRatingFromDb;
    private static final String TAG = "MainActivity";
    private static final String DEVICE_ID=Build.SERIAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        initComponent();
        mListView.setEmptyView(mEmptyView);
        mShoppingList=mUtils.getArrayListFromSf();
        mKeys=mUtils.getStringArrayListFromSF();
        Log.e(TAG,"ArraySize : "+mShoppingList.size());
        Log.e(TAG,"Key ArraySize : "+mKeys.size());
        Log.e(TAG,"android.os.Build.SERIAL: " + Build.SERIAL);
        mShoppingListAdapter=new ShoppingListAdapter(this,mShoppingList,mKeys);
        mListView.setAdapter(mShoppingListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key=mKeys.get(position);
                rateAndReview(key);
            }
        });

        // Searching through firebase data for the single alphabet written into search field (AutoCompleteTextView)
        mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchString=mAutoCompleteTextView.getText().toString();
                if (!searchString.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    mReference = FirebaseDatabase.getInstance().getReference().child("products").orderByChild("name").startAt(searchString).endAt(searchString + "\uf8ff");
                    mSearchListAdapter=new SearchListAdapter(MainActivity.this,Product.class,R.layout.single_list_item,mReference);
                    mAutoCompleteTextView.setAdapter(mSearchListAdapter);
                    mAutoCompleteTextView.showDropDown();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Adding an item selected from drop down list of searched item to Shopping list Array

        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e(TAG,"itemClicked");
                String name=mAutoCompleteTextView.getText().toString();
                Query reference=FirebaseDatabase.getInstance().getReference().child("products").orderByChild("name").equalTo(name);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot childSnapShot: dataSnapshot.getChildren()) {
                            String key=childSnapShot.getKey();
                            Product product=childSnapShot.getValue(Product.class);
                            mKeys.add(key);
                            mShoppingList.add(product);
                            mListView.setAdapter(mShoppingListAdapter);
                            mAutoCompleteTextView.setText("");
                            mUtils.addArrayListToSf(mShoppingList);
                            mUtils.addStringArrayListSF(mKeys);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.translation_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.product_translation:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * Initializations of all global variable used in the current context MainActivity
    * */

    public void initComponent() {

        mListView= (ListView) findViewById(R.id.list_item);
        mAutoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.auto);
        mEmptyView=findViewById(R.id.empty_view);
        mShoppingList=new ArrayList<>();
        mKeys=new ArrayList<>();
        mUtils=new Utils(this);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
    }


    /*
    * Dialog for adding rating item selected from shopping list
    * */

    public void rateAndReview(final String productKey){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        LayoutInflater inflater=this.getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.rate_review_dailogue,null);
        alertDialog.setView(dialogView);
        final RatingBar ratingBar= (RatingBar) dialogView.findViewById(R.id.rating_bar);
        final DatabaseReference referenceSet=FirebaseDatabase.getInstance().getReference().child("rating").child(productKey);
        final DatabaseReference reference=referenceSet.child("productRated");

        checkIfProductRated(DEVICE_ID,productKey);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null) {
                    mRatingFromDb=(double) dataSnapshot.getValue();
                    Log.e(TAG,""+mRatingFromDb);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        alertDialog.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final double rating=ratingBar.getRating();
                averageRate(rating);
                if (checkIfProductRated(DEVICE_ID,productKey)) {
                    Toast.makeText(MainActivity.this,"You can't add an other rate for this product. Thanks",Toast.LENGTH_LONG).show();
                }
                else {

                    HashMap<String,Object> ratingHash=new HashMap<>();
                    ratingHash.put("productRated",mAvgRating);
                    referenceSet.updateChildren(ratingHash);
                    productRatedBy(DEVICE_ID,productKey);
                    updateRating(productKey,mAvgRating);
                    Toast.makeText(MainActivity.this,"Product Rated",Toast.LENGTH_SHORT).show();
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

    /*
    *  Adding unique device id for the product rated by the user once only.
    * */

    public void productRatedBy(String deviceId,String key) {
        final DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("rating").child(key).child("ratedBy").child(deviceId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()==null) {
                    HashMap<String,Object> rated=new HashMap<>();
                    rated.put("rated","true");
                    ref.setValue(rated);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*
    * Confirming that the item user rating is not rated by the same user before.
    * */
    public boolean checkIfProductRated(String deviceId, String key) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("rating").child(key).child("ratedBy").child(deviceId);
        Log.e(TAG," "+ref);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,Object> ratedMap=(HashMap<String,Object>) dataSnapshot.getValue();
                if (ratedMap!=null && ratedMap.size()!=0) {
                    flag=true;
                    Log.e(TAG,"Product Rated Before");
                }
                else {
                    flag = false;
                    Log.e(TAG,"Not rated before");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return flag;
    }

    public void averageRate(double rating) {
        if (mRatingFromDb!=0.0) {
            mAvgRating=(rating+mRatingFromDb)/2;
            Log.e(TAG,"average Rate :"+mAvgRating);
        }

        else {
            mAvgRating=rating;
        }
    }

    public void locate(View view) {
        Intent intent=new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }

    public void updateRating(String key,final double rate) {
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("products").child(key).child("rating");
        final Query referenceCount=FirebaseDatabase.getInstance().getReference().child("rating").child(key).child("ratedBy");
        referenceCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count=dataSnapshot.getChildrenCount();
                Log.e(TAG,"Counted "+count);

                HashMap<String,Object> rating=new HashMap<>();
                rating.put("rating",rate);
                rating.put("ratedByNum",count);
                reference.updateChildren(rating);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
