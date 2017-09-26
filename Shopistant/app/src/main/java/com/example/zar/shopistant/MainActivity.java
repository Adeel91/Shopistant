package com.example.zar.shopistant;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
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

    //global variables used by current context

    private AutoCompleteTextView mAutoCompleteTextView;
    private static Button btnLocate;
    private static TextView txtBilled;
    private ListView mListView;
    private View mEmptyView;
    private Query mReference;
    private SearchListAdapter mSearchListAdapter;
    private static ArrayList<Product> mShoppingList;
    private static ArrayList<String> mKeys;
    private ShoppingListAdapter mShoppingListAdapter;
    private Utils mUtils;
    private double mAvgRating;
    private ProgressBar progressBar;
    private double mRatingFromDb;
    private static final String TAG = "MainActivity";
    private static final String DEVICE_ID=Build.SERIAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        searchProduct();
    }

    /*
    * Option menu on the right top corner configurations
    * */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.translation_menu,menu);

        return true;
    }

    /*
    * When any item selected from option menu
    * */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.product_translation:
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * Initializations of all global variable used in the current context MainActivity
    * */

    public void initComponent() {
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        mListView= (ListView) findViewById(R.id.list_item);
        btnLocate= (Button) findViewById(R.id.btn_locate);
        txtBilled= (TextView) findViewById(R.id.txt_billed);
        btnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        mAutoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.auto);
        mEmptyView=findViewById(R.id.empty_view);
        mShoppingList=new ArrayList<>();
        mKeys=new ArrayList<>();
        mUtils=new Utils(this);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        mListView.setEmptyView(mEmptyView);
        mShoppingList=mUtils.getArrayListFromSf();
        mKeys=mUtils.getStringArrayListFromSF();
        if (mKeys.size()!=0) {
            btnLocate.setVisibility(View.VISIBLE);
            txtBilled.setVisibility(View.VISIBLE);
            int billed=0;
            for (int i=0; i<mShoppingList.size(); i++) {

                billed+=Integer.parseInt(mShoppingList.get(i).getPrice());
            }
            txtBilled.setText("Total bill: Rs. "+billed);
        }
        mShoppingListAdapter=new ShoppingListAdapter(this,mShoppingList,mKeys);
        mListView.setAdapter(mShoppingListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key=mKeys.get(position);
                rateAndReview(key);
            }
        });


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
        final DatabaseReference referenceSet=mUtils.getDatabase().getReference().child("rating").child(productKey);
        final DatabaseReference reference=referenceSet.child("productRated");
        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null) {
                    mRatingFromDb= dataSnapshot.getValue(Double.class);
                    Log.e(TAG,""+mRatingFromDb);
                    reference.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } ;
        reference.addListenerForSingleValueEvent(listener);

        alertDialog.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final double rating=ratingBar.getRating();
                averageRate(rating);
                final DatabaseReference ref=mUtils.getDatabase().getReference().child("rating").child(productKey).child("ratedBy").child(DEVICE_ID);
                Log.e(TAG," "+ref);
                ValueEventListener listener=new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String,Object> ratedMap=(HashMap<String,Object>) dataSnapshot.getValue();
                        if (ratedMap!=null && ratedMap.size()!=0) {
                            Toast.makeText(MainActivity.this,"You can't add an other rate for this product. Thanks",Toast.LENGTH_LONG).show();
                            Log.e(TAG,"Product Rated Before");
                        }
                        else {
                            HashMap<String,Object> ratingHash=new HashMap<>();
                            ratingHash.put("productRated",mAvgRating);
                            referenceSet.updateChildren(ratingHash);
                            productRatedBy(DEVICE_ID,productKey,mAvgRating);
                            Toast.makeText(MainActivity.this,"Product Rated",Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Not rated before");
                        }
                        ref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                ref.addListenerForSingleValueEvent(listener);

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

    public void productRatedBy(String deviceId, final String key, final double rate) {

        final DatabaseReference ref=mUtils.getDatabase().getReference().child("rating").child(key).child("ratedBy").child(deviceId);
        ValueEventListener listener=new ValueEventListener() {

           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot.getValue()==null) {

                   HashMap<String,Object> rated=new HashMap<>();
                   rated.put("rated","true");
                   ref.setValue(rated);
                   final DatabaseReference reference=mUtils.getDatabase().getReference().child("products").child(key).child("rating");
                   final Query referenceCount=mUtils.getDatabase().getReference().child("rating").child(key).child("ratedBy");

                   ValueEventListener listener1=new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {

                           long count=dataSnapshot.getChildrenCount();
                           HashMap<String,Object> rating=new HashMap<>();
                           rating.put("rating",rate);
                           rating.put("ratedByNum",count);
                           reference.updateChildren(rating);
                           referenceCount.removeEventListener(this);

                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   };
                   referenceCount.addListenerForSingleValueEvent(listener1);

               }
               ref.removeEventListener(this);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       };
       ref.addListenerForSingleValueEvent(listener);
    }

    /*
    * Average rate calculations
    * */

    public void averageRate(double rating) {
        if (mRatingFromDb!=0.0) {
            mAvgRating=(rating+mRatingFromDb)/2;
            Log.e(TAG,"average Rate :"+mAvgRating);
        }

        else {
            mAvgRating=rating;
        }
    }


    /*
    * searching products from firebase and adding into a list.
    * */

    public void searchProduct() {

        mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchString=mAutoCompleteTextView.getText().toString();
                if (!searchString.equals("") && mUtils.checkNetwork(MainActivity.this)) {
                    progressBar.setVisibility(View.VISIBLE);
                    mReference = mUtils.getDatabase().getReference().child("products").orderByChild("name").startAt(searchString).endAt(searchString + "\uf8ff");
                    mSearchListAdapter=new SearchListAdapter(MainActivity.this,Product.class,R.layout.single_list_item,mReference);
                    mAutoCompleteTextView.setAdapter(mSearchListAdapter);
                    mAutoCompleteTextView.showDropDown();
                }

                else if (!mUtils.checkNetwork(MainActivity.this)) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this,"No network found",Toast.LENGTH_SHORT).show();
                }

                else {
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
                progressBar.setVisibility(View.INVISIBLE);
                String name=mAutoCompleteTextView.getText().toString();
                //Log.e(TAG,mSearchListAdapter.getRef(position).getKey());
                final Query reference=mUtils.getDatabase().getReference().child("products").orderByChild("name").equalTo(name);
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
                            if (mKeys.size()!=0) {
                                btnLocate.setVisibility(View.VISIBLE);
                                txtBilled.setVisibility(View.VISIBLE);
                                int billed=0;
                                for (int i=0; i<mShoppingList.size(); i++) {

                                    billed+=Integer.parseInt(mShoppingList.get(i).getPrice());
                                }
                                txtBilled.setText("Total bill: Rs. "+billed);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    /*
    * updating U.I by data changed in ShoppingListAdapter Class.
    * */

    public static void onDataChanged(Activity context) {
        Log.e(TAG,"called");
        Utils utils=new Utils(context);
        mKeys=utils.getStringArrayListFromSF();
        mShoppingList=utils.getArrayListFromSf();
        if (mKeys.size()!=0) {
            btnLocate.setVisibility(View.VISIBLE);
            txtBilled.setVisibility(View.VISIBLE);
            int billed=0;
            for (int i=0; i<mShoppingList.size(); i++) {

                billed+=Integer.parseInt(mShoppingList.get(i).getPrice());
            }
            txtBilled.setText("Total bill: Rs. "+billed);
        }
        else {

            txtBilled.setVisibility(View.INVISIBLE);
            btnLocate.setVisibility(View.INVISIBLE);
        }

    }
}
