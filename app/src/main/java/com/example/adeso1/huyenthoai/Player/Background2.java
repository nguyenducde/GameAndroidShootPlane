package com.example.adeso1.huyenthoai.Player;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.adeso1.huyenthoai.R;

public class Background2 {
    int x=0,y=0;
    Bitmap background;
    Background2 (int screenX, int screenY, Resources res){
        background = BitmapFactory.decodeResource(res, R.drawable.backgroud2);
        background=Bitmap.createScaledBitmap(background,screenX,screenY,false);
    }
}
