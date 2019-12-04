package com.example.hua.Sudoku;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class RecordTable extends SQLiteOpenHelper
{
    public static final String TABLE_NAME;

    public static  RecordTable RT;

    static {
        TABLE_NAME = "record";
    }

    public static final String hard_level = "hard_level";
    public static final String record_time = "time";
    public static final String DATABASE_NAME = "record.db";
    public static final int VERSION = 1;


   public static RecordTable Get_Instance(Context context)
   {
       if(RT==null) return new RecordTable(context);
       else return RT;
   }

    private RecordTable(Context context) {
        super(context, DATABASE_NAME, null, 2);
        SQLiteDatabase writableDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("hard_level","简单");
        values.put("time","无");
        writableDatabase.insert("record",null,values);
        values.clear();

        values.put("hard_level","中等");
        values.put("time","无");
        writableDatabase.insert("record",null,values);
        values.clear();

        values.put("hard_level","困难");
        values.put("time","无");
        writableDatabase.insert("record",null,values);
        values.clear();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + hard_level + " varchar(20) not null," + record_time+ " varchar(10) not null" + ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
