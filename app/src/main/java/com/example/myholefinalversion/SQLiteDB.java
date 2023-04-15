package com.example.myholefinalversion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLiteDB extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASENAME = "Holesome_Table3";
    private static final int DATABASEVERSION = 1;
    private static final String TABLEID = "Id";
    private static final String TABLE_LOCATIONNAME = "Location";
    private static final String TABLE_ADDRESS = "Address";
    private static final String TABLE_CITY = "City";
    private static final String TABLE_COUNTRY = "Country";
    private static final String TABLE_LATITUDE = "Latitude";
    private static final String TABLE_LONGITUDE = "Longitude";

    public SQLiteDB(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                " CREATE TABLE " + DATABASENAME +
                        " (" + TABLEID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TABLE_LOCATIONNAME + " TEXT, " +
                        TABLE_ADDRESS + " TEXT, " +
                        TABLE_CITY + " TEXT, " +
                        TABLE_COUNTRY + " TEXT, " +
                        TABLE_LATITUDE + " TEXT, " +
                        TABLE_LONGITUDE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + DATABASENAME);
        onCreate(db);
    }

    void addHole(String location, String city, String country, String address, String latitude, String longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_LOCATIONNAME, location);
        cv.put(TABLE_CITY, city);
        cv.put(TABLE_COUNTRY, country);
        cv.put(TABLE_ADDRESS, address);
        cv.put(TABLE_LONGITUDE, longitude);
        cv.put(TABLE_LATITUDE, latitude);

        long result = db.insert(DATABASENAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to record", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Recorded successfully", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readData(){
        String query = " SELECT * FROM " + DATABASENAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String location, String city, String country, String address, String latitude, String longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_LOCATIONNAME, location);
        cv.put(TABLE_CITY, city);
        cv.put(TABLE_COUNTRY, country);
        cv.put(TABLE_ADDRESS, address);
        cv.put(TABLE_LONGITUDE, longitude);
        cv.put(TABLE_LATITUDE, latitude);

        long result = db.update(DATABASENAME, cv, "Id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(DATABASENAME, "Id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
