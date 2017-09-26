package com.example.zar.shopistant.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.zar.shopistant.model.Product;
import com.example.zar.shopistant.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;
import java.util.HashMap;

/**
 * Created by Zar on 4/21/2017.
 */

public class SearchListAdapter extends FirebaseListAdapter<Product> implements Filterable {
    private static final String TAG = "SearchListAdapter";
    public SearchListAdapter(Activity activity, Class<Product> modelClass, int modelLayout, Query ref){
        super(activity,modelClass,modelLayout,ref);
        this.mActivity=activity;
    }
    @Override
    protected void populateView(View v, Product model, int position) {
        TextView textView= (TextView) v.findViewById(R.id.txt_item);
        final TextView rate= (TextView) v.findViewById(R.id.txt_rate);
        HashMap<String,Object> mapRate=model.getRating();
        String rateString=mapRate.get("rating").toString();
        String rateByNum=mapRate.get("ratedByNum").toString();
        rate.setText(rateString+"/"+rateByNum);
        textView.setText(model.getName());
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Product)(resultValue)).getName();
            return str;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results != null && results.count > 0) {
                notifyDataSetChanged();
            }
            else {
                notifyDataSetInvalidated();
            }
        }
    };


}
