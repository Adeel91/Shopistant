package com.example.zar.shopistantcontent;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;


/**
 * Created by Zar on 4/17/2017.
 */

public class ProductsListAdapter extends FirebaseListAdapter<Product> {

    public ProductsListAdapter(Activity activity, Class<Product> modelClass, int modelLayout, Query ref){
        super(activity,modelClass,modelLayout,ref);
        this.mActivity=activity;
    }
    @Override
    protected void populateView(View v, Product model, int position) {
        TextView name= (TextView) v.findViewById(R.id.txt_product_name);
        TextView ailse= (TextView) v.findViewById(R.id.txt_aisle_num);
        TextView price= (TextView) v.findViewById(R.id.txt_aisle_price);
        TextView quantity= (TextView) v.findViewById(R.id.txt_aisle_qunatity);
        name.setText(model.getName());
        ailse.setText(" "+model.getAislePosition());
        price.setText(" "+model.getPrice());
        quantity.setText(" "+model.getQuantity());

    }
}
