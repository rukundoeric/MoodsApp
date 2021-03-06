package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.AudioUtils.PlaySound;

import java.util.ArrayList;

/**
 * Created by Prestein on 3/2/2018.
 */

public class SendTextMessage {
    private Chat_Activity chat_activity;
    private Context context;

    public SendTextMessage(Chat_Activity chat_activity, Context context) {
        this.chat_activity = chat_activity;
        this.context = context;
    }

    public SendTextMessage(Context context) {
        this.context = context;
    }

    public void SendMessage(String User_id, String message, ArrayList<CharSequence> idFriends){
        String msgId=User_id+ ExtractedStrings.UID+String.valueOf(System.currentTimeMillis());
        final Message newMessage = new Message();
        newMessage.msgId=msgId;
        newMessage.msgType= ExtractedStrings.ITEM_MESSAGE_TEXT_TYPE;
        newMessage.idRoom= ExtractedStrings.UID+User_id;
        newMessage.idSender = ExtractedStrings.UID;
        newMessage.idReceiver = User_id;
        newMessage.text = message;
        newMessage.timestamp = System.currentTimeMillis();
        newMessage.PhotoDeviceUrl="-";
        newMessage.PhotoStringBase64="-";
        newMessage.VideoDeviceUrl="-";
        newMessage.VoiceDeviceUrl="-";
        newMessage.DocumentDeviceUrl="-";
        newMessage.AnyMediaUrl="-";
        newMessage.AnyMediaStatus="-";
        newMessage.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
        newMessage.msg_reprayed_id= ExtractedStrings.MESSAGE_REPLAYED_ID;
        newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_STILL_SENDING;
        AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
        new PlaySound(context, R.raw.messagesended);
        for (CharSequence s:idFriends){
            if (!String.valueOf(s).equals(ExtractedStrings.UID)){
                FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(String.valueOf(s))).child(msgId).setValue(newMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new PlaySound(context, R.raw.messagesent);
                        newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SENT;
                        AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
                        if (chat_activity!=null){
                            chat_activity.UpdateAdapterChats(false);
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SAVED;
                        AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
                        if (chat_activity!=null){
                            chat_activity.UpdateAdapterChats(false);
                        }
                    }
                });
            }
        }
            if (chat_activity!=null){
               chat_activity.mChatMessageText.setText("");
               chat_activity.UpdateAdapterChats(false);
            }

        ExtractedStrings.MESSAGE_REPLAYED_ID="";
        ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";
    }
}
