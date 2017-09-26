package com.example.zar.shopistant.model;

/**
 * Created by Zar on 9/24/2017.
 */

public class Point {
    private float x,y;
    public Point() {}
    public Point(float x,float y) {
        this.x=x;
        this.y=y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
