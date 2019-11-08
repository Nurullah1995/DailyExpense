package com.example.dailyexpence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "ExpenseDb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Expense (Id Integer primary key autoincrement,TypeId int,Amount text,Date text,Time text,Document text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Long insertData(int typeId,String amount,String date,String time,String document)
    {

        ContentValues values=new ContentValues();
        values.put("TypeId",typeId);
        values.put("Amount",amount);
        values.put("Date",date);
        values.put("Time",time);
        values.put("Document",document);
        Long lastRow=getWritableDatabase().insert("Expense",null,values);
        return lastRow;
    }

    public Cursor viewData()
    {
        Cursor cursor= getReadableDatabase().rawQuery("select * from Expense",null);

        return cursor;
    }
    public int updateData(int id,int typeId,String amount,String date,String time,String document)
    {
        ContentValues values=new ContentValues();
        values.put("Id",id);
        values.put("TypeId",typeId);
        values.put("Amount",amount);
        values.put("Date",date);
        values.put("Time",time);
        values.put("Document",document);
        int lastRow=getWritableDatabase().update("Expense",values,"Id=?",new String[]{String.valueOf(id)});
        return lastRow;
    }
    public int deleteData(int id) {
        int lastRow=getWritableDatabase().delete("Expense","Id=?",new String[]{String.valueOf(id)});
        return lastRow;
    }

}
