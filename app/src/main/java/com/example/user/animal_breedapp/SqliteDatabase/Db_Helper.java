package com.example.user.animal_breedapp.SqliteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ${Mina} on 17/06/2018.
 */

public class Db_Helper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "book.db";
    private static final int DATABASE_VERSION = 1;

    public Db_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + DatabaseContract.Categorie.TABLE_NAME + " ("
                + DatabaseContract.Categorie._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.Categorie.U_COLUMN_NAME + " TEXT NOT NULL,"
                + DatabaseContract.Categorie.U_COLUMN_image +" TEXT NOT NULL" + ");";


        db.execSQL(SQL_CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


        db.execSQL(" DROP TABLE IF Exists " + DatabaseContract.Categorie.TABLE_NAME);

        onCreate(db);

    }
}

