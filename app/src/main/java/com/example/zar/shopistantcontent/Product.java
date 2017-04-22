package com.example.zar.shopistantcontent;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by Zar on 4/16/2017.
 */

public class Product  {

    private String name,aislePosition,price;
    private int quantity;
    public Product(){}
    public Product(String name,int quantity,String aislePosition,String price){
        this.name=name;
        this.quantity=quantity;
        this.aislePosition=aislePosition;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getAislePosition() {
        return aislePosition;
    }

    public String getPrice() {
        return price;
    }
}

