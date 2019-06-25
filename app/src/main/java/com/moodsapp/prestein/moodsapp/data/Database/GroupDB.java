package com.moodsapp.prestein.moodsapp.data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.model.Group;
import com.moodsapp.prestein.moodsapp.model.ListGroup;
import com.moodsapp.prestein.moodsapp.util.MediaUtils.ConvertTypeMedia;

import java.util.ArrayList;

public final class GroupDB {
    private static GroupsDBHelper mDbHelper = null;

    private static GroupDB instance = null;
    private GroupDB() {

    }
    public static GroupDB getInstance(Context context,String GroupTableName) {
        if (instance == null) {
            instance = new GroupDB();
            mDbHelper = new GroupsDBHelper(context,GroupTableName);
        }
        return instance;
    }
    private static long addGroups(Group group, String groupTableName) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_ID, group.UserId);
        values.put(FeedEntry.COLUMN_NAME_NAME, group.UserName);
        values.put(FeedEntry.COLUMN_NAME_STATUS, group.UserStatus);
        values.put(FeedEntry.COLUMN_NAME_ROLE, group.UserRole);
        values.put(FeedEntry.COLUMN_NAME_AVATA, group.UserImage);
        // Insert the new row, returning the primary key value of the new row
        return db.insert(groupTableName, null, values);
    }
    private static long updateGroups(Context c, Group group, String User_id, String groupTableName) {
        SQLiteDatabase db=null;
        ContentValues values=null;
        String where= FeedEntry.COLUMN_NAME_ID+"="+"'"+User_id+"'";
        try {
            db= mDbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            values = new ContentValues();
            values.put(FeedEntry.COLUMN_NAME_NAME, group.UserName);
            values.put(FeedEntry.COLUMN_NAME_STATUS, group.UserStatus);
            values.put(FeedEntry.COLUMN_NAME_ROLE, group.UserRole);
            values.put(FeedEntry.COLUMN_NAME_AVATA, group.UserImage);
        }catch (Exception e){
            Toast.makeText(c, e.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }
        return db.update(groupTableName, values, where, null);
    }
    public ArrayList<String> getListInfoByPosition(int position,String groupTableName) {
        ArrayList<String> listGroups = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        try {
            Cursor cursor = db.rawQuery("select * from " + groupTableName, null);
            while (cursor.moveToNext()) {
                String image = cursor.getString(position);
                listGroups.add(image);
            }
            cursor.close();
        }catch (Exception e){
            return listGroups;
        }
        return listGroups;
    }
    public ArrayList<Group> getListGroup(String groupTableName) {
        ArrayList<Group> listGroups = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        try {
            Cursor cursor = db.rawQuery("select * from " + groupTableName, null);
            while (cursor.moveToNext()) {
                Group group = new Group();
                group.UserId = cursor.getString(0);
                group.UserName = cursor.getString(1);
                group.UserStatus = cursor.getString(2);
                group.UserRole = cursor.getString(3);
                group.UserImage = cursor.getString(4);
                listGroups.add(group);
            }
            cursor.close();
        }catch (Exception e){
            return listGroups;
        }
        return listGroups;
    }

    public ListGroup getListGroups(String groupTableName) {
        ListGroup listGroups = new ListGroup();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        try {
            Cursor cursor = db.rawQuery("select * from " + groupTableName, null);
            while (cursor.moveToNext()) {
                Group group = new Group();
                group.UserId = cursor.getString(0);
                group.UserName = cursor.getString(1);
                group.UserStatus = cursor.getString(2);
                group.UserRole = cursor.getString(3);
                group.UserImage = cursor.getString(4);
                listGroups.getListGroup().add(group);
            }
            cursor.close();
        }catch (Exception e){
            return new ListGroup();
        }
        return listGroups;
    }
    public void checkBeforeAddGroup(Group group, String id,Context context,String groupTableName) {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        try {
            Cursor c=db.rawQuery("select * from "+ groupTableName+" where "+ FeedEntry.COLUMN_NAME_ID + "='" + id + "'",null);
            if (c.moveToNext()){
                updateGroups(context,group,id,groupTableName);
            }else{
                addGroups(group,groupTableName);
            }
        }catch (Exception e)
        {
            try {
                createTable(db,groupTableName);
                addGroups(group,groupTableName);
            }catch (Exception r){

            }


        }
    }
    public void dropDB(String groupTableName){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        deleteTable(db,groupTableName);
        createTable(db,groupTableName);
    }


    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        static final String COLUMN_NAME_ID = "friendID";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_STATUS = "status";
        static final String COLUMN_NAME_ROLE = "role";
        static final String COLUMN_NAME_AVATA = "image";
}
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static void createTable(SQLiteDatabase db,String groupTableName){
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + groupTableName + " (" +
                        FeedEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_ROLE + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_AVATA + TEXT_TYPE + " )";
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    private static void deleteTable(SQLiteDatabase db,String groupTableName){
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + groupTableName;
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    private static class GroupsDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "groups_info_store.db";
        String groupTableName;
        GroupsDBHelper(Context context, String groupTableName) {
            super(context, ConvertTypeMedia.CreateFileForDatabase(context, Data_Storage_Path.USERS_PROFILE_STORAGE_PATH) + DATABASE_NAME, null, DATABASE_VERSION);
            this.groupTableName=groupTableName;
        }

        public void onCreate(SQLiteDatabase db) {
            createTable(db,groupTableName);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            deleteTable(db,groupTableName);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
