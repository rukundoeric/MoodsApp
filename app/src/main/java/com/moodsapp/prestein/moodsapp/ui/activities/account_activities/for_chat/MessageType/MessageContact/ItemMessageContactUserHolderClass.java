package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Telephone;

/**
 * Created by USER on 3/19/2018.
 */

public class ItemMessageContactUserHolderClass {
    private final ImageView messageStatus;
    private Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private Bitmap src;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private LinearLayout mLayoutContactRunder;
    private LinearLayout userContactContainer;
    private EmojiconTextView mContactInfoDisplay;
    private EmojiconTextView mContcatMessageTime;
    private EmojiconTextView mViewAllContactList;
    private CircleImageView mFirstImageContact;
    private CircleImageView mSecondImageContact;
    private CircleImageView mThirdImageContact;
    private View itemView;

    public ItemMessageContactUserHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, LinearLayout mLayoutContactRunder, LinearLayout userContactContainer, EmojiconTextView mContactInfoDisplay, EmojiconTextView mContcatMessageTime, EmojiconTextView mViewAllContactList, CircleImageView mFirstImageContact, CircleImageView mSecondImageContact, CircleImageView mThirdImageContact, ImageView mMessageStatus, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataDB = bitmapAvataDB;
        this.bitmapAvataUser = bitmapAvataUser;
        this.src = src;
        this.position = position;
        this.mLayoutContactRunder = mLayoutContactRunder;
        this.userContactContainer = userContactContainer;
        this.mContactInfoDisplay = mContactInfoDisplay;
        this.mContcatMessageTime = mContcatMessageTime;
        this.mViewAllContactList = mViewAllContactList;
        this.mFirstImageContact = mFirstImageContact;
        this.mSecondImageContact = mSecondImageContact;
        this.mThirdImageContact = mThirdImageContact;
        this.itemView = itemView;
        this.messageStatus=mMessageStatus;
    }
    public void setMessageContactUserHolder(){
        if (position==0){
            userContactContainer.setPadding(Chat_Activity.Divice_with/4,16,2,2);
        }else {
            if (consersation.getListMessageData().get(position-1).idSender.equals(ExtractedStrings.UID)){
                mLayoutContactRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal_ext);
                userContactContainer.setPadding(Chat_Activity.Divice_with/4,2,2,2);
            }  else {
                mLayoutContactRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
                userContactContainer.setPadding(Chat_Activity.Divice_with/4,16,2,2);
            }
        }
        mContactInfoDisplay.setText(consersation.getListMessageData().get(position).DocumentDeviceUrl);
        mContcatMessageTime.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));
        mViewAllContactList.setPaintFlags(mViewAllContactList.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }

    private void SetContactPhoto() {
        try {
            HashMap<String, DatabaseReference> contactAvataDB=new HashMap<>();
            String contacts=consersation.getListMessageData().get(position).text;
            List<VCard> vcards= Ezvcard.parse(contacts).all();
            int i=0;
            for (VCard vCard:vcards){
                for (Telephone tel: vCard.getTelephoneNumbers()){
                   if (i==1){
                       String id=tel.getText();
                       FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture").addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               if (dataSnapshot.exists()){
                                   if (dataSnapshot.getValue() != null) {
                                       String avataStr = (String) dataSnapshot.getValue();
                                       if (!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                           byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                           mFirstImageContact.setImageBitmap(BitmapFactory.decodeByteArray(decodedString,0,decodedString.length));
                                       } else {
                                           mFirstImageContact.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                       }
                                   }
                               }else {
                                   mFirstImageContact.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                               }
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {
                               mFirstImageContact.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));

                           }
                       });
                   }
                   i++;
                }
            }


    /*        if (contactList.size()<=0){
                String user_id=consersation.getListMessageData().get(position).idSender;
                String msgId=consersation.getListMessageData().get(position).msgId;
                ExtractedStrings.MESSAGES_SELECTED_ID.add(msgId);
                AllChatsDB.getInstance(context).DeleteMessages(context
                        ,user_id, ExtractedStrings.MESSAGES_SELECTED_ID);
                chat_activity.UpdateAdapterChats();
                chat_activity.clearMessageSelected();
            }else if (contactList.size()<=1){
                mViewAllContactList.setText("SAVE THIS CONTACT");
                mThirdImageContact.setVisibility(View.GONE);
                mSecondImageContact.setVisibility(View.GONE);
            }else if (contactList.size()<=2){
                mThirdImageContact.setVisibility(View.GONE);
            }
            try {
                final String id = contactList.get(1);
                FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            if (dataSnapshot.getValue() != null) {
                                String avataStr = (String) dataSnapshot.getValue();
                                if (!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                    byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                    mFirstImageContact.setImageBitmap(BitmapFactory.decodeByteArray(decodedString,0,decodedString.length));
                                } else {
                                    mFirstImageContact.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                }
                            }
                        }else {
                            mFirstImageContact.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mFirstImageContact.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));

                    }
                });
            }catch (Exception e){
                Toast.makeText(chat_activity, "In fiching contact icon", Toast.LENGTH_SHORT).show();
            }*/
        }catch (Exception r){
            Toast.makeText(context, "In View Message \n"+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SAVED)){
            messageStatus.setImageResource(R.drawable.bpg_message_saved_to_storage);
        }else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SENT)) {
            messageStatus.setImageResource(R.drawable.bpg_message_saved_to_server);
        }
        else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_RECEIVED)){
            messageStatus.setImageResource(R.drawable.bpg_message_received_by_user);
        }
    }

}
