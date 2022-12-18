package com.erick_10201036.tubespapb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class KostActivity extends AppCompatActivity {
    protected Cursor cursor;
    String[] daftarkost, hargakost;
    DBHelper dbkost;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    SearchView searchView;
    BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_home :
                    Intent dashIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                    dashIntent.putExtra("nama_user",getIntent().getStringExtra("nama_akhir"));
                    startActivity(dashIntent);
                    finish();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost);
        bottomNavigationView = findViewById(R.id.bottom_nav_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);


        dbkost = new DBHelper(this);

        RefreshKost();
    }

    public void RefreshKost(){
        SQLiteDatabase db = dbkost.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM kost",null);
        daftarkost = new String[cursor.getCount()];
        hargakost = new String[cursor.getCount()];
        cursor.moveToFirst();

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            daftarkost[i] = cursor.getString(1);
            hargakost[i] = cursor.getString(2);

        }

        listView = (ListView) findViewById(R.id.kost_list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, hargakost){
            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(daftarkost[position]);
                text2.setText("RP " + hargakost[position]);

                return view;
            }
        };

        searchView = (SearchView) findViewById(R.id.menu_cari_kost);
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

        listView.setAdapter(arrayAdapter);
        listView.setSelected(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final  String selection = daftarkost[arg2];
                final CharSequence[] dialogitem = {"Lihat Kost"};
                AlertDialog.Builder builder = new AlertDialog.Builder(KostActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                Intent i = new Intent(KostActivity.this, SewakostActivity.class);
                                i.putExtra("nama_last", getIntent().getStringExtra("nama_akhir"));
                                i.putExtra("nama_kost", selection);
                                startActivity(i);
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });

        ((ArrayAdapter) listView.getAdapter()).notifyDataSetInvalidated();
    }
}