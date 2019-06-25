package com.moodsapp.prestein.moodsapp.data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.model.ResentChats;
import com.moodsapp.prestein.moodsapp.util.MediaUtils.ConvertTypeMedia;

import java.util.ArrayList;

public final class AllChatsDB extends Thread{
    private static AllChatsDBHelper mDbHelper = null;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private AllChatsDB() {
    }

    private static AllChatsDB instance = null;

    public static AllChatsDB getInstance(Context context) {
        if (instance == null) {
            instance = new AllChatsDB();
            mDbHelper = new AllChatsDBHelper(context);
        }
        return instance;
    }

    public void updateChats(Context x, Message message, String msgID) {
        SQLiteDatabase db=null;
        ContentValues contentValues1=null;
        String where= FeedEntry.COLUMN_MESSAGE_ID+"="+"'"+msgID+"'";
        // String table=table_name.startsWith("moodApp")? table_name: "moodApp"+table_name;
        try {
            db= mDbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            contentValues1 = new ContentValues();
            contentValues1.put(FeedEntry.COLUMN_MESSAGE_TYPE, message.msgType);
            contentValues1.put(FeedEntry.COLUMN_ID_ROOM,message.idRoom);
            contentValues1.put(FeedEntry.COLUMN_ID_SENDER,message.idSender);
            contentValues1.put(FeedEntry.COLUMN_ID_RECEIVER,message.idReceiver);
            contentValues1.put(FeedEntry.COLUMN_MESSAGE_TEXT,message.text);
            contentValues1.put(FeedEntry.COLUMN_PHOTO_DEVICE_URL, message.PhotoDeviceUrl);
            contentValues1.put(FeedEntry.COLUMN_PHOTO_STRING_BASE64, message.PhotoStringBase64);
            contentValues1.put(FeedEntry.COLUMN_VOICE_DEVICE_URL,message.VoiceDeviceUrl);
            contentValues1.put(FeedEntry.COLUMN_VIDEO_DEVICE_URL,message.VideoDeviceUrl);
            contentValues1.put(FeedEntry.COLUMN_DOCUMENT_DEVICE_URL,message.DocumentDeviceUrl);
            contentValues1.put(FeedEntry.COLUMN_MESSAGE_ANY_MEDIA_URL,message.AnyMediaUrl);
            contentValues1.put(FeedEntry.COLUMN_MESSAGE_ANY_MEDIA_STATUS,message.AnyMediaStatus);
            contentValues1.put(FeedEntry.COLUMN_TIME_STAMP,message.timestamp);
            contentValues1.put(FeedEntry.COLUMN_MESSAGE_REPLAYED_ID,message.msg_reprayed_id);
            contentValues1.put(FeedEntry.COLUMN_MESSAGE_REPLAYED_MESSAGE,message.msg_reprayed_message);
            contentValues1.put(FeedEntry.COLUMN_MESSAGE_STATUS, message.messageStatus);
            // Insert the new row, returning the primary key value of the new row
        }catch (Exception e){
            Toast.makeText(x,"in Updation"+ e.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }
        assert db != null;
        db.update(FeedEntry.TABLE_NAME, contentValues1, where, null);
    }
    public void AddMessage(Context x, Message message,Boolean isFromFriend) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(FeedEntry.COLUMN_MESSAGE_ID, message.msgId);
        contentValues1.put(FeedEntry.COLUMN_MESSAGE_TYPE, message.msgType);
        contentValues1.put(FeedEntry.COLUMN_ID_ROOM,message.idRoom);
        contentValues1.put(FeedEntry.COLUMN_ID_SENDER,message.idSender);
        contentValues1.put(FeedEntry.COLUMN_ID_RECEIVER,message.idReceiver);
        contentValues1.put(FeedEntry.COLUMN_MESSAGE_TEXT,message.text);
        contentValues1.put(FeedEntry.COLUMN_PHOTO_DEVICE_URL, message.PhotoDeviceUrl);
        contentValues1.put(FeedEntry.COLUMN_PHOTO_STRING_BASE64, message.PhotoStringBase64);
        contentValues1.put(FeedEntry.COLUMN_VOICE_DEVICE_URL,message.VoiceDeviceUrl);
        contentValues1.put(FeedEntry.COLUMN_VIDEO_DEVICE_URL,message.VideoDeviceUrl);
        contentValues1.put(FeedEntry.COLUMN_DOCUMENT_DEVICE_URL,message.DocumentDeviceUrl);
        contentValues1.put(FeedEntry.COLUMN_MESSAGE_ANY_MEDIA_URL,message.AnyMediaUrl);
        contentValues1.put(FeedEntry.COLUMN_MESSAGE_ANY_MEDIA_STATUS,message.AnyMediaStatus);
        contentValues1.put(FeedEntry.COLUMN_TIME_STAMP,message.timestamp);
        contentValues1.put(FeedEntry.COLUMN_MESSAGE_REPLAYED_ID,message.msg_reprayed_id);
        contentValues1.put(FeedEntry.COLUMN_MESSAGE_REPLAYED_MESSAGE,message.msg_reprayed_message);
        contentValues1.put(FeedEntry.COLUMN_MESSAGE_STATUS, message.messageStatus);
        // Insert the new row, returning the primary key value of the new
        db.insert(FeedEntry.TABLE_NAME, null, contentValues1);
        if (!isFromFriend){
            UpdateRecentChat(x,message.idReceiver,message.msgType,message.idSender, message.msgId, String.valueOf(message.timestamp));
        }
    }
    public String getInfoByMsgId(int position, String msgId,Context context)
    {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        String Image=null;
        try {
            Cursor c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME+" where "+ FeedEntry.COLUMN_MESSAGE_ID + "='" + msgId + "'",null);
            if(c.moveToNext())
            {
                Image=c.getString(position);
            }
        }catch (Exception e)
        {
            return "";
        }
        return Image;
    }
    public void DeleteMessages(Context context,String idSender,String idReceiver,ArrayList<String> msg){
        if (msg.isEmpty()){
            Toast.makeText(context, "No selected message,Please select at least one message to delete.", Toast.LENGTH_SHORT).show();
        }else {
            for (String s:msg){
                DeleteMessage(context,s);
            }
            if (getListMessages(context,idSender,idReceiver).getListMessageData().size()<=0){
                RecentChatsDB.getInstance(context).DeleteMessage(context,idSender);
            }else {
                Message message=getListMessages(context,idSender,idReceiver).getListMessageData().get(getListMessages(context,idSender,idReceiver).getListMessageData().size()-1);
               if (message.idSender.equals(ExtractedStrings.UID)){
                   UpdateRecentChat(context,message.idReceiver,message.msgType,message.idSender, message.text, String.valueOf(message.timestamp));
               }else {
                   UpdateRecentChat(context,message.idSender,message.msgType,message.idSender, message.text, String.valueOf(message.timestamp));
               }

            }
        }

    }
    public long DeleteMessage(Context context,String s){
        try {
            SQLiteDatabase db=mDbHelper.getWritableDatabase();
            String where= FeedEntry.COLUMN_MESSAGE_ID+"="+"'"+s+"'";
            return db.delete(FeedEntry.TABLE_NAME,where,null);
        }catch (Exception g){
            Toast.makeText(context, "In Delete Message" + g.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return 0;
    }
    public void UpdateRecentChat(Context context,
                                  String id,
                                  String msgType,
                                  String idSender,
                                  String lastMessage,
                                  String timeStamp){

        ResentChats resentChats=new ResentChats();
        resentChats.id=id;
        resentChats.name=id.startsWith("GROUP")?GroupListDB.getInstance(context).getGroupInfoByIdId(1,id,context):FriendDB.getInstance(context).getInfoByIdUser(1,id,context);
        resentChats.idSender=idSender;
        resentChats.lastMessage=lastMessage;
        resentChats.small_profile_image=id.startsWith("GROUP")?GroupListDB.getInstance(context).getGroupInfoByIdId(2,id,context):FriendDB.getInstance(context).getInfoByIdUser(3,id,context);
        resentChats.unReadMessage="";
        resentChats.status=id.startsWith("GROUP")? "NONE":FriendDB.getInstance(context).getInfoByIdUser(2,id,context);
        resentChats.timeStamps=Long.parseLong(timeStamp);
        RecentChatsDB.getInstance(context).checkBeforeAdd(resentChats,context,id);
    }
    public void CkeckBeforeAddMessage(Context context, Message message,Boolean isFromFriend)
    {
       try {
           if(getMessageId().contains(message.msgId))
            {
                updateChats(context,message,message.msgId);
            }else {
                AddMessage(context,message,isFromFriend);
            }
        }catch (Exception e)
        {
            System.out.println("hhhhhhhhhhhhhhhhhh error:"+e.getMessage());
//            Toast.makeText(context, "in coming start up"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_LONG).show();
        }

    }
    private ArrayList<String> getMessageId(){
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor c=null;
        ArrayList<String> idMsgLists=new ArrayList<>();
        c=db.rawQuery("select * from "+ FeedEntry.TABLE_NAME,null);
        while (c.moveToNext()){
            idMsgLists.add(c.getString(0));
        }
        return idMsgLists;
    }
    public Consersation getMediaListMessages(Context context, String idSender, String idReceiver) {
        Consersation listMessages = new Consersation();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            String condition=" where "+ FeedEntry.COLUMN_ID_ROOM + "='" + idReceiver+idSender + "' OR "+FeedEntry.COLUMN_ID_ROOM + "='" + idSender+idReceiver+ "'";
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME + condition, null);
            while (cursor.moveToNext()) {
                if (isMediaType(cursor.getString(1))){
                    Message message = new Message();
                    message.msgId = cursor.getString(0);
                    message.msgType = cursor.getString(1);
                    message.idRoom = cursor.getString(2);
                    message.idSender = cursor.getString(3);
                    message.idReceiver = cursor.getString(4);
                    message.text = cursor.getString(5);
                    message.PhotoDeviceUrl = cursor.getString(6);
                    message.PhotoStringBase64 = cursor.getString(7);
                    message.VoiceDeviceUrl = cursor.getString(8);
                    message.VideoDeviceUrl = cursor.getString(9);
                    message.DocumentDeviceUrl = cursor.getString(10);
                    message.AnyMediaUrl = cursor.getString(11);
                    message.AnyMediaStatus = cursor.getString(12);
                    message.timestamp =Long.parseLong(cursor.getString(13));
                    message.msg_reprayed_id = cursor.getString(14);
                    message.msg_reprayed_message = cursor.getString(15);
                    message.messageStatus=cursor.getString(16);
                    listMessages.getListMessageData().add(message);
                }
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(context, "In getMessages", Toast.LENGTH_SHORT).show();
        }
        return listMessages;
    }
    private boolean isMediaType(String s){
     return s.equals(ExtractedStrings.ITEM_MESSAGE_PHOTO_TYPE) || s.equals(ExtractedStrings.ITEM_MESSAGE_VIDEO_TYPE);
    }
    public Consersation getListMessages(Context context, String idSender, String idReceiver) {
        Consersation listMessages = new Consersation();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        // String table=tName.startsWith("moodApp")? tName: "moodApp"+tName;
        try {
            String condition=" where "+ FeedEntry.COLUMN_ID_ROOM + "='" + idReceiver+idSender + "' OR "+FeedEntry.COLUMN_ID_ROOM + "='" + idSender+idReceiver+ "'";
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME + condition, null);
            while (cursor.moveToNext()) {
                Message message = new Message();
                message.msgId = cursor.getString(0);
                message.msgType = cursor.getString(1);
                message.idRoom = cursor.getString(2);
                message.idSender = cursor.getString(3);
                message.idReceiver = cursor.getString(4);
                message.text = cursor.getString(5);
                message.PhotoDeviceUrl = cursor.getString(6);
                message.PhotoStringBase64 = cursor.getString(7);
                message.VoiceDeviceUrl = cursor.getString(8);
                message.VideoDeviceUrl = cursor.getString(9);
                message.DocumentDeviceUrl = cursor.getString(10);
                message.AnyMediaUrl = cursor.getString(11);
                message.AnyMediaStatus = cursor.getString(12);
                message.timestamp =Long.parseLong(cursor.getString(13));
                message.msg_reprayed_id = cursor.getString(14);
                message.msg_reprayed_message = cursor.getString(15);
                message.messageStatus=cursor.getString(16);
                listMessages.getListMessageData().add(message);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(context, "In getMessages", Toast.LENGTH_SHORT).show();
        }
        return listMessages;
    }

    public void dropDB(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /* Inner class that defines the table contents */
    private static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME= "AllMessageTB";
        static final String COLUMN_MESSAGE_ID = "msgId";
        static final String COLUMN_MESSAGE_TYPE = "msgType";
        static final String COLUMN_ID_ROOM = "idRoom";
        static final String COLUMN_ID_SENDER = "idSender";
        static final String COLUMN_ID_RECEIVER = "idReceiver";
        static final String COLUMN_MESSAGE_TEXT = "text";
        static final String COLUMN_PHOTO_DEVICE_URL = "PhotoDeviceUrl";
        static final String COLUMN_PHOTO_STRING_BASE64 = "PhotoStringBase64";
        static final String COLUMN_VOICE_DEVICE_URL = "VoiceDeviceUrl";
        static final String COLUMN_VIDEO_DEVICE_URL = "VideoDeviceUrl";
        static final String COLUMN_DOCUMENT_DEVICE_URL = "DocumentDeviceUrl";
        static final String COLUMN_MESSAGE_ANY_MEDIA_URL = "mediaUrl";
        static final String COLUMN_MESSAGE_ANY_MEDIA_STATUS= "mediaStatus";
        static final String COLUMN_TIME_STAMP = "timestamp";
        static final String COLUMN_MESSAGE_REPLAYED_ID = "msg_reprayed_id";
        static final String COLUMN_MESSAGE_REPLAYED_MESSAGE= "msg_reprayed_message";
        static final String COLUMN_MESSAGE_STATUS="msg_status";
    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_MESSAGE_ID + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_MESSAGE_TYPE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_ID_ROOM + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_ID_SENDER + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_ID_RECEIVER + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_MESSAGE_TEXT + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_PHOTO_DEVICE_URL + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_PHOTO_STRING_BASE64 + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_VOICE_DEVICE_URL + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_VIDEO_DEVICE_URL + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_DOCUMENT_DEVICE_URL + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_MESSAGE_ANY_MEDIA_URL + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_MESSAGE_ANY_MEDIA_STATUS + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_TIME_STAMP + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_MESSAGE_REPLAYED_ID + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_MESSAGE_REPLAYED_MESSAGE + TEXT_TYPE + COMMA_SEP+
                    FeedEntry.COLUMN_MESSAGE_STATUS + TEXT_TYPE +" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    private static class AllChatsDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "AllMyChats.db";

        AllChatsDBHelper(Context context) {
            super(context, ConvertTypeMedia.CreateFileForDatabase(context, Data_Storage_Path.ALL_CHAT_MESSAGE_PATH) + DATABASE_NAME, null, DATABASE_VERSION);
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
    public void run (){

    }
}
