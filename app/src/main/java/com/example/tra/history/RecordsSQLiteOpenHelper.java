package com.example.tra.history;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordsSQLiteOpenHelper extends SQLiteOpenHelper {
    //声明一个代表数据库名称的字符串
    private final static  String DB_NAME = "MyRecords.db";
    //声明一个代表数据库版本的整形变量
    private static int DB_VERSION = 1;

    public RecordsSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建立records表格
        String sql = "CREATE TABLE IF NOT EXISTS records (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}

