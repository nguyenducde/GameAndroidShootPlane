package com.example.adeso1.huyenthoai.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adeso1.huyenthoai.Player.GameActivity;
import com.example.adeso1.huyenthoai.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_LONG;

public class login  extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText ednEmail,ednPassWord;
    TextView txtDangNhap, txtDangKi, txtQuenMatKhau;
    ProgressBar nProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();

        // this is a new

        ednEmail = findViewById(R.id.edtEmailLog);
        ednPassWord = findViewById(R.id.edtPassLog);
        txtDangNhap = findViewById(R.id.txtlogLog);
        txtDangKi = findViewById(R.id.txtregLog);
        txtQuenMatKhau = findViewById(R.id.txtForgotLog);
        nProgressBar = findViewById(R.id.progressBarLog);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // set even when user click register a new account
        txtDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, Register.class));
            }
        });

        // forgot password
        txtQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nProgressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(ednEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        nProgressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "Vui lòng check email để hoàn tất việc reset mật khẩu", LENGTH_LONG).show();
                        } else {
                            Toast.makeText(login.this, task.getException().toString(), LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        // login
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ednEmail.getText().toString();
                final String pass = ednPassWord.getText().toString();

                if (email.isEmpty()) {
                    ednEmail.setError("Please enter email id");
                    ednEmail.requestFocus();
                } else if (pass.isEmpty()) {
                    ednPassWord.setError("Please enter password");
                    ednPassWord.requestFocus();
                } else if (pass.isEmpty() && email.isEmpty()) {
                    Toast.makeText(login.this, "Vui lòng nhập tất cả các trường", LENGTH_LONG).show();
                } else if (!(pass.isEmpty() && email.isEmpty())) {
                    loginEmail(email, pass);
                } else {
                    Toast.makeText(login.this, "Lỗi không xác định", LENGTH_LONG).show();
                }
            }
        });
    }

    private void loginEmail(String email, String pass) {
        nProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(login.this,"Sai email va password", LENGTH_LONG).show();

                } else {
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        startActivity(new Intent(login.this, GameActivity.class));

                    } else {
                        mAuth.signOut();
                        Toast.makeText(login.this,"Hãy xác minh địa chỉ email trước", LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
