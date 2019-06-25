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

public class GroupListDB {
    private static GroupListDB.GroupDBHelper mDbHelper = null;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private GroupListDB() {
    }

    private static GroupListDB instance = null;

    public static GroupListDB getInstance(Context context) {
        if (instance == null) {
            instance = new GroupListDB();
            mDbHelper = new GroupListDB.GroupDBHelper(context);
        }
        return instance;
    }

    public void addGroup(Group group) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_GROUP_ID, group.GroupId);
        values.put(FeedEntry.COLUMN_GROUP_NAME, group.GroupName);
        values.put(FeedEntry.COLUMN_GROUP_IMAGE, group.GroupImage);
        db.insert(FeedEntry.TABLE_NAME, null, values);
    }
    public void updateGroup(Group group,String group_id) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String where= FeedEntry.COLUMN_GROUP_ID+"="+"'"+group_id+"'";
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_GROUP_NAME, group.GroupName);
        values.put(FeedEntry.COLUMN_GROUP_IMAGE, group.GroupImage);
        db.update(FeedEntry.TABLE_NAME,values, where,null);
    }
    private ArrayList<String> getGroupId(){
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor c=null;
        ArrayList<String> idMsgLists=new ArrayList<>();
        c=db.rawQuery("select * from "+  FeedEntry.TABLE_NAME,null);
        while (c.moveToNext()){
            idMsgLists.add(c.getString(0));
        }
        return idMsgLists;
    }
    public String getGroupInfoByIdId(int position, String gId,Context context)
    {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        String Image=null;
        try {
            Cursor c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME+" where "+ FeedEntry.COLUMN_GROUP_ID + "='" + gId + "'",null);
            if(c.moveToNext())
            {
                Image=c.getString(position);
            }
        }catch (Exception e)
        {
            return Image;
        }
        return Image;
    }
    public void CheckBeforeAddGroup(Context context, Group group)
    {
        try {
            if(getGroupId().contains(group.GroupId))
            {
                updateGroup(group,group.GroupId);
            }else {
                addGroup(group);
            }
        }catch (Exception e)
        {
            System.out.println("hhhhhhhhhhhhhhhhhh error:"+e.getMessage());
           Toast.makeText(context, "in coming start up"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_LONG).show();
        }

    }
    public void deleteGroup(String idGroup){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(FeedEntry.TABLE_NAME, FeedEntry.COLUMN_GROUP_ID + " = " + idGroup , null);
    }

    public void addListGroup(ArrayList<Group> listGroup) {
        for (Group group : listGroup) {
            addGroup(group);
        }
    }
    public ArrayList<Group> getListGroups() {
        ArrayList<Group> getListGroup = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        try {
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                Group group = new Group();
                group.GroupId = cursor.getString(0);
                group.GroupName = cursor.getString(1);
                group.GroupImage = cursor.getString(2);
                getListGroup.add(group);
            }
            cursor.close();
        }catch (Exception e){
            return new ArrayList<>();
        }
        return getListGroup;
    }
    public ArrayList<Group> getUserCommonListGroups(Context context,String uid) {
        ArrayList<Group> getListGroup = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        try {
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                ArrayList<Group> list = GroupDB.getInstance(context, cursor.getString(0)).getListGroup(cursor.getString(0));
                for (Group group:list){
                   try {
                       if (group.UserId.equals(uid)){
                           Group group1 = new Group();
                           group1.GroupId = cursor.getString(0);
                           group1.GroupName = cursor.getString(1);
                           group1.GroupImage = cursor.getString(2);
                           getListGroup.add(group1);
                       }
                   }catch (NullPointerException e){
                       e.printStackTrace();
                   }
                }
            }
            cursor.close();
        }catch (Exception e){
            return new ArrayList<>();
        }
        return getListGroup;
    }
    /*public ArrayList<Group> getListGroups() {
        Map<String, Group> mapGroup = new HashMap<>();
        ArrayList<String> listKey = new ArrayList<>();
       SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
         try {
            Cursor cursor = db.rawQuery("select * from " + GroupListDB.FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                if (!listKey.contains(idGroup)) {
                    Group newGroup = new Group();
                    newGroup.id = idGroup;
                    newGroup.groupInfo.put("name", nameGroup);
                    newGroup.groupInfo.put("admin", admin);
                    newGroup.groupInfo.put("image", image);
                    newGroup.member.add(member);
                    listKey.add(idGroup);
                    mapGroup.put(idGroup, newGroup);
                } else {
                    mapGroup.get(idGroup).member.add(member);
                }
            }
            cursor.close();
        } catch (Exception e) {
            return new ArrayList<Group>();
        }

        ArrayList<Group> listGroup = new ArrayList<>();
        for (String key : listKey) {
            listGroup.add(mapGroup.get(key));
        }
        return null;
    }
*/
    public void dropDB() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    public static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "groups";
        static final String COLUMN_GROUP_ID = "groupID";
        static final String COLUMN_GROUP_NAME = "name";
        static final String COLUMN_GROUP_IMAGE = "image";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_GROUP_ID + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_GROUP_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_GROUP_IMAGE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GroupListDB.FeedEntry.TABLE_NAME;


    private static class GroupDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "GroupChat.db";

        GroupDBHelper(Context context) {
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
