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

import java.util.ArrayList;

public class HunianActivity extends AppCompatActivity {
    protected Cursor cursor;
    String[] daftarhunian, hargahunian;
    DBHelper dbhunian;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
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
        setContentView(R.layout.activity_hunian);

        dbhunian = new DBHelper(this);
        bottomNavigationView = findViewById(R.id.bottom_nav_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);

        RefreshHunian();
    }

    public void RefreshHunian() {
        SQLiteDatabase db = dbhunian.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM hunian",null);
        daftarhunian = new String[cursor.getCount()];
        hargahunian = new String[cursor.getCount()];
        cursor.moveToFirst();
        arrayList = new ArrayList<String>();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            daftarhunian[i] = cursor.getString(1);
            hargahunian[i] = cursor.getString(2);
        }
        listView = (ListView) findViewById(R.id.hunian_list);

        searchView = (SearchView) findViewById(R.id.menu_cari_hunian);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, daftarhunian){
            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(daftarhunian[position]);
                text2.setText("RP " + hargahunian[position]);

                return view;
            }
        };
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
                final  String selection = daftarhunian[arg2];
                final CharSequence[] dialogitem = {"Lihat Hunian"};
                AlertDialog.Builder builder = new AlertDialog.Builder(HunianActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                Intent i = new Intent(HunianActivity.this, SewahunianActivity.class);
                                i.putExtra("nama_last", getIntent().getStringExtra("nama_akhir"));
                                i.putExtra("nama_hunian", selection);
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