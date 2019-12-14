package com.example.adeso1.huyenthoai.Player.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.adeso1.huyenthoai.R;

import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioX;
import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioY;

public class BigBang {

    public int x;
    public int y;
    public int width;
    public int height;
    int planeCounter=1;

    public Bitmap BigBang1,BigBang2,BigBang3,BigBang4;


    public BigBang(Resources res){

        BigBang1 = BitmapFactory.decodeResource(res, R.drawable.bigbang1);
        BigBang2= BitmapFactory.decodeResource(res, R.drawable.bigbang2);
        BigBang3= BitmapFactory.decodeResource(res, R.drawable.bigbang3);
        BigBang4= BitmapFactory.decodeResource(res, R.drawable.bigbang4);
        width=BigBang1.getWidth();
        height=BigBang1.getHeight();

        width/=2;
        height /=2;

        width*=(int)screenRatioX;
        height*=(int)screenRatioY;

        BigBang1=Bitmap.createScaledBitmap(BigBang1,(int)width,(int)height,false);
        BigBang2=Bitmap.createScaledBitmap(BigBang2,(int)width,(int)height,false);
        BigBang3=Bitmap.createScaledBitmap(BigBang3,(int)width,(int)height,false);
        BigBang4=Bitmap.createScaledBitmap(BigBang4,(int)width,(int)height,false);


    }
    public Bitmap getBigBang() {
        if (planeCounter == 1) {
            planeCounter++;
            return  BigBang1;
        }

        if (planeCounter==2)
        {
            planeCounter++;
            return  BigBang2;
        }
        if (planeCounter==3)
        {
            planeCounter++;
            return  BigBang3;
        }
        if (planeCounter==4)
        {
            planeCounter=1;
            return  BigBang4;
        }
        return BigBang1;
    }





    public Rect getShape()
    {
        return  new Rect((int)x,(int)y,(int)(x+width),(int)(y+height));
    }
}

