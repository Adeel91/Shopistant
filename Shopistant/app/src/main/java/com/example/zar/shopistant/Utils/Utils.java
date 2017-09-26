package com.example.zar.shopistant.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.example.zar.shopistant.model.Product;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zar on 5/7/2017
 */

public class Utils {

    private Context mContext;
    private static final String TAG = "Utils";
    public Utils(Context mContext) {
        this.mContext=mContext;
    }

    private static FirebaseDatabase mDatabase;
    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true); // keeps firebase database offline on cache
        }
        return mDatabase;
    }

    public static boolean checkNetwork(Activity activity) {
        ConnectivityManager cm=(ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        return isConnected;
    }

    public ArrayList<Product> getArrayListFromSf() {
        SharedPreferences mPreferences=mContext.getSharedPreferences("ListPref",Context.MODE_PRIVATE);
        ArrayList<Product> list;
        Gson gson=new Gson();
        String json=mPreferences.getString("JSONLIST","");
        if (json.isEmpty()){
            list=new ArrayList<>();
        }
        else {
            Type type=new TypeToken<ArrayList<Product>>(){}.getType();
            list=gson.fromJson(json,type);
        }

        return list;
    }

    public void addArrayListToSf(ArrayList<Product> mShoppingList) {
        SharedPreferences mPreferences=mContext.getSharedPreferences("ListPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mPreferences.edit();
        Gson gson=new Gson();
        String jsonShoppingList=gson.toJson(mShoppingList);
        Log.e(TAG,jsonShoppingList);
        mEditor.putString("JSONLIST",jsonShoppingList);
        mEditor.commit();
    }

    public void addStringArrayListSF(ArrayList<String> keysArray) {
        SharedPreferences mPreferences=mContext.getSharedPreferences("ListPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mPreferences.edit();
        Set<String> keySet=new HashSet<>();
        keySet.addAll(keysArray);
        mEditor.putStringSet("KEYS",keySet);
        mEditor.commit();
    }

    public ArrayList<String> getStringArrayListFromSF() {
        ArrayList<String> list=new ArrayList<>();
        SharedPreferences mPreferences=mContext.getSharedPreferences("ListPref",Context.MODE_PRIVATE);
        Set<String> keysSets=mPreferences.getStringSet("KEYS",null);
        if (keysSets != null) {
            list.addAll(keysSets);
        }
        return list;
    }
}
