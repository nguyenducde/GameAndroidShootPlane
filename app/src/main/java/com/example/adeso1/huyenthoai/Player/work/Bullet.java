package com.example.adeso1.huyenthoai.Player.work;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.adeso1.huyenthoai.R;

import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioX;
import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioY;

public class Bullet {
    public float x;
    public float y;
    float width;
    float height;
    public Bitmap bullet;
    public Bullet(Resources res){

        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        float width=bullet.getWidth();
        float heiht=bullet.getHeight();

        width/=3.5;
        heiht /=4;

        width*=(int)screenRatioX;
        heiht*=(int)screenRatioY;

        bullet=Bitmap.createScaledBitmap(bullet,(int)width,(int)heiht,false);
    }
    public Rect getShape()
    {
        return  new Rect((int)x,(int)y,(int)(x+width),(int)(y+height));
    }
}
