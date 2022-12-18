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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RiwayatActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DBHelper dbriwayat;
    protected Cursor cursor;
    String[] daftarriwayat, daftarharga;
    ListView RlistView;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        dbriwayat = new DBHelper(this);

        RefreshRiwayat();
    }

    public void RefreshRiwayat() {

        SQLiteDatabase db = dbriwayat.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM riwayat WHERE nama_penyewa = ? AND status = ? ",  new String[]{getIntent().getStringExtra("nama_akhir"), "bayar"});
        daftarriwayat = new String[cursor.getCount()];
        daftarharga = new String[cursor.getCount()];

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            daftarriwayat[i] = cursor.getString(2);
            daftarharga[i] = cursor.getString(4);
        }

        RlistView = findViewById(R.id.list_riwayat);
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, daftarriwayat);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, daftarriwayat){
            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(daftarriwayat[position]);
                text2.setText("RP " + daftarharga[position]);

                return view;
            }
        };
        RlistView.setAdapter(arrayAdapter);
        RlistView.setSelected(true);

        RlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final  String selection = daftarriwayat[arg2];
                String a = String.valueOf(arg2);
                final CharSequence[] dialogitem = {"Lihat Detail Riwayat"};
                AlertDialog.Builder builder = new AlertDialog.Builder(RiwayatActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                Intent i = new Intent(RiwayatActivity.this, DetailriwayatActivity.class);
                                i.putExtra("nama_penyewa", getIntent().getStringExtra("nama_akhir"));
                                i.putExtra("nama_kost", selection);
                                i.putExtra("urutan", a);
                                startActivity(i);
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });

        ((ArrayAdapter) RlistView.getAdapter()).notifyDataSetInvalidated();
        setupToolbar();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbRiwayat);
        toolbar.setTitle("Riwayat");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(getApplicationContext(), DashboardActivity.class);
                x.putExtra("nama_user",getIntent().getStringExtra("nama_akhir"));
                startActivity(x);
            }
        });
    }
}