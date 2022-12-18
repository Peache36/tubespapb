package com.erick_10201036.tubespapb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    DBHelper db;
    Button login;
    EditText username, password;
    ImageView back;
    TextView register;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);

        username = (EditText) findViewById(R.id.edt_username);
        password = (EditText) findViewById(R.id.edt_password);
        login = (Button) findViewById(R.id.R_btn_1);
        back = (ImageView) findViewById(R.id.back_button);
        register = (TextView) findViewById(R.id.R_txt_2);

        preferences = getPreferences(Context.MODE_PRIVATE);


        //LOGIN

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();

                Boolean login = db.checkLogin(strUsername,strPassword);
                if (login == true){
                    Boolean updateSession = db.upgradeSession("ada", 1);
                    if (updateSession == true){
                        Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent dashIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                        dashIntent.putExtra("nama_user", strUsername);
                        startActivity(dashIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Periksa lagi username atau password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // TOMBOL BACK
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });

        // BELUM PUNYA AKUN
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regisIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regisIntent);
                finish();
            }
        });


    }
}