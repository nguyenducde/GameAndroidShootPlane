package com.example.adeso1.huyenthoai.Player;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adeso1.huyenthoai.R;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    public GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point =new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView=new GameView(this,point.x,point.y);
        setContentView(gameView);



    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }


}
