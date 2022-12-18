package com.erick_10201036.tubespapb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SewakostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    protected Cursor cursor;
    String[] sno_hp, sNama, sAlamat, sharga;
    DBHelper dbkost;
    TextView nama, no_hp, nama_kost, alamat_kost, harga_kost;
    Button selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewakost);

        dbkost = new DBHelper(this);

        SQLiteDatabase dk = dbkost.getReadableDatabase();
////        CURSOR NAMA & NO HP IDENTITAS
//        cursor = dk.rawQuery("SELECT no_hp FROM user WHERE username = ? " , new String[]{getIntent().getStringExtra("nama_last")} );
//        sno_hp = new String[cursor.getCount()];
//        cursor.moveToFirst();
//        for(int i = 0; i < cursor.getCount(); i++){
//            cursor.moveToPosition(i);
//            sno_hp[i] = cursor.getString(0);
//        }

//        CURSOR DATA KOST
        cursor = dk.rawQuery("SELECT * FROM kost WHERE nama_kost = ? " , new String[]{getIntent().getStringExtra("nama_kost")} );
        sNama = new String[cursor.getCount()];
        sAlamat = new String[cursor.getCount()];
        sharga = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            sNama[i] = cursor.getString(1);
            sAlamat[i] = cursor.getString(3);
            sharga[i] = cursor.getString(2);
        }

        selesai = findViewById(R.id.selesaiKost);
//        nama = (TextView) findViewById(R.id.txtName);
//        no_hp = (TextView) findViewById(R.id.txtTlp);
        nama_kost = (TextView) findViewById(R.id.txtNmKost);
        alamat_kost = (TextView) findViewById(R.id.txtalmtKost);
        harga_kost = (TextView) findViewById(R.id.txtHrgKost);


        final  String selection1 = sNama[0];
        final  String selection2 = sAlamat[0];
        final  String selection3 = sharga[0];

////        UBAH ISI DATA DIRI
//        nama.setText("Nama Diri  : " + getIntent().getStringExtra("nama_last"));
//        no_hp.setText("No HP        : "+selection);

//        UBAH ISI DATA KOST
        nama_kost.setText("Nama Kost    : " + selection1);
        alamat_kost.setText("Alamat Kost  : " + selection2);
        harga_kost.setText("Harga Kost    : " + selection3);

//        TOMBOL SEWA
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogitem = {"Sewa Kost", "Cari Partner"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SewakostActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                String nama_orang = getIntent().getStringExtra("nama_last");
                                Boolean daftar = dbkost.riwayat(selection1, selection2, nama_orang ,selection3, "kost", "bayar","");
                                Intent i = new Intent(SewakostActivity.this, RiwayatActivity.class);
                                i.putExtra("nama_akhir", getIntent().getStringExtra("nama_last"));
                                i.putExtra("nama_kost", selection1);
                                i.putExtra("alamat_kost", selection2);
                                i.putExtra("harga_kost", selection3);
                                startActivity(i);
                                break;
                            }
                            case 1 :{
                                String nama_orang = getIntent().getStringExtra("nama_last");
                                Boolean daftar = dbkost.riwayat(selection1, selection2, nama_orang ,selection3, "kost", "partner","Belum ada");
                                Intent i = new Intent(SewakostActivity.this, RiwayatPartnerActivity.class);
                                i.putExtra("nama_akhir", getIntent().getStringExtra("nama_last"));
                                i.putExtra("nama_kost", selection1);
                                i.putExtra("alamat_kost", selection2);
                                i.putExtra("harga_kost", selection3);
                                startActivity(i);
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });
        setupToolbar();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbSewaKost);
        toolbar.setTitle("Detail Sewa Kost");
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