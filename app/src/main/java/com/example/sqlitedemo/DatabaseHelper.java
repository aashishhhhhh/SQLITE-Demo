package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "customer_table";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_AGE = "customer_age";
    public static final String ACTIVE_CUSTOMER = "active_customer";
    public static final String ID = "id";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    // this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CreateTableQuery = "create table " + CUSTOMER_TABLE + " (" + ID + " integer primary key autoincrement, " + CUSTOMER_NAME + " text, " + CUSTOMER_AGE + " int, " + ACTIVE_CUSTOMER + " bool)";
        db.execSQL(CreateTableQuery);

    }

    // this is called if the database version number changes. It version users apps from breaking when you change the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_NAME,customerModel.getName());
        cv.put(CUSTOMER_AGE,customerModel.getAge());
        cv.put(ACTIVE_CUSTOMER,customerModel.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert==-1)
            return false;
        else
            return true;
    }

    public boolean deleteOne(CustomerModel customerModel){
        //find customer model in the database. if it found, delete it and return it and return true.
        //if it is not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "Delete From " + CUSTOMER_TABLE+ "Where "+ID+" = "+ customerModel.getId();

        final Cursor cursor;
        cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }
        else
            return false;
    }

    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();
        // get data from the database

        String queryString = "SELECT * FROM "+ CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        final Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            //loop through the results and create new customer object. Put them into list
            do {
                int customerId = cursor.getInt(0);
                String cutomerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3)==1? true: false;

                CustomerModel customerModel = new CustomerModel(customerId,cutomerName,customerAge,customerActive);
                returnList.add(customerModel);
            }while (cursor.moveToNext());
        }
        else {

        }

        cursor.close();
        db.close();
        return returnList;
    }


}
