package com.moodsapp.prestein.moodsapp.data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.model.RecentChatRoom;
import com.moodsapp.prestein.moodsapp.model.ResentChats;
import com.moodsapp.prestein.moodsapp.util.MediaUtils.ConvertTypeMedia;

import java.util.ArrayList;

public final class RecentChatsDB {
    private static FriendDBHelper mDbHelper = null;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private RecentChatsDB() {
    }

    private static RecentChatsDB instance = null;

    public static RecentChatsDB getInstance(Context context) {
        if (instance == null) {
            instance = new RecentChatsDB();
            mDbHelper = new FriendDBHelper(context);
        }
        return instance;
    }

    public long addRecentChats(ResentChats resentChats,Context context) {
        SQLiteDatabase db=null;
        ContentValues values=null;
        try {
            db= mDbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            values = new ContentValues();
            values.put(FeedEntry.COLUMN_NAME_ID, resentChats.id);
            values.put(FeedEntry.COLUMN_NAME_NAME, resentChats.name);
            values.put(FeedEntry.COLUMN_NAME_STATUS, resentChats.status);
            values.put(FeedEntry.COLUMN_NAME_AVATA, resentChats.small_profile_image);
            values.put(FeedEntry.COLUMN_LAST_MESSAGE, resentChats.lastMessage);
            values.put(FeedEntry.COLUMN_ID_SENDER, resentChats.idSender);
            values.put(FeedEntry.COLUMN_UNREAD_MESSAGE, resentChats.unReadMessage);
            values.put(FeedEntry.COLUMN_TIME_STAMPS, resentChats.timeStamps);
            // Insert the new row, returning the primary key value of the new row
        }catch (Exception e){
            Toast.makeText(context, e.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }
        return db.insert(FeedEntry.TABLE_NAME, null, values);

    }

    public void checkBeforeAdd(ResentChats resentChats,Context context,String userId)
    {

        try {
            if(getRecentChatsId().contains(resentChats.id))
            {
                updateRecentChats(userId,resentChats,context);
            }else {
                addRecentChats(resentChats,context);
            }
        }catch (Exception e)
        {
            System.out.println("Erorrrrrrrrrrrrr:"+e.getMessage());
           // Toast.makeText(context, "/////"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_LONG).show();
        }


    }
    private ArrayList<String> getRecentChatsId(){
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor c=null;
        ArrayList<String> idMsgLists=new ArrayList<>();
        c=db.rawQuery("select * from "+ RecentChatsDB.FeedEntry.TABLE_NAME,null);
        while (c.moveToNext()){
            idMsgLists.add(c.getString(0));
        }
        return idMsgLists;
    }
    public long updateRecentChats(String id,ResentChats resentChats,Context context) {
        SQLiteDatabase db=null;
        ContentValues values=null;
        String where=FeedEntry.COLUMN_NAME_ID+"="+"'"+id+"'";
        try {
            db= mDbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            values = new ContentValues();

            values.put(FeedEntry.COLUMN_NAME_NAME, resentChats.name);
            values.put(FeedEntry.COLUMN_NAME_STATUS, resentChats.status);
            values.put(FeedEntry.COLUMN_NAME_AVATA, resentChats.small_profile_image);
            values.put(FeedEntry.COLUMN_LAST_MESSAGE, resentChats.lastMessage);
            values.put(FeedEntry.COLUMN_ID_SENDER, resentChats.idSender);
            values.put(FeedEntry.COLUMN_UNREAD_MESSAGE, resentChats.unReadMessage);
            values.put(FeedEntry.COLUMN_TIME_STAMPS, resentChats.timeStamps);

            // Insert the new row, returning the primary key value of the new row
        }catch (Exception e){
            Toast.makeText(context, e.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }
        return db.update(FeedEntry.TABLE_NAME, values, where, null);
    }
    public long DeleteMessage(Context context,String s){
        try {
            SQLiteDatabase db=mDbHelper.getWritableDatabase();
            String where= FeedEntry.COLUMN_NAME_ID+"="+"'"+s+"'";
            return db.delete(FeedEntry.TABLE_NAME,where,null);
        }catch (Exception g){
            Toast.makeText(context, "In Delete Message" + g.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return 0;
    }
    public RecentChatRoom getListRecentChats(Context c) {
        RecentChatRoom recentChatRoom = new RecentChatRoom();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor=null;
        try {
            cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                ResentChats resentChats = new ResentChats();
                resentChats.id = cursor.getString(0);
                resentChats.name = cursor.getString(1);
                resentChats.status = cursor.getString(2);
                resentChats.small_profile_image = cursor.getString(3);
                resentChats.lastMessage=cursor.getString(4);
                resentChats.idSender=cursor.getString(5);
                resentChats.unReadMessage=cursor.getString(6);
                resentChats.timeStamps=cursor.getLong(7);
                recentChatRoom.getListRecentChatDataData().add(resentChats);
            }

        }catch (Exception e){
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
            return new RecentChatRoom();
        }
        finally {
            if (cursor!=null)
            {
                cursor.close();
            }
        }
        return recentChatRoom;
    }
    public ArrayList<ResentChats> getListRecentChatsArray(Context c) {
        ArrayList<ResentChats> recentChatRoom = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor=null;
        try {
            cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                ResentChats resentChats = new ResentChats();
                resentChats.id = cursor.getString(0);
                resentChats.name = cursor.getString(1);
                resentChats.status = cursor.getString(2);
                resentChats.small_profile_image = cursor.getString(3);
                resentChats.lastMessage=cursor.getString(4);
                resentChats.idSender=cursor.getString(5);
                resentChats.unReadMessage=cursor.getString(6);
                resentChats.timeStamps=cursor.getLong(7);
                recentChatRoom.add(resentChats);
            }

        }catch (Exception e){
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
        finally {
            if (cursor!=null)
            {
                cursor.close();
            }
        }
        return recentChatRoom;
    }
    public String getImageByIdUser(String uId,Context context)
    {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        String Image=null;
        Cursor c=null;
        try {
            c=db.rawQuery("select * from "+FeedEntry.TABLE_NAME+" where "+FeedEntry.COLUMN_NAME_ID + "='" + uId + "'",null);
            if(c.moveToNext())
            {
                Image=c.getString(4);
            }

        }catch (Exception e)
        {
            Toast.makeText(context, "/////"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_LONG).show();
        }
        finally {
            if (c != null) {
                c.close();
            }
        }

        return Image;
    }
    public void dropDB(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "RecentChatData";
        static final String COLUMN_NAME_ID = "RecentChatID";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_STATUS = "status";
        static final String COLUMN_NAME_AVATA = "image";
        static final String COLUMN_LAST_MESSAGE="lastmesage";
        static final String COLUMN_ID_SENDER="idSender";
        static final String COLUMN_UNREAD_MESSAGE="unReadMessage";
        static final String COLUMN_TIME_STAMPS="timeStamps";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_AVATA + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_LAST_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_ID_SENDER + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_UNREAD_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_TIME_STAMPS + TEXT_TYPE +" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    private static class FriendDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "msg_recent_chat_store.db";

        FriendDBHelper(Context context) {
            super(context, ConvertTypeMedia.CreateFileForDatabase(context,Data_Storage_Path.ALL_CHAT_MESSAGE_PATH) + DATABASE_NAME, null, DATABASE_VERSION);
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
