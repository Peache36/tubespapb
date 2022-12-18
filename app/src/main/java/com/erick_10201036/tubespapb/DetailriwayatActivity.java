package com.erick_10201036.tubespapb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailriwayatActivity extends AppCompatActivity {
    protected Cursor cursor;
    String[] sno_hp, sNama, sAlamat, sharga;
    DBHelper dbkost;
    TextView nama, no_hp, nama_kost, alamat_kost, harga_kost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailriwayat);

        dbkost = new DBHelper(this);
        SQLiteDatabase dk = dbkost.getReadableDatabase();
//        CURSOR NAMA & NO HP IDENTITAS

        cursor = dk.rawQuery("SELECT no_hp FROM user WHERE username = ? " , new String[]{getIntent().getStringExtra("nama_penyewa")} );
        sno_hp = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            sno_hp[i] = cursor.getString(0);
        }

        cursor = dk.rawQuery("SELECT * FROM riwayat WHERE nama_penyewa = ? AND status = ? ",  new String[]{getIntent().getStringExtra("nama_penyewa"), "bayar"});
        sNama = new String[cursor.getCount()];
        sAlamat = new String[cursor.getCount()];
        sharga = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            sNama[i] = cursor.getString(2);
            sAlamat[i] = cursor.getString(3);
            sharga[i] = cursor.getString(4);
        }

        nama = (TextView) findViewById(R.id.txtNmPenyewa);
        no_hp = (TextView) findViewById(R.id.txtnohppeyewa);
        nama_kost = (TextView) findViewById(R.id.txtNmKostDetail);
        alamat_kost = (TextView) findViewById(R.id.txtalmtKostDetail);
        harga_kost = (TextView) findViewById(R.id.txtHrgKostDetail);

        nama.setText("Nama Diri  : " + getIntent().getStringExtra("nama_penyewa"));
        no_hp.setText("No HP        : "+ sno_hp[0]);

        int a = Integer.parseInt(getIntent().getStringExtra("urutan"));

        nama_kost.setText("Nama Tempat    : " + sNama[a]);
        alamat_kost.setText("Alamat Tempat  : " + sAlamat[a]);
        harga_kost.setText("Harga Tempat    : "+ sharga[a]);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbDetailHistoriKost);
        toolbar.setTitle("Detail Riwayat");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}