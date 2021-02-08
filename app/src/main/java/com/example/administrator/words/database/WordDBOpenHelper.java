package com.example.administrator.words.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-19
 * <p>
 * Summary:
 * <p>
 * api path:
 */
public class WordDBOpenHelper extends SQLiteOpenHelper {


    public WordDBOpenHelper(@Nullable Context context) {
        super(context, "words_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE words(id integer primary key autoincrement," +
                "word varchar(30)," +
                "en_pronunciation varchar(255)," +
                "us_pronunciation varchar(255)," +
                "translate,isComplete,isCollect)");
              //  "translate,isComplete,isCollect)");
        db.execSQL("CREATE TABLE users(id integer primary key autoincrement," +
                "userid varchar(30)," +
                "name varchar(30)," +
                "password varchar(30)," +
                "user_logo varchar(255)," +
                "personal_txt varchar(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
