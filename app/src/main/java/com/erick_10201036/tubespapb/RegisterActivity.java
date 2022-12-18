package com.erick_10201036.tubespapb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.internal.EdgeToEdgeUtils;

public class RegisterActivity extends AppCompatActivity {
    DBHelper db;
    Button register;
    EditText username, password, passwordConf, nomorHp;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelper(this);

        username = (EditText) findViewById(R.id.edtR_nama);
        password = (EditText) findViewById(R.id.edtR_password);
        nomorHp = (EditText) findViewById(R.id.edtR_nohp);
        passwordConf = (EditText) findViewById(R.id.edtR_konfirpass);
        back = (ImageView) findViewById(R.id.regis_back);
        register = (Button) findViewById(R.id.Reg_btn_1);

        //REGISTER
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                String strNohp = nomorHp.getText().toString();
                String strPasswordConf = passwordConf.getText().toString();

                if (strUsername.length() == 0){
                    username.setError("Nama diperlukan");
                }else {
                    if (strPassword.length() == 0){
                        password.setError("Masukan password");
                    }
                    else {
                        if (strPassword.equals(strPasswordConf)){
                            Boolean daftar = db.insertUser(strUsername,strPassword,strNohp);
                            if (daftar == true){
                                Toast.makeText(getApplicationContext(), "Register Berhasil", Toast.LENGTH_SHORT).show();
                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "Register Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Password Tidak Sama", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        // TOMBOL BACK
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

}