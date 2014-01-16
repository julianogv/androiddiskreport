package com.julianogv.androiddiskreport.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by juliano.vieira on 15/01/14.
 */
public class FileDbHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "fileData";
    public static final String DATABASE = "androiddiskreport";
    public static final int VERSION = 1;

    public FileDbHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table fileData( "
                +"id integer primary key autoincrement, "
                +"path text not null, "
                +"isDirectory integer not null, "
                +"length integer, "
                +"parentId integer, "
                +"FOREIGN KEY(parentId) REFERENCES fileData(id));");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ((newVersion - oldVersion) > 2) {
            db.execSQL("drop table if exists fileData");
            onCreate(db);
        }
    }
}
