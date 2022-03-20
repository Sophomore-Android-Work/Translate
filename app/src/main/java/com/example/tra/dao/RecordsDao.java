package com.example.tra.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tra.history.RecordsSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索记录操作类
 * 用于封装度对搜索记录的各种操作
 */
public class RecordsDao {
    RecordsSQLiteOpenHelper recordHelper;  //定义一个数据库的操作帮助对象
    SQLiteDatabase recordDb;               //定义一个记录的数据库对象

    public RecordsDao(Context context){
        recordHelper = new RecordsSQLiteOpenHelper(context);
    }

    /**
     * 该函数用于添加搜索记录
     */
    public void addRecords(String record){
        if (!isHasRecord(record)){     //判断源数据中是否已有该记录
            recordDb = recordHelper.getWritableDatabase();   //获取搜索记录数据库
            ContentValues value = new ContentValues();
            value.put("name",record);
            recordDb.insert("records",null,value);    //插入记录
            recordDb.close();
        }
    }

    /**
     * 该函数用于获取全部搜索记录
     * @return List<String>
     */
    public List<String> getRecordsList(){
        List<String> recordsList = new ArrayList<String>();
        recordDb = recordHelper.getReadableDatabase();
        Cursor cursor = recordDb.query("records",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String record = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            recordsList.add(record);
        }
        recordDb.close();
        cursor.close();
        return recordsList;
    }

    /**
     * 该函数用于清空搜索记录
     */
    public void clearRecords(){
        String sql = "delete from records";
        recordDb = recordHelper.getWritableDatabase();
        recordDb.execSQL(sql);
        recordDb.close();
    }

    /**
     * 该函数用于模糊查询
     */
    public List<String> querySimilarRecords(String record){
        String sql = "select * from records where name like'%" + record + "%' order by name";
        // String queryStr = "select * from records where name like '%" + record + "%' order by name ";
        List<String> similarRecordsList = new ArrayList<String>();
        recordDb = recordHelper.getReadableDatabase();
        Cursor cursor = recordDb.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String myRecord = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            similarRecordsList.add(myRecord);
        }
        recordDb.close();
        cursor.close();
        return similarRecordsList;
    }


    /**
     * 该函数用于判断原数据库中是否已有该记录
     * @return 有记录返回true
     */
    private boolean isHasRecord(String record){
        boolean isHasRecord = false;
        recordDb = recordHelper.getReadableDatabase();
        Cursor cursor = recordDb.query("records",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            if (record.equals(cursor.getString(cursor.getColumnIndexOrThrow("name")))){
                isHasRecord = true;
                break;
            }
        }
        recordDb.close();
        cursor.close();
        return isHasRecord;
    }
}

