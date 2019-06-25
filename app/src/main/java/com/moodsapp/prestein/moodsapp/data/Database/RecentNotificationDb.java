package com.moodsapp.prestein.moodsapp.data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.service.RecentNotification.ItemNotifictaion;
import com.moodsapp.prestein.moodsapp.service.RecentNotification.RecentNotificationListClass;

public final class RecentNotificationDb {
    private static NotificationDBHelper mDbHelper = null;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private RecentNotificationDb() {
    }

    private static RecentNotificationDb instance = null;

    public static RecentNotificationDb getInstance(Context context) {
        if (instance == null) {
            instance = new RecentNotificationDb();
            mDbHelper = new NotificationDBHelper(context);
        }
        return instance;
    }


    public long addUnReadMessageNotification(ItemNotifictaion itemNotifictaion) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NOTIFICATION_ID, itemNotifictaion.notificationId);
        values.put(FeedEntry.COLUMN_NOTIFICATION_ICON, itemNotifictaion.notificationIcon);
        values.put(FeedEntry.COLUMN_NOTIFICATION_TITLE, itemNotifictaion.title);
        values.put(FeedEntry.COLUMN_NOTIFICATION_MESSAGE, itemNotifictaion.message);
        values.put(FeedEntry.COLUMN_NOTIFICATION_FROM, itemNotifictaion.senderid);
        // Insert the new row, returning the primary key value of the new row
        return db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    public void removeAllNotification(Context context) {
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.delete(FeedEntry.TABLE_NAME,null,null);
    }

    public RecentNotificationListClass getRecentNotificationListClass() {
        RecentNotificationListClass listNotification = new RecentNotificationListClass();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        try {
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                ItemNotifictaion itemNotifictaion = new ItemNotifictaion();
                itemNotifictaion.notificationId = cursor.getString(0);
                itemNotifictaion.notificationIcon=cursor.getString(1);
                itemNotifictaion.title = cursor.getString(2);
                itemNotifictaion.message = cursor.getString(3);
                itemNotifictaion.senderid = cursor.getString(4);
                listNotification.getListRecentNotification().add(itemNotifictaion);
            }
            cursor.close();
        }catch (Exception e){
            return new RecentNotificationListClass();
        }
        return listNotification;
    }
    public long DeleteMessageNotification(Context context,String s){
        try {
            SQLiteDatabase db=mDbHelper.getWritableDatabase();
            String where=FeedEntry.COLUMN_NOTIFICATION_FROM+"="+"'"+s+"'";
            return db.delete(FeedEntry.TABLE_NAME,where,null);
        }catch (Exception g){
           return 0;
        }
    }
    public int getUnReadMessageCount(Context context, String idSender) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int numberOfUnRead=0;
         try {
            String condition=" where "+ FeedEntry.COLUMN_NOTIFICATION_FROM + "='" + idSender + "'";
            Cursor cursor = db.rawQuery("select * from " +FeedEntry.TABLE_NAME + condition, null);
            while (cursor.moveToNext()) {
                numberOfUnRead++;
            }
            cursor.close();
        }catch (Exception e){
            return 0;
        }
        return numberOfUnRead;
    }
    public void dropDB(Context context){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
        Toast.makeText(context, "Deleted succefull", Toast.LENGTH_SHORT).show();
    }


    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "UnReadMessage";
        static final String COLUMN_NOTIFICATION_ID = "notificationId";
        static final String COLUMN_NOTIFICATION_ICON = "notificationIcon";
        static final String COLUMN_NOTIFICATION_TITLE = "notificationTitle";
        static final String COLUMN_NOTIFICATION_MESSAGE = "notificationMessage";
        static final String COLUMN_NOTIFICATION_FROM = "notificationSenderId";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NOTIFICATION_ID + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_NOTIFICATION_ICON + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NOTIFICATION_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NOTIFICATION_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NOTIFICATION_FROM + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    private static class NotificationDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "UnReadNotificationMessage.db";

        NotificationDBHelper(Context context) {
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
