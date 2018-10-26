package com.example.emzo.aplikasimodul2kel27;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {

    private ListView listView;
    private DatabaseHandler MyDatabase;
    private ArrayList<String> ListData;

    // static variable
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "modulandroid";

    // table name
    private static final String TABLE_MAHASISWA = "mahasiswa";

    // column tables
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "username";
    private static final String KEY_TALL = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        getSupportActionBar().setTitle("Daftar Mahasiswa");
        listView = findViewById(R.id.list);
        ListData = new ArrayList<>();
        MyDatabase = new DatabaseHandler(getBaseContext());
        getData();
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListData));
    }

    //Berisi Statement-Statement Untuk Mengambi Data dari Database
    @SuppressLint("Recycle")
    private void getData(){
        //Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery( "SELECT  * FROM " + TABLE_MAHASISWA,null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for(int count=0; count < cursor.getCount(); count++){

            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir

            ListData.add(cursor.getString(1));//Menambil Data Dari Kolom 1 (Nama)
            //Lalu Memasukan Semua Datanya kedalam ArrayList
        }
    }
}