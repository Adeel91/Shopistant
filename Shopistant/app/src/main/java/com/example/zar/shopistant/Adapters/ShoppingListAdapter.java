package com.example.zar.shopistant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zar.shopistant.MainActivity;
import com.example.zar.shopistant.Utils.Utils;
import com.example.zar.shopistant.model.Product;
import com.example.zar.shopistant.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Zar on 4/29/2017.
 */

public class ShoppingListAdapter extends ArrayAdapter<Product> {
    private static final String TAG = "ShoppingListAdapter";
    private ArrayList<Product> shoppingList;
    private ArrayList<String> keys;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    Activity mContext;

    public ShoppingListAdapter(Activity context, ArrayList<Product> shoppingList, ArrayList<String> keys){
        super(context,0,shoppingList);
        this.shoppingList=shoppingList;
        this.keys=keys;
        this.mContext=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_shopping_list_item, parent, false);
        }

        Product item=getItem(position);
        Log.e(TAG,item.getPrice());
        TextView itemName= (TextView) listItemView.findViewById(R.id.text_view_active_list_item_name);
        char[] array=item.getName().toCharArray();
        int count=0;

        for (int i=0; i<array.length; i++) {
            String current=""+array[i];
            if (current.equals(" ")) {
                count=i;
                break;
            }

        }

        itemName.setText(item.getName().substring(count));
        TextView itemPrice= (TextView) listItemView.findViewById(R.id.text_view_price);
        itemPrice.setText("Rs. "+item.getPrice());
        ImageButton buttonRemoveItem=(ImageButton) listItemView.findViewById(R.id.button_remove_item);

        buttonRemoveItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(getContext(),R.style.CustomTheme_Dialog)
                        .setTitle(getContext().getString(R.string.remove_item_option))
                        .setMessage(getContext().getString(R.string.dialog_message_are_you_sure_remove_item))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                removeItem(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();

            }
        });
        return listItemView;
    }

    private void removeItem(int id) {
        Utils mUtils=new Utils(mContext);
        shoppingList.remove(id);
        keys.remove(id);
        mUtils.addArrayListToSf(shoppingList);
        mUtils.addStringArrayListSF(keys);
        notifyDataSetChanged();
        ((MainActivity)mContext).onDataChanged(mContext);
    }

}
