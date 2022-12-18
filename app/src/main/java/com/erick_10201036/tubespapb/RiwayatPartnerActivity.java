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
import android.widget.Toast;

public class RiwayatPartnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DBHelper dbpartner;
    protected Cursor cursor;
    String[] daftarpartner, hargakost, id_ku;
    ListView PartnerListView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayatpartner);

        dbpartner = new DBHelper(this);

        RefreshPartner();

        setupToolbar();
    }

    public void RefreshPartner() {
        SQLiteDatabase db = dbpartner.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM riwayat WHERE nama_penyewa = ? OR nama_partner = ? AND status = ? ",  new String[]{getIntent().getStringExtra("nama_akhir"),getIntent().getStringExtra("nama_akhir"), "partner"});
        daftarpartner = new String[cursor.getCount()];
        hargakost = new String[cursor.getCount()];
        id_ku = new String[cursor.getCount()];

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            daftarpartner[i] = cursor.getString(2);
            hargakost[i] = cursor.getString(4);
            id_ku[i] = cursor.getString(0);
        }

        PartnerListView = (ListView) findViewById(R.id.partner_list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, hargakost){
            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(daftarpartner[position]);
                text2.setText("RP " + hargakost[position]);

                return view;
            }
        };

        PartnerListView.setAdapter(arrayAdapter);
        PartnerListView.setSelected(true);

        PartnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final  String selection = daftarpartner[arg2];
                String a = String.valueOf(arg2);
                final  String selection2 = id_ku[arg2];
                final CharSequence[] dialogitem = {"Lihat Detail Riwayat"};
                AlertDialog.Builder builder = new AlertDialog.Builder(RiwayatPartnerActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                Intent i = new Intent(RiwayatPartnerActivity.this, DetailsewapartnerActivity.class);
                                i.putExtra("nama_penyewa", getIntent().getStringExtra("nama_akhir"));
                                i.putExtra("nama_kost", selection);
                                i.putExtra("urutan", a);
                                i.putExtra("id", selection2);
                                startActivity(i);
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });

        ((ArrayAdapter) PartnerListView.getAdapter()).notifyDataSetInvalidated();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbRiwayatpartner);
        toolbar.setTitle("Riwayat Partner");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(getApplicationContext(), DashboardActivity.class);
                x.putExtra("nama_user", getIntent().getStringExtra("nama_akhir"));
                startActivity(x);
            }
        });
    }
}