package com.example.adeso1.huyenthoai.Player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.adeso1.huyenthoai.Login.Register;
import com.example.adeso1.huyenthoai.Login.login;
import com.example.adeso1.huyenthoai.R;

public class MainActivity extends AppCompatActivity {
TextView ChoiMoi,DangKy,DangNhap,choionline;
private boolean online=true;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChoiMoi=findViewById(R.id.txtChoi);

        setContentView(R.layout.activity_main);
        mediaPlayer=MediaPlayer.create(this,R.raw.yumenhatban);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();
        choionline=findViewById(R.id.txtChoiOnlie);
        DangKy=findViewById(R.id.txtDangKy);
        DangNhap=findViewById(R.id.txtDangNhap);

        findViewById(R.id.txtChoi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();

                startActivity(new Intent(MainActivity.this, GameActivity.class
                ));
            }
        });
        DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, login.class));
            }
        });
        if(online==true)
        {
            choionline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        else {
            online=false;
        }



    }


}
