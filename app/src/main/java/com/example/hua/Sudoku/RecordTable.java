package com.example.hua.Sudoku;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class RecordTable extends SQLiteOpenHelper
{
    public static final String TABLE_NAME;

    static {
        TABLE_NAME = "record";
    }

    public static final String hard_level = "hard_level";
    public static final String record_time = "time";
    public static final String DATABASE_NAME = "record.db";
    public static final int VERSION = 1;




    public RecordTable(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + hard_level + " varchar(20) not null," + record_time+ " varchar(10) not null" + ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
