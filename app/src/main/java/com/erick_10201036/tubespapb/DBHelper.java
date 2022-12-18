package com.erick_10201036.tubespapb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "loginSQLLite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id integer PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE user(id integer PRIMARY KEY AUTOINCREMENT, username text, password text, no_hp text)");
        db.execSQL("CREATE TABLE kost(id integer PRIMARY KEY AUTOINCREMENT, nama_kost text, harga integer, alamat text)");
        db.execSQL("CREATE TABLE hunian(id integer PRIMARY KEY AUTOINCREMENT, nama_hunian text, harga integer, alamat text)");
        db.execSQL("CREATE TABLE partner(id integer PRIMARY KEY AUTOINCREMENT, nama_penyewa text, nama_tempat text, alamat_tempat text , harga_tempat text , jenis text,  nama_partner text)");
        db.execSQL("CREATE TABLE riwayat(id integer PRIMARY KEY AUTOINCREMENT, nama_penyewa text, nama_tempat text, alamat_tempat text , harga_tempat text , jenis text,  status text, nama_partner text)");
        db.execSQL("INSERT INTO user(username,password,no_hp) VALUES ('admin','12345', '0853-xxxx')");
        db.execSQL("INSERT INTO user(username,password,no_hp) VALUES ('erick','12345', '0853-xxxx')");

        db.execSQL("INSERT INTO kost(nama_kost,harga,alamat) VALUES ('Kost 1', 500000, 'Jl.Kost 1 Balikpapan')");
        db.execSQL("INSERT INTO kost(nama_kost,harga,alamat) VALUES ('Kost 2', 600000, 'Jl.Kost 2 Balikpapan')");
        db.execSQL("INSERT INTO kost(nama_kost,harga,alamat) VALUES ('Kost 3', 700000, 'Jl.Kost 3 Balikpapan')");
        db.execSQL("INSERT INTO kost(nama_kost,harga,alamat) VALUES ('Kost 4', 800000, 'Jl.Kost 4 Balikpapan')");
        db.execSQL("INSERT INTO kost(nama_kost,harga,alamat) VALUES ('Kost 5', 900000, 'Jl.Kost 5 Balikpapan')");
        db.execSQL("INSERT INTO kost(nama_kost,harga,alamat) VALUES ('Kost 6', 1000000, 'Jl.Kost 6 Balikpapan')");

        db.execSQL("INSERT INTO hunian(nama_hunian,harga,alamat) VALUES ('Hunian 1', 500000, 'Jl.Hunian 1 Balikpapan')");
        db.execSQL("INSERT INTO hunian(nama_hunian,harga,alamat) VALUES ('Hunian 2', 600000, 'Jl.Hunian 2 Balikpapan')");

        db.execSQL("INSERT INTO riwayat(nama_penyewa,nama_tempat,alamat_tempat, harga_tempat, jenis, status, nama_partner) VALUES ('erick', 'Hunian 2' , 'Jl.Hunian 2 Balikpapan' ,600000, 'hunian','partner', 'admin' )");
        db.execSQL("INSERT INTO riwayat(nama_penyewa,nama_tempat,alamat_tempat, harga_tempat, jenis, status, nama_partner) VALUES ('erick', 'Hunian 1' , 'Jl.Hunian 2 Balikpapan' ,600000, 'hunian','partner', 'Belum ada' )");

        db.execSQL("INSERT INTO session(id, login) VALUES (1,'kosong')");

    }

//    public List<String> getAllCategories(){
//        List<String> categories = new ArrayList<String>();
//        String selectQuery = "SELECT * FROM kost";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery,null);
//
//        if (cursor.moveToFirst()){
//            do {
//                categories.add(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//
//        return categories;
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS hunian");
        db.execSQL("DROP TABLE IF EXISTS sewa_kost");
        onCreate(db);
    }

    //CHECK SESSION

    public Boolean checkSession(String sessionValues){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE login = ? ", new String[]{sessionValues});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    //UPGRADE SESSION
    public Boolean upgradeSession(String sessionValues, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login",sessionValues);
        long update = db.update("session", contentValues,"id="+id,null);

        if (update == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //INSERT USER
    public Boolean insertUser(String username, String password, String nomorHp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("no_hp", nomorHp);
        long insert = db.insert("user", null, contentValues);

        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //INSERT SEWA TEMPAT
    public Boolean riwayat(String nama_tempat, String alamat_tempat, String nama_penyewa, String harga_tempat, String jenis, String status, String nama_partner){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_tempat", nama_tempat);
        contentValues.put("nama_penyewa", nama_penyewa);
        contentValues.put("alamat_tempat", alamat_tempat);
        contentValues.put("harga_tempat", harga_tempat);
        contentValues.put("jenis", jenis);
        contentValues.put("status", status);
        contentValues.put("nama_partner", nama_partner);
        long insert = db.insert("riwayat", null, contentValues);

        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

//    GABUNG PARTNER
    public Boolean partner(String nama_partner, int idku){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_partner", nama_partner);
        long update = db.update("riwayat", contentValues,"riwayat.id = ? ", new String[]{String.valueOf(idku)});
        if(update == -1){
            return false;
        }
        else {
            db.close();
            return true;
        }
    }

    public Boolean checkLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE  username = ? AND password = ?", new String[]{username,password});
        if (cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }
}
