package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class ItemMessageContactFriendHolderClass {


    private final ArrayList<Integer> colors;
    private Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private Bitmap src;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private EmojiconTextView mViewConatct;
    private EmojiconTextView mTextContactView;
    private EmojiconTextView mTextContactTime;
    private CircleImageView mThirdContactPhoto;
    private CircleImageView mFirstContactPhoto;
    private CircleImageView  mSecondContactPhoto;
    private CircleImageView avata;
    private LinearLayout mLayoutContaner;
    private LinearLayout mLayoutContactRunder;
    private View itemView;

    public ItemMessageContactFriendHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, ArrayList<Integer> colorList, EmojiconTextView mViewConatct, EmojiconTextView mTextContactView, EmojiconTextView mTextContactTime, CircleImageView mThirdContactPhoto, CircleImageView mFirstContactPhoto, CircleImageView mSecondContactPhoto, CircleImageView avata, LinearLayout mLayoutContaner, LinearLayout mLayoutContactRunder, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataDB = bitmapAvataDB;
        this.bitmapAvataUser = bitmapAvataUser;
        this.src = src;
        this.position = position;
        this.colors=colorList;
        this.mViewConatct = mViewConatct;
        this.mTextContactView = mTextContactView;
        this.mTextContactTime = mTextContactTime;
        this.mThirdContactPhoto = mThirdContactPhoto;
        this.mFirstContactPhoto = mFirstContactPhoto;
        this.mSecondContactPhoto = mSecondContactPhoto;
        this.avata = avata;
        this.mLayoutContaner = mLayoutContaner;
        this.mLayoutContactRunder = mLayoutContactRunder;
        this.itemView = itemView;
    }

    public void setMessageContactFriendHolder() {
        if (position == 0) {
            mLayoutContaner.setPadding(5, 16, ExtractedStrings.DeviceWidth / 6, 0);
            mLayoutContactRunder.setBackgroundResource(R.drawable.balloon_incoming_normal);
        } else {
            if (!consersation.getListMessageData().get(position - 1).idSender.equals(ExtractedStrings.UID)) {
                mLayoutContactRunder.setBackgroundResource(R.drawable.balloon_incoming_normal_ext);
                avata.setVisibility(CircleImageView.GONE);
                mLayoutContaner.setPadding(5, 2, ExtractedStrings.DeviceWidth / 6, 0);
            } else {
                mLayoutContactRunder.setBackgroundResource(R.drawable.balloon_incoming_normal);
                avata.setVisibility(CircleImageView.VISIBLE);
                mLayoutContaner.setPadding(5, 16, ExtractedStrings.DeviceWidth / 6, 0);
            }
        }
        Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idSender);
        if (currentAvata != null) {
            avata.setImageBitmap(currentAvata);
        } else {
            final String id = consersation.getListMessageData().get(position).idSender;
            if (bitmapAvataDB.get(id) == null) {
                bitmapAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture"));
                bitmapAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            String avataStr = (String) dataSnapshot.getValue();
                            if (!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                            } else {
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        avata.setBorderWidth(2);
        avata.setBorderColor(colors.get(position));
        mTextContactView.setText(consersation.getListMessageData().get(position).DocumentDeviceUrl);
        mTextContactTime.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));
        mViewConatct.setPaintFlags(mViewConatct.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
    private void SetContactPhoto() {
        try {
            HashMap<String, DatabaseReference> contactAvataDB=new HashMap<>();
            String phone="";
            String contacts=consersation.getListMessageData().get(position).text;
            ArrayList<String> contactList=new ArrayList<>();
            List<VCard> vcards= Ezvcard.parse(contacts).all();
            for (VCard vCard:vcards){
                for (Telephone tel: vCard.getTelephoneNumbers()){
                    phone=tel.getText();
                }
                contactList.add(phone);
            }
            if (contactList.size()<=0){
                String user_id=consersation.getListMessageData().get(position).idSender;
                String msgId=consersation.getListMessageData().get(position).msgId;
                ExtractedStrings.MESSAGES_SELECTED_ID.add(msgId);
                AllChatsDB.getInstance(context).DeleteMessages(context
                        ,user_id, ExtractedStrings.UID,ExtractedStrings.MESSAGES_SELECTED_ID);
                chat_activity.UpdateAdapterChats(true);
                chat_activity.clearMessageSelected();
            }
            for (int i=1;i<=3;i++){
                if (i==1){
                    mFirstContactPhoto.setVisibility(View.VISIBLE);
                    try {
                        final String id = contactList.get(i);
                        if (contactAvataDB.get(id) == null) {
                            contactAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture"));
                            contactAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        if (dataSnapshot.getValue() != null) {
                                            String avataStr = (String) dataSnapshot.getValue();
                                            if (!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                                byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                                mFirstContactPhoto.setImageBitmap(BitmapFactory.decodeByteArray(decodedString,0,decodedString.length));
                                            } else {
                                                mFirstContactPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                            }
                                        }
                                    }else {
                                        mFirstContactPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    mFirstContactPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                }
                            });
                        }
                    }catch (Exception e){
                        Toast.makeText(chat_activity, "In fiching contact icon", Toast.LENGTH_SHORT).show();
                    }
                }else if (i==2){
                    mSecondContactPhoto.setVisibility(View.VISIBLE);
                    try {
                        final String id = contactList.get(i);
                        if (contactAvataDB.get(id) == null) {
                            contactAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture"));
                            contactAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        if (dataSnapshot.getValue() != null) {
                                            String avataStr = (String) dataSnapshot.getValue();
                                            if (!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                                byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                                mSecondContactPhoto.setImageBitmap(BitmapFactory.decodeByteArray(decodedString,0,decodedString.length));
                                            } else {
                                                mSecondContactPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                            }
                                        }
                                    }else {
                                        mSecondContactPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    mSecondContactPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                }
                            });
                        }
                    }catch (Exception e){
                        Toast.makeText(chat_activity, "In fiching contact icon", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    mThirdContactPhoto.setVisibility(View.VISIBLE);
                    try {
                        final String id = contactList.get(i);
                        if (contactAvataDB.get(id) == null) {
                            contactAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture"));
                            contactAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        if (dataSnapshot.getValue() != null) {
                                            String avataStr = (String) dataSnapshot.getValue();
                                            if (!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                                byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                                mThirdContactPhoto.setImageBitmap(BitmapFactory.decodeByteArray(decodedString,0,decodedString.length));
                                            } else {
                                                mThirdContactPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                            }
                                        }
                                    }else {
                                        mThirdContactPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    mThirdContactPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_default));
                                }
                            });
                        }
                    }catch (Exception e){
                        Toast.makeText(chat_activity, "In fiching contact icon", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (Exception r){
            Toast.makeText(context, "In View Message \n"+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
