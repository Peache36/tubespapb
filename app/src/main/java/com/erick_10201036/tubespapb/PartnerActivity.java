package com.erick_10201036.tubespapb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PartnerActivity extends AppCompatActivity {
    protected Cursor cursor;
    String[] daftarpartner, hargatempat, namapenyewa, id ;
    DBHelper dbpartner;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    TextView nama;
    SearchView searchView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);

        dbpartner = new DBHelper(this);

        RefreshPartner();
    }

    public void RefreshPartner() {
        SQLiteDatabase db = dbpartner.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM riwayat WHERE nama_penyewa != ? AND status = ? AND nama_partner = ? ", new String[]{ getIntent().getStringExtra("nama_akhir"), "partner", "Belum ada"});
        daftarpartner = new String[cursor.getCount()];
        hargatempat = new String[cursor.getCount()];
        namapenyewa = new String[cursor.getCount()];
        id = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            id[i] = cursor.getString(0);
            daftarpartner[i] = cursor.getString(2);
            hargatempat[i] = cursor.getString(4);
            namapenyewa[i] = cursor.getString(7);
        }

        listView = (ListView) findViewById(R.id.kost_list);
        searchView = (SearchView) findViewById(R.id.menu_cari_partner);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, daftarpartner){
            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(daftarpartner[position]);
                text2.setText("RP " + hargatempat[position]);

                return view;
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final  String selection = daftarpartner[arg2];
                String a = String.valueOf(arg2);
                final  String selection2 = id[arg2];
                final CharSequence[] dialogitem = {"Lihat Tempat"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PartnerActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                Intent i = new Intent(PartnerActivity.this, SewapartnerActivity.class);
                                i.putExtra("nama_penyewa", getIntent().getStringExtra("nama_akhir"));
                                i.putExtra("id", selection2);
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

        listView.setAdapter(arrayAdapter);
        listView.setSelected(true);
        ((ArrayAdapter) listView.getAdapter()).notifyDataSetInvalidated();
    }
}