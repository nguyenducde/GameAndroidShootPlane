package com.example.adeso1.huyenthoai.Player.Object;
//á á á 
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.adeso1.huyenthoai.Player.GameView;
import com.example.adeso1.huyenthoai.R;

import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioX;
import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioY;


public class Fight {
    public float x;
    public float y;
    float height;
    float width;
    float wingCount=0;
    float shootCounter=1;
    Point i=new Point();
    int toshoot=1;
    Bitmap dead;
    public boolean isGoing=false;

    private GameView gameView;
    Bitmap shoot1,shoot2;
    public Fight(GameView gameView, int screenY, Resources res)
    {
        this.gameView=gameView;

        shoot1 =BitmapFactory.decodeResource(res,R.drawable.myplan1);
        shoot2 =BitmapFactory.decodeResource(res,R.drawable.myplane);
      width=shoot1.getWidth();
        height=shoot2.getHeight();
        width /=2.5;
        height /=2.5;
       width *=(int)screenRatioX;
        height *=(int)screenRatioY;
        shoot1=Bitmap.createScaledBitmap(shoot1,(int)width,(int)height,false);
        shoot2=Bitmap.createScaledBitmap(shoot2,(int)width,(int)height,false);

        dead=BitmapFactory.decodeResource(res,R.drawable.dead);

    }
    public Bitmap getFight()
    {   if(toshoot !=0)
    {
        if (shootCounter==0)
        {
            shootCounter++;
            return shoot1;
        }


        shootCounter-=shootCounter;
        toshoot ++;

        gameView.newBullet();


    }
        return shoot2;


    }


   public Rect getShape()
    {
        return  new Rect((int)x,(int)y,(int)(x+width),(int)(y+height));
    }
   public Bitmap getDead()
    {
        return  dead;
    }

}
