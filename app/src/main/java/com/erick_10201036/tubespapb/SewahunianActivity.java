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

public class SewahunianActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    protected Cursor cursor;
    String[] sno_hp, hNama, hAlamat, hharga;
    DBHelper dbkost;
    TextView nama, no_hp, nama_hunian, alamat_hunian, harga_hunian;
    Button selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewahunian);

        dbkost = new DBHelper(this);
        SQLiteDatabase db = dbkost.getReadableDatabase();

//        CURSOR DATA KOST
                cursor = db.rawQuery("SELECT * FROM hunian WHERE nama_hunian = ? " , new String[]{getIntent().getStringExtra("nama_hunian")} );
        hNama = new String[cursor.getCount()];
        hAlamat = new String[cursor.getCount()];
        hharga = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            hNama[i] = cursor.getString(1);
            hAlamat[i] = cursor.getString(3);
            hharga[i] = cursor.getString(2);
        }

        selesai = findViewById(R.id.selesaiHunian);
//
        nama_hunian = (TextView) findViewById(R.id.txtNmHunian);
        alamat_hunian = (TextView) findViewById(R.id.txtalmtHunian);
        harga_hunian = (TextView) findViewById(R.id.txtHrgHunian);


        final  String selectionnama = hNama[0];
        final  String selectionalamat = hAlamat[0];
        final  String selectionharga = hharga[0];

//        UBAH ISI DATA DIRI
//        nama.setText("Nama Diri  : " + getIntent().getStringExtra("nama_last"));
//        no_hp.setText("No HP        : "+selection);

//        UBAH ISI DATA HUNIAN
        nama_hunian.setText("Nama hunian    : " + selectionnama);
        alamat_hunian.setText("Alamat hunian  : " + selectionalamat);
        harga_hunian.setText("Harga hunian    : " + selectionharga);

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogitem = {"Sewa Hunian", "Cari Partner"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SewahunianActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                String nama_orang = getIntent().getStringExtra("nama_last");
                                Boolean daftar = dbkost.riwayat(selectionnama,selectionalamat,nama_orang, selectionharga, "huniar", "bayar","");
                                Intent i = new Intent(SewahunianActivity.this, RiwayatActivity.class);
                                i.putExtra("nama_akhir", getIntent().getStringExtra("nama_last"));
                                i.putExtra("nama_kost", selectionnama);
                                i.putExtra("alamat_kost", selectionalamat);
                                i.putExtra("harga_kost", selectionharga);
                                startActivity(i);
                                break;
                            }
                            case 1 :{
                                String nama_orang = getIntent().getStringExtra("nama_last");
                                Boolean daftar = dbkost.riwayat(selectionnama, selectionalamat, nama_orang ,selectionharga, "hunian", "partner","Belum ada");
                                Intent i = new Intent(SewahunianActivity.this, RiwayatPartnerActivity.class);
                                i.putExtra("nama_akhir", getIntent().getStringExtra("nama_last"));
                                i.putExtra("nama_kost", selectionnama);
                                i.putExtra("alamat_kost", selectionalamat);
                                i.putExtra("harga_kost", selectionharga);
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
        Toolbar toolbar = findViewById(R.id.tbSewaHunian);
        toolbar.setTitle("Detail Sewa Hunian");
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