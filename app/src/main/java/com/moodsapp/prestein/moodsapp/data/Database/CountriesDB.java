package com.moodsapp.prestein.moodsapp.data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.resourceshelper.CountryCode.Countries;

public final class CountriesDB {
    private static CountryDBHelper mDbHelper = null;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private CountriesDB() {
    }

    private static CountriesDB instance = null;

    public static CountriesDB getInstance(Context context) {
        if (instance == null) {
            instance = new CountriesDB();
            mDbHelper = new CountryDBHelper(context);
        }
        return instance;
    }
    public long addCountry(Countries countries) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_COUNTRY_ID, countries.country_name_code);
        values.put(FeedEntry.COLUMN_COUNTRY_NAME, countries.country_name);
        values.put(FeedEntry.COLUMN_PHONE_CODE, countries.country_phone_number);
        // Insert the new row, returning the primary key value of the new row
        return db.insert(FeedEntry.TABLE_NAME, null, values);
    }


    public void dropDB(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public String getInfoByIdCountryName(int position, String country,Context context)
    {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        String Image=null;
        try {
            Cursor c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME+" where "+ FeedEntry.COLUMN_COUNTRY_NAME + "='" + country + "'",null);
            if(c.moveToNext())
            {
                Image=c.getString(position);
            }
        }catch (Exception e)
        {
            Toast.makeText(context, "/////"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_LONG).show();
        }
        return Image;
    }
    public void checkBeforeAdd(Countries countries, String id,Context context){
        if (!isCountryExist(id,context)){
            addCountry(countries);
        }
    }

    public boolean isCountryExist(String cId,Context context)
    {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        boolean True=true;
        try {
           Cursor c=db.rawQuery("select * from "+FeedEntry.TABLE_NAME+" where "+ FeedEntry.COLUMN_COUNTRY_ID + "='" + cId + "'",null);
           return c.moveToNext();
        }catch (Exception e)
        {
            Toast.makeText(context, "/////"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_LONG).show();
        }
        return false;
    }
    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "countries";
        static final String COLUMN_COUNTRY_ID = "nameCode";
        static final String COLUMN_COUNTRY_NAME = "name";
        static final String COLUMN_PHONE_CODE = "phoneCode";

    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_COUNTRY_ID + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_COUNTRY_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_PHONE_CODE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    private static class CountryDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "Countries_code_list.db";

        CountryDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
