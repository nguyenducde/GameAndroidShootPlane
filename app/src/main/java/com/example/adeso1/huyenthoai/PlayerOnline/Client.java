package com.example.adeso1.huyenthoai.PlayerOnline;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adeso1.huyenthoai.R;

import java.io.IOException;
import java.net.Socket;

public class Client extends AppCompatActivity {
    Button btnketnoi;
    TextView tvgui;
    Socket s;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testclient);
        btnketnoi.findViewById(R.id.btnKetnoi);
        tvgui=findViewById(R.id.txtTen);
        btnketnoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    s=new Socket("192.168.1.5",4000);
                } catch (IOException e) {
                    tvgui.setText("Ket noi that bai");
                    e.printStackTrace();
                }
            }
        });


    }
}
