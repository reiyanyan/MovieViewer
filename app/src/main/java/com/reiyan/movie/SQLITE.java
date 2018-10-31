package com.reiyan.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;
import android.util.Log;

import com.reiyan.movie.data.MovieData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SQLITE extends SQLiteOpenHelper {

    public static final int db_versi =1;
    public static final String db_nama = "db_fav";

    public static final String tabel_nama = "fav";

    public static final String kolom_id = "id";
    public static final String kolom_title = "title";
    public static final String kolom_thumbnail = "thumbnail";
    public static final String kolom_desc = "descr";
    public static final String kolom_popularity = "popularity";
    public static final String kolom_date = "date";
    public static final String kolom_rate = "rate";

    String buat_tabel = "CREATE TABLE " + tabel_nama + "(" +
            kolom_id + " INTEGER PRIMARY KEY, " +
            kolom_thumbnail + " TEXT, " +
            kolom_title + " TEXT, " +
            kolom_desc + " TEXT, " +
            kolom_popularity + " TEXT, " +
            kolom_date + " TEXT, " +
            kolom_rate + " TEXT " + ")";

    public SQLITE(Context context) {

        super(context, db_nama, null, db_versi);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(buat_tabel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tabel_nama);
        onCreate(db);
    }

    public long addToDB(MovieData m){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(kolom_id, m.getId());
        values.put(kolom_title, m.getTitle());
        values.put(kolom_thumbnail, m.getThumbnail());
        values.put(kolom_desc, m.getDescription());
        values.put(kolom_popularity, m.getPopularity());
        values.put(kolom_date, m.getReleaseDate());
        values.put(kolom_rate, m.getRate());

        long id = db.insert(tabel_nama, null, values);
        db.close();
        return id;
    }

    public MovieData read(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =db.query(tabel_nama,
                new String[]{kolom_id, kolom_title, kolom_thumbnail, kolom_desc, kolom_popularity, kolom_date, kolom_rate},
                kolom_id + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        MovieData m = new MovieData(
                cursor.getString(cursor.getColumnIndex(kolom_id)),
                cursor.getString(cursor.getColumnIndex(kolom_title)),
                cursor.getString(cursor.getColumnIndex(kolom_thumbnail)),
                cursor.getString(cursor.getColumnIndex(kolom_desc)),
                cursor.getString(cursor.getColumnIndex(kolom_popularity)),
                cursor.getString(cursor.getColumnIndex(kolom_date)),
                cursor.getString(cursor.getColumnIndex(kolom_rate)));
        cursor.close();

        return m;
    }

    public List<MovieData> readAll(){
        List<MovieData> data = new ArrayList<>();
        String query = "select * from " + tabel_nama;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor!= null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()){
            do {
                MovieData m = new MovieData(
                        cursor.getString(cursor.getColumnIndex(kolom_id)),
                        cursor.getString(cursor.getColumnIndex(kolom_title)),
                        cursor.getString(cursor.getColumnIndex(kolom_thumbnail)),
                        cursor.getString(cursor.getColumnIndex(kolom_desc)),
                        cursor.getString(cursor.getColumnIndex(kolom_popularity)),
                        cursor.getString(cursor.getColumnIndex(kolom_date)),
                        cursor.getString(cursor.getColumnIndex(kolom_rate)));
                data.add(m);
            }while (cursor.moveToNext());
        }
    db.close();
        return data;
    }

    public void delete(MovieData data){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tabel_nama, kolom_id + "=?",
                new String[]{String.valueOf(data.getId())});

        db.close();

    }

}
