package com.example.zar.shopistant;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.zar.shopistant.Utils.Utils;
import com.example.zar.shopistant.model.Point;
import com.example.zar.shopistant.model.Product;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    // global variables used by current context

    private ArrayList<Product> mShoppingList ;
    private Utils mUtils;
    boolean isLeftSide=false;
    boolean isRightSide=false;
    int mMinOfLeft=0;
    int mMinOfRight=0;
    ArrayList<Point> points;
    ImageView routeView;
    Path path;
    Paint paint;
    private static final String TAG = "MapActivity";

    /*
    * Method overladen from AppCompactActivity part of activity life cycle to run initialize the screen and run all code.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initComponent();
        switchChases();
        generateRoute();
        Log.e(TAG,String.valueOf("Right side "+isRightSide+" Left side "+isLeftSide
        +" Min Right"+mMinOfRight+" Min Left"+mMinOfLeft));
    }

    /*
    * Initialization of all components used in current context
    * */

    public void initComponent() {
        points=new ArrayList<>();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displaymetrics.heightPixels;
        int w = displaymetrics.widthPixels;
        Log.e(TAG,String.valueOf(h+" "+w));
        mUtils=new Utils(this);
        mShoppingList=mUtils.getArrayListFromSf();
        routeView= (ImageView) findViewById(R.id.route_view);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
        float[] intervals = new float[]{50.0f, 20.0f};
        float phase = 0;
        DashPathEffect dashPathEffect =
                new DashPathEffect(intervals, phase);

        paint.setPathEffect(dashPathEffect);

    }

    /*
    * pin point positions configurations
    * */

    public void switchChases() {
        for (int i=0; i<mShoppingList.size(); i++){

            String aisle=mShoppingList.get(i).getAislePosition();
            switch (aisle){
                case "a01":
                    ImageView a01= (ImageView) findViewById(R.id.pin_a01);
                    a01.setVisibility(View.VISIBLE);
                    isRightSide=true;
                    mMinOfRight=1;
                    break;
                case "a02":
                    ImageView a02= (ImageView) findViewById(R.id.pin_a02);
                    a02.setVisibility(View.VISIBLE);
                    isRightSide=true;
                    if (mMinOfRight==0) {
                        mMinOfRight=2;
                    }
                    break;
                case "a03":
                    ImageView a03= (ImageView) findViewById(R.id.pin_a03);
                    isRightSide=true;
                    if (mMinOfRight==0) {
                        mMinOfRight=3;
                    }
                    a03.setVisibility(View.VISIBLE);
                    break;
                case "a04":
                    ImageView a04= (ImageView) findViewById(R.id.pin_a04);
                    isRightSide=true;
                    if (mMinOfRight==0) {
                        mMinOfRight=4;
                    }
                    a04.setVisibility(View.VISIBLE);
                    break;
                case "a05":
                    ImageView a05= (ImageView) findViewById(R.id.pin_a05);
                    isRightSide=true;
                    if (mMinOfRight==0) {
                        mMinOfRight=5;
                    }
                    a05.setVisibility(View.VISIBLE);
                    break;
                case "a06":
                    ImageView a06= (ImageView) findViewById(R.id.pin_a06);
                    isRightSide=true;
                    if (mMinOfRight==0) {
                        mMinOfRight=6;
                    }
                    a06.setVisibility(View.VISIBLE);
                    break;
                case "a07":
                    ImageView a07= (ImageView) findViewById(R.id.pin_a07);
                    isRightSide=true;
                    if (mMinOfRight==0) {
                        mMinOfRight=7;
                    }
                    a07.setVisibility(View.VISIBLE);
                    break;
                case "a08":
                    ImageView a08= (ImageView) findViewById(R.id.pin_a08);
                    isLeftSide=true;
                    mMinOfLeft=8;
                    a08.setVisibility(View.VISIBLE);
                    break;
                case "a09":
                    ImageView a09= (ImageView) findViewById(R.id.pin_a09);
                    isLeftSide=true;
                    if (mMinOfLeft==0) {
                        mMinOfLeft=9;
                    }
                    a09.setVisibility(View.VISIBLE);
                    break;
                case "a10":
                    ImageView a10= (ImageView) findViewById(R.id.pin_a10);
                    isLeftSide=true;
                    if (mMinOfLeft==0) {
                        mMinOfLeft=10;
                    }
                    a10.setVisibility(View.VISIBLE);
                    break;
                case "a11":
                    ImageView a11= (ImageView) findViewById(R.id.pin_a11);
                    isLeftSide=true;
                    if (mMinOfLeft==0) {
                        mMinOfLeft=11;
                    }
                    a11.setVisibility(View.VISIBLE);
                    break;
                case "a12":
                    ImageView a12= (ImageView) findViewById(R.id.pin_a12);
                    isLeftSide=true;
                    if (mMinOfLeft==0) {
                        mMinOfLeft=12;
                    }
                    a12.setVisibility(View.VISIBLE);
                    break;
                case "a13":
                    ImageView a13= (ImageView) findViewById(R.id.pin_a13);
                    isLeftSide=true;
                    if (mMinOfLeft==0) {
                        mMinOfLeft=13;
                    }
                    a13.setVisibility(View.VISIBLE);
                    break;
                case "a14":
                    ImageView a14= (ImageView) findViewById(R.id.pin_a14);
                    isLeftSide=true;
                    if (mMinOfLeft==0) {
                        mMinOfLeft=14;
                    }
                    a14.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }

    /*
    * generating shortest route possible for the pin points marked according to shopping list.
    * */

    public void generateRoute() {

        if (isLeftSide && isRightSide) {
            if (mMinOfRight<4) {
                //round path
                Log.e(TAG,"all true");
                 roundPath();
            }
            else if (mMinOfLeft>=11 && mMinOfRight>=4) {
                //not round path
                Log.e(TAG,"not round true");
                pathReturn();
            }
        }

        else if (isRightSide && !isLeftSide) {
                //only right path
            Log.e(TAG,"only right true");
            if(mMinOfRight!=1) {
                onlyRightPath();
            }
            else {
                roundPath();
            }
        }

        else if (isLeftSide && !isRightSide) {
            //only left path
            Log.e(TAG,"only left true");
            onlyLeftPath();
        }
    }

    /*
    * drawing route on canvas by given points
    * */

    public void pointListOnRoute(ArrayList<Point> list) {
        for (int i=0; i<list.size(); i++) {
            if (i==0) {
                path.moveTo(convertPixelsToDp(list.get(i).getX(),this),convertPixelsToDp(list.get(i).getY(),this));
            }
            else {
                path.lineTo(convertPixelsToDp(list.get(i).getX(),this),convertPixelsToDp(list.get(i).getY(),this));
            }

        }
        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
                .getDefaultDisplay().getWidth(), (int) getWindowManager()
                .getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        routeView.setImageBitmap(bitmap);
        canvas.drawPath(path,paint);
    }

    /*
    * when there is pinpoints on both sides of aisle this path will be drown.
    * */

    public void pathReturn() {
        Point p1=new Point(50,585);
        points.add(p1);
        Point p2=new Point(235,585);
        points.add(p2);

        if (mMinOfRight==7) {
            Point p3=new Point(235,485);
            points.add(p3);
            Point p4=new Point(255,485);
            points.add(p4);
            Point p5=new Point(255,565);
            points.add(p5);
            Point p6=new Point(115,565);
            points.add(p6);
        }

        else if (mMinOfRight==6) {
            Point p3=new Point(235,425);
            points.add(p3);
            Point p4=new Point(255,425);
            points.add(p4);
            Point p5=new Point(255,565);
            points.add(p5);
            Point p6=new Point(115,565);
            points.add(p6);
        }

        else if (mMinOfRight==5) {
            Point p3=new Point(235,385);
            points.add(p3);
            Point p4=new Point(255,385);
            points.add(p4);
            Point p5=new Point(255,565);
            points.add(p5);
            Point p6=new Point(115,565);
            points.add(p6);
        }

        else if (mMinOfRight==4) {
            Point p3=new Point(235,305);
            points.add(p3);
            Point p4=new Point(255,305);
            points.add(p4);
            Point p5=new Point(255,565);
            points.add(p5);
            Point p6=new Point(115,565);
            points.add(p6);
        }

        if (mMinOfLeft==14) {
            Point p7=new Point(115,485);
            points.add(p7);
            Point p8=new Point(50,485);
            points.add(p8);
        }

        else if (mMinOfLeft==13) {
            Point p7=new Point(115,425);
            points.add(p7);
            Point p8=new Point(90,425);
            points.add(p8);
            Point p9=new Point(90,485);
            points.add(p9);
            Point p10=new Point(50,485);
            points.add(p10);
        }

        else if (mMinOfLeft==12) {
            Point p7=new Point(115,385);
            points.add(p7);
            Point p8=new Point(90,385);
            points.add(p8);
            Point p9=new Point(90,485);
            points.add(p9);
            Point p10=new Point(50,485);
            points.add(p10);
        }
        else if (mMinOfLeft==11) {
            Point p7=new Point(115,305);
            points.add(p7);
            Point p8=new Point(90,305);
            points.add(p8);
            Point p9=new Point(90,485);
            points.add(p9);
            Point p10=new Point(50,485);
            points.add(p10);
        }
        pointListOnRoute(points);
    }

    /*
    * This is the complete path followed around aisle.
    * */

    public void roundPath() {
        Point p1=new Point(50,585);
        points.add(p1);
        Point p2=new Point(250,585);
        points.add(p2);
        Point p3=new Point(250,55);
        points.add(p3);
        Point p4=new Point(90,55);
        points.add(p4);
        Point p5=new Point(90,510);
        points.add(p5);
        Point p6=new Point(50,510);
        points.add(p6);
        pointListOnRoute(points);

    }

    /*
    * when pinpoints only marked on right side this path will be follow.
    * */

    public void onlyRightPath() {
        Point p1=new Point(50,585);
        points.add(p1);
        Point p2=new Point(235,585);
        points.add(p2);
        switch (mMinOfRight) {
            case 7:
                rightPathPoints(485);
                break;
            case 6:
                rightPathPoints(425);
                break;
            case 5:
                rightPathPoints(385);
                break;
            case 4:
                rightPathPoints(305);
                break;
            case 3:
                rightPathPoints(265);
                break;
            case 2:
                rightPathPoints(185);
                break;
        }
    }

    /*
    * when pinpoints only marked on left side this path will be follow.
    * */

    public void onlyLeftPath() {

        switch (mMinOfLeft) {
            case 14:
                leftPathPoints(485);
                break;
            case 13:
                leftPathPoints(425);
                break;
            case 12:
                leftPathPoints(385);
                break;
            case 11:
                leftPathPoints(305);
                break;
            case 10:
                leftPathPoints(265);
                break;
            case 9:
                leftPathPoints(165);
                break;
            case 8:
                leftPathPoints(125);
                break;
        }
    }

    /*
    * The points which will be going to select for right side path only.
    * */

    public void rightPathPoints(int y) {
        Point p3=new Point(235,y);
        points.add(p3);
        Point p4=new Point(255,y);
        points.add(p4);
        Point p5=new Point(255,565);
        points.add(p5);
        Point p6=new Point(115,565);
        points.add(p6);
        Point p7=new Point(115,485);
        points.add(p7);
        Point p8=new Point(50,485);
        points.add(p8);
        pointListOnRoute(points);
    }

    /*
    * The points which will be going to select for left side path only.
    * */

    public void leftPathPoints(int y) {
        Point p1=new Point(50,585);
        points.add(p1);
        Point p2=new Point(115,585);
        points.add(p2);
        Point p3=new Point(115,y);
        points.add(p3);
        if (y==485) {
            Point p4=new Point(50,y);
            points.add(p4);
        }
        else {
            Point p4=new Point(90,y);
            points.add(p4);
            Point p5=new Point(90,485);
            points.add(p5);
            Point p6=new Point(50,485);
            points.add(p6);
        }
        pointListOnRoute(points);
    }

    /*
    * This method used to convert the pixels into dp (Display independent pixels). To make it responsive for kind of screen sizes.
    * */

    public float convertPixelsToDp(float px, Context context){
        Resources r = context.getResources();
        DisplayMetrics metrics = r.getDisplayMetrics();
        float dp = px * (metrics.densityDpi / 160f);
        Log.e("tag",String.valueOf(metrics.densityDpi));
        return dp;
    }
 }
