package com.kartal.kuaforoto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class KayitActivity extends AppCompatActivity {
    private EditText emailEt,passwordEt1,passwordEt2;
    private Button SignUpButton;
    private TextView SignInTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayit);
        mAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt1 = findViewById(R.id.password1);
        passwordEt2 = findViewById(R.id.password2);
        SignUpButton = findViewById(R.id.register);
        progressDialog = new ProgressDialog(KayitActivity.this);
        SignInTv = findViewById(R.id.signInTv);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Register();
                //alo sesin yok ha :d bilir kanka

            }
        });


        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Bunları yazmayı unutma (activity.class yapacağın ,isme göre değiştir.)
                Intent intent= new Intent(KayitActivity.this,GirisActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

        private void Register() {

        String email=emailEt.getText().toString();
        String password1=passwordEt1.getText().toString();
        String password2=passwordEt2.getText().toString();
        if (TextUtils.isEmpty(email)){
            emailEt.setError("E-Mail Girin");
            return;
        }

          else if (TextUtils.isEmpty(password1)){
                passwordEt1.setError("Şifre Girin");
                return;
            }

        else if (TextUtils.isEmpty(password2)){
            passwordEt2.setError("Şifreyi Tekrar Girin!");
            return;
        }

        else if (!password1.equals(password2)){
            passwordEt2.setError("Farklı Şifre Girdiniz!");
            return;
        }

        else if (password1.length()<4){
            passwordEt1.setError("Şifre 4 karakterden az olamaz!");
            return;
        }

        else if (!isVallidEmail(email)){
            emailEt.setError("Geçersiz E-Mail !");
            return;
        }

        progressDialog.setMessage("Lütfen bekleyiniz...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(KayitActivity.this, task -> {
           if (task.isSuccessful()){
               Toast.makeText(com.kartal.kuaforoto.KayitActivity.this,"Kayıt Başarılı",Toast.LENGTH_LONG).show();
               Intent intent= new Intent(KayitActivity.this,GirisActivity.class);
               startActivity(intent);
               finish();

           }
           else{
               Toast.makeText(KayitActivity.this,"Giriş Hatası!",Toast.LENGTH_LONG).show();

           }
           progressDialog.dismiss();
        });


    }

    private Boolean isVallidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());


    }




}
