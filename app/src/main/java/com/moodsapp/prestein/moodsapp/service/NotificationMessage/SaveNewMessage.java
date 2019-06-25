package com.moodsapp.prestein.moodsapp.service.NotificationMessage;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.moodsapp.prestein.moodsapp.Application.MoodsApp;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.RecentChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.ItemNotification;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.model.ResentChats;

public class SaveNewMessage extends Service {
    public static final String ACTION_NEW_MESSAGE_RECEIVED =SaveNewMessage.class.getName() ;
    private ItemNotification initData;


    @Override
    public void onCreate() {
        super.onCreate();
        initData = ExtractedStrings.extraIntentItemNotificationMessage;
        if (initData!=null){
            Message newMessage=new Message();
            newMessage.msgId=initData.msgId;
            newMessage.msgType=initData.msgType;
            newMessage.idRoom=initData.idRoom;
            newMessage.idSender =initData.idSender;
            newMessage.idReceiver =initData.idReceiver;
            newMessage.text = initData.text;
            newMessage.timestamp = Long.parseLong(initData.timestamp);
            newMessage.PhotoDeviceUrl=initData.PhotoDeviceUrl;
            newMessage.PhotoStringBase64=initData.PhotoStringBase64;
            newMessage.VideoDeviceUrl=initData.VideoDeviceUrl;
            newMessage.VoiceDeviceUrl=initData.VoiceDeviceUrl;
            newMessage.DocumentDeviceUrl=initData.DocumentDeviceUrl;
            newMessage.AnyMediaUrl=initData.AnyMediaUrl;
            newMessage.AnyMediaStatus=initData.AnyMediaStatus;
            newMessage.msg_reprayed_message = initData.msg_reprayed_message;
            newMessage.msg_reprayed_id=initData.msg_reprayed_id;
            newMessage.messageStatus="-";
            AllChatsDB.getInstance(getApplicationContext()).CkeckBeforeAddMessage(getApplicationContext(),newMessage,true);
            ResentChats resentChats=new ResentChats();
            resentChats.id=initData.idSender;
            resentChats.name= FriendDB.getInstance(getApplicationContext()).getInfoByIdUser(1,initData.idSender,getApplicationContext());
            resentChats.idSender=initData.idSender;
            resentChats.lastMessage=initData.msgId;/*initData.msgType.equals(ExtractedStrings.ITEM_MESSAGE_TEXT_TYPE) ? initData.text:
            initData.msgType.equals(ExtractedStrings.ITEM_MESSAGE_CONTACT_TYPE ) ? ExtractedStrings.RECENT_CHAT_MESSAGE_CONTACT :
                    initData.msgType.equals(ExtractedStrings.ITEM_MESSAGE_PHOTO_TYPE) ? ExtractedStrings.RECENT_CHAT_MESSAGE_PHOTO :
                            initData.msgType.equals(ExtractedStrings.ITEM_MESSAGE_VOICE_TYPE) ? ExtractedStrings.RECENT_CHAT_MESSAGE_VOICE:
                                    initData.msgType.equals(ExtractedStrings.ITEM_MESSAGE_DOCUMENT_TYPE) ? ExtractedStrings.RECENT_CHAT_MESSAGE_DOCUMENT:
                                            initData.msgType.equals(ExtractedStrings.ITEM_MESSAGE_APK_TYPE) ? ExtractedStrings.RECENT_CHAT_MESSAGE_APK:
                                                    ExtractedStrings.RECENT_CHAT_MESSAGE_VIDEO;*/
            resentChats.small_profile_image=FriendDB.getInstance(getApplicationContext()).getInfoByIdUser(3,initData.idSender,getApplicationContext());
            resentChats.unReadMessage="";
            resentChats.status=FriendDB.getInstance(getApplicationContext()).getInfoByIdUser(2,initData.idSender,getApplicationContext());
            resentChats.timeStamps=Long.parseLong(initData.timestamp);
            RecentChatsDB.getInstance(getApplicationContext()).checkBeforeAdd(resentChats,getApplicationContext(),initData.idSender);
          //  if (MoodsApp.isChatActivityVisibility() || MoodsApp.isHomeActivityVisibility()){
                sendBroadcastMessage();
          //  }
          //  FirebaseDatabase.getInstance().getReference().child("Notifications/"+ExtractedStrings.UID+"/"+initData.msgId).removeValue();
            stopSelf();
        }
        else {
            if (MoodsApp.isChatActivityVisibility() || MoodsApp.isHomeActivityVisibility()){
                sendBroadcastMessage();
            }
           stopSelf();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void sendBroadcastMessage() {
        Intent intent = new Intent(ACTION_NEW_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
