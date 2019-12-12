package com.example.adeso1.huyenthoai.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adeso1.huyenthoai.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_LONG;

public class Register extends AppCompatActivity {
    EditText ednEmail, ednPass, ednRePass;
    TextView txtreg, txtLog;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ednEmail = findViewById(R.id.edtEmailReg);
        ednPass = findViewById(R.id.edtPassReg);
        ednRePass = findViewById(R.id.edtRePassReg);
        txtreg = findViewById(R.id.txtregReg);
        txtLog = findViewById(R.id.txtLogReg);
        progressBar = findViewById(R.id.progressBarReg);
        mAuth = FirebaseAuth.getInstance();
        txtLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, login.class));
            }
        });
        txtreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ednEmail.getText().toString();
                final  String password = ednPass.getText().toString();
                final String repass = ednRePass.getText().toString();

                // regex email
                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                CharSequence inputEmailStr = email;
                Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(inputEmailStr);

                // regex password
                String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
                CharSequence inputPassStr = password;
                Pattern pattern1 = Pattern.compile(passwordPattern, Pattern.CASE_INSENSITIVE);
                Matcher matcherpassword = pattern1.matcher(inputPassStr);

                // if else
                if (email.isEmpty()) {
                    ednEmail.setError("Hay nhap email");
                    ednEmail.requestFocus();
                } else if (!(matcher.matches())) {
                    ednEmail.setError(("Vui long nhap dung dinh dang email"));
                    ednEmail.requestFocus();
                } else if (password.isEmpty()) {
                    ednPass.setError("Vui long nhap password");
                    ednPass.requestFocus();
                } else if (!(matcherpassword.matches())) {
                    ednPass.setError("mat khau tren 8 ki tu, co so va chu");
                    ednPass.requestFocus();
                } else if (repass.isEmpty()) {
                    ednRePass.setError("vui long nhap laij password");
                    ednRePass.requestFocus();
                } else if (repass.equalsIgnoreCase(password)) {
                    ednRePass.setError("repass khong dung voi password");
                    ednRePass.requestFocus();
                } else if (email.isEmpty() && !(matcher.matches()) && password.isEmpty() && !(matcherpassword.matches() && repass.isEmpty() && repass.equalsIgnoreCase(password))) {
                    Toast.makeText(Register.this, "Vui lòng nhập tất cả các trường", LENGTH_LONG).show();
                } else {
                    createanewAccount(email, password);
                }
            }
        });

    }

    private void createanewAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Vui lòng check email de hoan tat viecj tao tai khoan", LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Register.this, "tao tai khoan that bai!", LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    ednEmail.setError("email da ton tai");
                    ednEmail.requestFocus();
                }
            }
        });
    }
}
