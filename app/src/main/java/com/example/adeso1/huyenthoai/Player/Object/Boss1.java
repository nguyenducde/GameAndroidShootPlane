package com.example.adeso1.huyenthoai.Player.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.adeso1.huyenthoai.Player.GameView;
import com.example.adeso1.huyenthoai.R;

import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioX;
import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioY;

public class Boss1 {
    public float x;
    public float y;
    private GameView gameView;
    float height;
    float width;
    Bitmap boss;
    public Boss1(GameView gameView, int screenY, Resources res)
    {
        this.gameView=gameView;
        boss= BitmapFactory.decodeResource(res, R.drawable.boss);
        width=boss.getWidth();
        height=boss.getHeight();
        width /=2;
        height/=2;
        width *=(int)screenRatioX;
        height *=(int)screenRatioY;
        boss=Bitmap.createScaledBitmap(boss,(int)width,(int)height,false);
    }
    public Bitmap getPlanes() {
        gameView.newBulletBossLV1();
        return  boss;
    }

    public Rect getShape()
    {
        return  new Rect((int)x,(int)y,(int)(x+width),(int)(y+height));
    }

}
