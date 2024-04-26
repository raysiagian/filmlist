package com.example.tugasakhir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Film.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_FILMLIST = "filmlist";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_JUDUL = "judul";
    private static final String COLUMN_DESKRIPSI = "deskripsi";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTableQuery = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT)";

        String createFilmListTableQuery = "CREATE TABLE " + TABLE_FILMLIST + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_JUDUL + " TEXT, "
                + COLUMN_DESKRIPSI + " TEXT)";

        db.execSQL(createUsersTableQuery);
        db.execSQL(createFilmListTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMLIST);
        onCreate(db);
    }

    public boolean insertData(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean checkEmailPassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean insertFilm(String judul, String deskripsi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_JUDUL, judul);
        contentValues.put(COLUMN_DESKRIPSI, deskripsi);
        long result = db.insert(TABLE_FILMLIST, null, contentValues);
        return result != -1;
    }

    public ArrayList<Film> getAllFilms() {
        ArrayList<Film> filmArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FILMLIST, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String judul = cursor.getString(cursor.getColumnIndex(COLUMN_JUDUL));
                String deskripsi = cursor.getString(cursor.getColumnIndex(COLUMN_DESKRIPSI));
                Film film = new Film(id, judul, deskripsi);
                filmArrayList.add(film);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return filmArrayList;
    }

    public Film getFilmById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FILMLIST, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Film film = null;
        if (cursor != null && cursor.moveToFirst()) {
            String judul = cursor.getString(cursor.getColumnIndex(COLUMN_JUDUL));
            String deskripsi = cursor.getString(cursor.getColumnIndex(COLUMN_DESKRIPSI));
            film = new Film(id, judul, deskripsi);
            cursor.close();
        }
        return film;
    }


    public boolean updateFilm(int id, String judul, String deskripsi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_JUDUL, judul);
        contentValues.put(COLUMN_DESKRIPSI, deskripsi);
        int result = db.update(TABLE_FILMLIST, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deleteFilm(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_FILMLIST, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
