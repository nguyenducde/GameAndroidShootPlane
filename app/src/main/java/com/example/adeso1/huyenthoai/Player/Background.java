package com.example.adeso1.huyenthoai.Player;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.BaseAdapter;

import com.example.adeso1.huyenthoai.R;

import java.util.ResourceBundle;

public class Background {
    int x=0,y=0;
    Bitmap background;
    Background (int screenX, int screenY, Resources res){
    background = BitmapFactory.decodeResource(res, R.drawable.imgbackgroud);
    background=Bitmap.createScaledBitmap(background,screenX,screenY,false);
    }
}
