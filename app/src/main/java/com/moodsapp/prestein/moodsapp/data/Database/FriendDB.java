package com.moodsapp.prestein.moodsapp.data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.model.ListFriend;
import com.moodsapp.prestein.moodsapp.util.MediaUtils.ConvertTypeMedia;

import java.util.ArrayList;

public final class FriendDB {
    private static FriendDBHelper mDbHelper = null;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FriendDB() {
    }

    private static FriendDB instance = null;

    public static FriendDB getInstance(Context context) {
        if (instance == null) {
            instance = new FriendDB();
            mDbHelper = new FriendDBHelper(context);
        }
        return instance;
    }
    private void addFriend(Friend friend) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_ID, friend.id);
        values.put(FeedEntry.COLUMN_NAME_NAME, friend.name);
        values.put(FeedEntry.COLUMN_NAME_STATUS, friend.status);
        values.put(FeedEntry.COLUMN_NAME_AVATA, friend.image);
        values.put(FeedEntry.COLUMN_NAME_COUNTRY, friend.country);
        // Insert the new row, returning the primary key value of the new row
        db.enableWriteAheadLogging();
        db.insert(FeedEntry.TABLE_NAME, null, values);
    }
    private void updateFriend(Context c, Friend friend, String User_id) {
        SQLiteDatabase db=null;
        ContentValues values=null;
        String where= FeedEntry.COLUMN_NAME_ID+"="+"'"+User_id+"'";
        db= mDbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            values = new ContentValues();
            values.put(FeedEntry.COLUMN_NAME_NAME, friend.name);
            values.put(FeedEntry.COLUMN_NAME_STATUS, friend.status);
            values.put(FeedEntry.COLUMN_NAME_AVATA, friend.image);
            values.put(FeedEntry.COLUMN_NAME_COUNTRY, friend.country);
        db.enableWriteAheadLogging();
        db.update(FeedEntry.TABLE_NAME, values, where, null);
    }
    public void addListFriend(ListFriend listFriend){
        for(Friend friend: listFriend.getListFriend()){
            addFriend(friend);
        }
    }
    public ArrayList<String> getListFriendId(Context context) {
        ArrayList<String> listFriendId = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        try {
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                listFriendId.add(cursor.getString(0));
            }
            cursor.close();
        }catch (Exception e){
            return listFriendId;
        }
        return listFriendId;
    }

   public ArrayList<Friend> getListFriendArray() {
       ArrayList<Friend> listFriend = new ArrayList<>();
    SQLiteDatabase db = mDbHelper.getReadableDatabase();
    // Define a projection that specifies which columns from the database
// you will actually use after this query.
    try {
        Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Friend friend = new Friend();
            friend.id = cursor.getString(0);
            friend.name = cursor.getString(1);
            friend.status = cursor.getString(2);
            friend.image = cursor.getString(3);
            friend.country=cursor.getString(4);
            listFriend.add(friend);
        }
        cursor.close();
    }catch (Exception e){
        return new ArrayList<Friend>();
    }
    return listFriend;
    }
    public ListFriend getListFriend() {
        ListFriend listFriend = new ListFriend();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        try {
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                Friend friend = new Friend();
                friend.id = cursor.getString(0);
                friend.name = cursor.getString(1);
                friend.status = cursor.getString(2);
                friend.image = cursor.getString(3);
                friend.country=cursor.getString(4);
                listFriend.getListFriend().add(friend);
            }
            cursor.close();
        }catch (Exception e){
            return new ListFriend();
        }
        return listFriend;
    }
    public void dropDB(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public String getInfoByIdUser(int position, String uId,Context context)
    {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        String Image=null;
        try {
            Cursor c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME+" where "+ FeedEntry.COLUMN_NAME_ID + "='" + uId + "'",null);
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
    public String getImageByIdUser(String uId,Context context)
    {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        String Image=null;
        try {
            Cursor c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME+" where "+ FeedEntry.COLUMN_NAME_ID + "='" + uId + "'",null);
            if(c.moveToNext())
            {
                Image=c.getString(3);
            }
        }catch (Exception e)
        {
            Toast.makeText(context, "/////"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_LONG).show();
        }
        return Image;
    }

    public void checkBeforeAdd(Friend friend, String id,Context context){
       try {
           SQLiteDatabase db=mDbHelper.getReadableDatabase();
           Cursor c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME+" where "+ FeedEntry.COLUMN_NAME_ID + "='" + id + "'",null);
           if (c.moveToNext()){
               updateFriend(context,friend,id);
           }else{
               addFriend(friend);
           }
       }catch (OutOfMemoryError e){
           e.printStackTrace();
       }catch (Exception e){
         e.printStackTrace();
        }

    }
/*    public boolean isThisUserExit(String uId,Context context)
    {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        try {
            Cursor c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME+" where "+ FeedEntry.COLUMN_NAME_ID + "='" + uId + "'",null);
            return c.moveToNext();
        }catch (Exception e)
        {
            Toast.makeText(context, "/////"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_LONG).show();
        }
        return false;
    }*/
    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "friend";
        static final String COLUMN_NAME_ID = "friendID";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_STATUS = "status";
        static final String COLUMN_NAME_AVATA = "image";
        static final String COLUMN_NAME_COUNTRY = "country";
    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_AVATA+ TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_COUNTRY  + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    private static class FriendDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "users_profile_store.db";

        FriendDBHelper(Context context) {
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
