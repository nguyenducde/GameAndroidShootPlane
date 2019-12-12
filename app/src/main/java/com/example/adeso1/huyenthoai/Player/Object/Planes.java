package com.example.adeso1.huyenthoai.Player.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.adeso1.huyenthoai.R;

import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioX;
import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioY;


public class Planes {
    public  int speed=20;
    public int x;
    public int y;
    public int width;
    public int height;
    int planeCounter=1;
    Bitmap plane1,plane2;

    public Planes(Resources res)
    {
        plane1= BitmapFactory.decodeResource(res, R.drawable.plane);
        plane2= BitmapFactory.decodeResource(res, R.drawable.plane1);

        width=plane1.getWidth();
        height=plane1.getHeight();
        width /=5;
        height/=5;
        width *=(int)screenRatioX;
        height *=(int)screenRatioY;
        plane1=Bitmap.createScaledBitmap(plane1,width,height,false);
        plane2=Bitmap.createScaledBitmap(plane2,width,height,false);


       // y=-height;

    }
    public Bitmap getPlanes() {
        if (planeCounter == 1) {
            planeCounter++;
            return  plane1;
        }

       planeCounter=1;
        return  plane1;
    }

    public Rect getShape()
    {
        return  new Rect(x,y,x+width,y+height);
    }


}
