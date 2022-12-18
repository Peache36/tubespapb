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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SewapartnerActivity extends AppCompatActivity {
    protected Cursor cursor;
    String[] sno_hp, sNama, sAlamat, sharga, sPenyewa;
    DBHelper dbkost;
    TextView nama_penyewa, no_hp, nama_tempat, alamat_tempat, harga_tempat, nama_partner;
    Button selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewapartner);

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

        cursor = dk.rawQuery("SELECT * FROM riwayat WHERE id = ? ",  new String[]{getIntent().getStringExtra("id")});
        sNama = new String[cursor.getCount()];
        sAlamat = new String[cursor.getCount()];
        sharga = new String[cursor.getCount()];
        sPenyewa = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            sNama[i] = cursor.getString(2);
            sAlamat[i] = cursor.getString(3);
            sharga[i] = cursor.getString(4);
            sPenyewa[i] = cursor.getString(1);
        }

        nama_penyewa = (TextView) findViewById(R.id.txtNmPenyewaSewaPartner);
        no_hp = (TextView) findViewById(R.id.txtnohppeyewaSewaPartner);
        nama_tempat = (TextView) findViewById(R.id.txtNmTempatSewaPartner);
        alamat_tempat = (TextView) findViewById(R.id.txtalmttempatSewaPartner);
        harga_tempat = (TextView) findViewById(R.id.txtHrgTempatSewaPartner);
        selesai = (Button) findViewById(R.id.btn_jadipartner);

        nama_penyewa.setText("Nama Diri  : " + sPenyewa[0]);
        no_hp.setText("No HP        : "+ sno_hp[0]);

        int a = Integer.parseInt(getIntent().getStringExtra("urutan"));

        nama_tempat.setText("Nama Tempat    : " + sNama[a]);
        alamat_tempat.setText("Alamat Tempat  : " + sAlamat[a]);
        harga_tempat.setText("Harga Tempat    : "+ sharga[a]);

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogitem = {"Gabung Partner"};
                int ID = Integer.parseInt( getIntent().getStringExtra("id"));
                AlertDialog.Builder builder = new AlertDialog.Builder(SewapartnerActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                String nama_orang = getIntent().getStringExtra("nama_penyewa");
                                Boolean daftar = dbkost.partner(nama_orang , ID);
                                Intent i = new Intent(SewapartnerActivity.this, RiwayatPartnerActivity.class);

                                i.putExtra("nama_akhir", getIntent().getStringExtra("nama_penyewa"));
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

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbSewaPartner);
        toolbar.setTitle("Detail Gabung Partner");
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