package com.erick_10201036.tubespapb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsewapartnerActivity extends AppCompatActivity {
    protected Cursor cursor;
    String[] sno_hp, sNama, sAlamat, sharga, sPenyewa, sPartner, snoPartner;
    DBHelper dbkost;
    TextView nama_penyewa, no_hp, nama_tempat, alamat_tempat, harga_tempat, nama_partner, no_partner;
    Button selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsewapartner);

        dbkost = new DBHelper(this);
        SQLiteDatabase dk = dbkost.getReadableDatabase();

        cursor = dk.rawQuery("SELECT no_hp FROM user WHERE username = ? " , new String[]{getIntent().getStringExtra("nama_penyewa")} );
        sno_hp = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            sno_hp[i] = cursor.getString(0);
        }

        cursor = dk.rawQuery("SELECT * FROM riwayat WHERE id = ? ",  new String[]{getIntent().getStringExtra("id")});
        sNama = new String[cursor.getCount()];
        sAlamat = new String[cursor.getCount()];
        sharga = new String[cursor.getCount()];
        sPenyewa = new String[cursor.getCount()];
        sPartner = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            sNama[i] = cursor.getString(2);
            sAlamat[i] = cursor.getString(3);
            sharga[i] = cursor.getString(4);
            sPenyewa[i] = cursor.getString(1);
            sPartner[i] = cursor.getString(7);
        }

        nama_partner = (TextView) findViewById(R.id.txtNmPartnerSewaPartner);
        no_partner = (TextView) findViewById(R.id.txtnohpPartnerSewaPartner);

        nama_penyewa = (TextView) findViewById(R.id.txtNmPenyewaDetailPartner);
        no_hp = (TextView) findViewById(R.id.txtnohppeyewaDetailPartner);
        nama_tempat = (TextView) findViewById(R.id.txtNmTempatDetailPartner);
        alamat_tempat = (TextView) findViewById(R.id.txtalmttempatDetailPartner);
        harga_tempat = (TextView) findViewById(R.id.txtHrgTempatDetailPartner);

        int a = Integer.parseInt(getIntent().getStringExtra("urutan"));

        nama_penyewa.setText("Nama Diri  : " + sPenyewa[0]);
        no_hp.setText("No HP        : "+ sno_hp[0]);

        if(sPartner[0] != "Belum ada"){
            cursor = dk.rawQuery("SELECT no_hp FROM user WHERE username = ? " , new String[]{sPartner[0]} );
            snoPartner = new String[cursor.getCount()];
            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++){
                cursor.moveToPosition(i);
                snoPartner[i] = cursor.getString(0);
            }
                nama_partner.setText("Nama Diri  : " + sPartner[0]);
            if (snoPartner.length != 0) {
                no_partner.setText("No HP        : " + snoPartner[0]);
            } else
                no_partner.setText("No HP        : Belum ada");

        }else {
            nama_partner.setText("Nama Diri  : - "  );
            no_partner.setText("No HP        : - ");
        }



        nama_tempat.setText("Nama Tempat    : " + sNama[0]);
        alamat_tempat.setText("Alamat Tempat  : " + sAlamat[0]);
        harga_tempat.setText("Harga Tempat    : "+ sharga[0]);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbSewaPartner);
        toolbar.setTitle("Detail Riwayat Partner");
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