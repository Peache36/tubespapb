package com.erick_10201036.tubespapb;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {
    DBHelper db;
    Button logout, btn_kost, btn_hunian, btn_partner;
    TextView nama_user;
    BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()){

                case R.id.menu_profile:
                    Intent profilIntent = new Intent(DashboardActivity.this, ProfileActivity.class);
                    profilIntent.putExtra("nama_akhir",getIntent().getStringExtra("nama_user"));
                    startActivity(profilIntent);
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = new DBHelper(this);
//        logout = (Button) findViewById(R.id.dash_logout);
        btn_kost = (Button) findViewById(R.id.cari_kost);
        btn_hunian = (Button) findViewById(R.id.cari_hunian);
        btn_partner = (Button) findViewById(R.id.cari_partner);
        nama_user = (TextView) findViewById(R.id.dash_welcome);

        bottomNavigationView = findViewById(R.id.bottom_nav_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);

        nama_user.setText("Selamat pagi, " + getIntent().getStringExtra("nama_user"));




        Boolean checkSession = db.checkSession("kosong");
        if(checkSession == true){
            Intent loginIntent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        //LOGOUT
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Boolean updateSession = db.upgradeSession("ada", 1);
//                if (updateSession == true){
//                    Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
//                    Intent loginIntent = new Intent(DashboardActivity.this, LoginActivity.class);
//                    startActivity(loginIntent);
//                    finish();
//                }
//            }
//        });

        btn_kost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kostIntent = new Intent(DashboardActivity.this, KostActivity.class);
                kostIntent.putExtra("nama_akhir",getIntent().getStringExtra("nama_user"));
                startActivity(kostIntent);
            }
        });

        btn_hunian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hunianIntent = new Intent(DashboardActivity.this, HunianActivity.class);
                hunianIntent.putExtra("nama_akhir",getIntent().getStringExtra("nama_user"));
                startActivity(hunianIntent);
            }
        });

        btn_partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent partnerIntent = new Intent(DashboardActivity.this, PartnerActivity.class);
                partnerIntent.putExtra("nama_akhir",getIntent().getStringExtra("nama_user"));
                startActivity(partnerIntent);
            }
        });


    }
}