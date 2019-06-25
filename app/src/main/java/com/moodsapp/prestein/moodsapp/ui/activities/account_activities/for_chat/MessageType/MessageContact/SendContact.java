package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.moodsapp.prestein.moodsapp.ActivityRunningStatus.Chat_Activity_Status;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.util.AudioUtils.PlaySound;

import java.util.ArrayList;

/**
 * Created by Eric prestein on 1/27/2018.
 */

public class SendContact {
    public void saveContact(final Context context, String ContactList, String User_id, ArrayList<CharSequence> idFriends, ArrayList<ContactItemSelect> listSelectedContact) {
        Toast.makeText(context, String.valueOf(listSelectedContact.size()), Toast.LENGTH_SHORT).show();
    StringBuilder contactIfo= new StringBuilder();
    if (listSelectedContact.size()==1){
        contactIfo = new StringBuilder(listSelectedContact.get(0).getName());
    }else if (listSelectedContact.size()==2){
      contactIfo=new StringBuilder(listSelectedContact.get(0).getName()+" and "+listSelectedContact.get(1).getName());
    }else if (listSelectedContact.size()>2){
        contactIfo=new StringBuilder(listSelectedContact.get(0).getName()+" and "+listSelectedContact.size()+" More contacts");
    }

    try {
            String msgId = User_id+ ExtractedStrings.UID+String.valueOf(System.currentTimeMillis());
            final Message newMessage = new Message();
            newMessage.msgId = msgId;
            newMessage.msgType = ExtractedStrings.ITEM_MESSAGE_CONTACT_TYPE;
            newMessage.idRoom=ExtractedStrings.UID+User_id;
            newMessage.idSender = ExtractedStrings.UID;
            newMessage.idReceiver = User_id;
            newMessage.text = ContactList;
            newMessage.timestamp = System.currentTimeMillis();
            newMessage.PhotoDeviceUrl = "-";
            newMessage.PhotoStringBase64 = "-";
            newMessage.VideoDeviceUrl = "-";
            newMessage.VoiceDeviceUrl = "-";
            newMessage.DocumentDeviceUrl =contactIfo.toString();
            newMessage.AnyMediaUrl = "-";
            newMessage.AnyMediaStatus ="-";
            newMessage.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
            newMessage.msg_reprayed_id = ExtractedStrings.MESSAGE_REPLAYED_ID;
            newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SAVED;
            AllChatsDB.getInstance(context).AddMessage(context, newMessage,false);
            new PlaySound(context, R.raw.messagesended);
            ExtractedStrings.MESSAGE_REPLAYED_ID = "";
            ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT = "";
            Chat_Activity_Status.isNewImageMessageSent = true;
            for (CharSequence s:idFriends){
                if (!String.valueOf(s).equals(ExtractedStrings.UID)){
                    FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(String.valueOf(s))).child(msgId).setValue(newMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            new PlaySound(context, R.raw.messagesent);
                            newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SENT;
                            AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SAVED;
                            AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
                        }
                    });
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "In Save image \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

