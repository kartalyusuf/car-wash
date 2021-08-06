package com.kartal.kuaforoto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GirisActivity extends AppCompatActivity {

    private EditText emailEt,passwordEt;
    private Button SignInButton;
    private TextView SignUpTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris);


        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        SignInButton = findViewById(R.id.giris);
        progressDialog = new ProgressDialog(GirisActivity.this);
        SignUpTv = findViewById(R.id.signUpTv);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login();

            }
        });


        SignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Bunları yazmayı unutma (activity.class yapacağın ,isme göre değiştir.)
                Intent intent= new Intent(GirisActivity.this,KayitActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void  Login() {

        mAuth = FirebaseAuth.getInstance();

        String email=emailEt.getText().toString();
        String password=passwordEt.getText().toString();


        if (TextUtils.isEmpty(email)){
            emailEt.setError("E-Mail Girin");
            return;
        }

        else if (TextUtils.isEmpty(password)){
            passwordEt.setError("Şifre Girin");
            return;
        }


        progressDialog.setMessage("Lütfen bekleyiniz...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(GirisActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(GirisActivity.this,"Giriş Başarılı",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(GirisActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(GirisActivity.this,"Giriş Hatası!",Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();
            }

        });


    }
}