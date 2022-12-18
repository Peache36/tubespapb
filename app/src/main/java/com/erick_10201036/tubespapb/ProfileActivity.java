package com.erick_10201036.tubespapb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    Button btn_riwayat, btn_logouts, btn_riwayatpartner;
    TextView nama_user;
    DBHelper dbprof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btn_riwayat = (Button) findViewById(R.id.btn_riwayatku);
        btn_logouts = (Button) findViewById(R.id.btn_logout);
        btn_riwayatpartner = (Button) findViewById(R.id.btn_riwayatpartner);
        nama_user = (TextView) findViewById(R.id.txtnmprofile);

        nama_user.setText(getIntent().getStringExtra("nama_akhir"));

        btn_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent riwayatIntent = new Intent(getApplicationContext(), RiwayatActivity.class);
                riwayatIntent.putExtra("nama_akhir", getIntent().getStringExtra("nama_akhir"));
                startActivity(riwayatIntent);
            }
        });

        btn_riwayatpartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent partnerIntent = new Intent(getApplicationContext(), RiwayatPartnerActivity.class);
                partnerIntent.putExtra("nama_akhir", getIntent().getStringExtra("nama_akhir"));
                startActivity(partnerIntent);
            }
        });

        btn_logouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(loginIntent);

                    finish();
            }
        });




        setupToolbar();
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbProfil);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                i.putExtra("nama_user",getIntent().getStringExtra("nama_akhir"));
                startActivity(i);
            }
        });
    }
}