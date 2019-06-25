package com.moodsapp.prestein.moodsapp.data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.model.MyProfile;
import com.moodsapp.prestein.moodsapp.util.MediaUtils.ConvertTypeMedia;

public final class MyProfileDB {
    private static MyProfileDBHelper mDbHelper = null;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private MyProfileDB() {
    }

    private static MyProfileDB instance = null;

    public static MyProfileDB getInstance(Context context) {
        if (instance == null) {
            instance = new MyProfileDB();
            mDbHelper = new MyProfileDBHelper(context);
        }
        return instance;
    }
    public long addMyProfile(MyProfile profile) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_ID, profile.profileId);
        values.put(FeedEntry.COLUMN_NAME_NAME, profile.profileName);
        values.put(FeedEntry.COLUMN_NAME_STATUS, profile.profileStatus);
        values.put(FeedEntry.COLUMN_NAME_COUNTRY, profile.profileCountry);
        values.put(FeedEntry.COLUMN_NAME_AVATA, profile.profileImage);
        values.put(FeedEntry.COLUMN_NAME_AVATA_PATH, profile.profileImagePath);
        // Insert the new row, returning the primary key value of the new row
        return db.insert(FeedEntry.TABLE_NAME, null, values);
    }
    public long updateMyProfile(Context c,MyProfile profile,String User_id) {
        SQLiteDatabase db=null;
        ContentValues values=null;
        String where= FeedEntry.COLUMN_NAME_ID+"="+"'"+User_id+"'";
        try {
            db= mDbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            values = new ContentValues();
            values.put(FeedEntry.COLUMN_NAME_NAME, profile.profileName);
            values.put(FeedEntry.COLUMN_NAME_STATUS, profile.profileStatus);
            values.put(FeedEntry.COLUMN_NAME_COUNTRY, profile.profileCountry);
            values.put(FeedEntry.COLUMN_NAME_AVATA, profile.profileImage);
            values.put(FeedEntry.COLUMN_NAME_AVATA_PATH, profile.profileImagePath);
        }catch (Exception e){
            Toast.makeText(c, e.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }
        return db.update(FeedEntry.TABLE_NAME, values, where, null);
    }

    public void dropDB(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public String getInfoUser(int position,Context context)
    {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        String Image=null;
        try {
            Cursor c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME,null);
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

    public void checkBeforeAdd(MyProfile friend, String id,Context context){
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        try {
            Cursor c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME+" where "+ FeedEntry.COLUMN_NAME_ID + "='" + id + "'",null);
            if (c.moveToNext()){
                updateMyProfile(context,friend,id);
            }else{
                addMyProfile(friend);
            }
        }catch (Exception e)
        {
            Toast.makeText(context, "/////"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_LONG).show();
        }
    }
    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "myprofileTB";
        static final String COLUMN_NAME_ID ="profileId";
        static final String COLUMN_NAME_NAME = "profileName";
        static final String COLUMN_NAME_STATUS = "profileStatus";
        static final String COLUMN_NAME_COUNTRY = "profileCountry";
        static final String COLUMN_NAME_AVATA = "profileImage";
        static final String COLUMN_NAME_AVATA_PATH = "profileImagePath";
    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_AVATA + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_AVATA_PATH + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    private static class MyProfileDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "myProfile.db";

        MyProfileDBHelper(Context context) {
            super(context, ConvertTypeMedia.CreateFileForDatabase(context, Data_Storage_Path.USERS_PROFILE_STORAGE_PATH) + DATABASE_NAME, null, DATABASE_VERSION);
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
