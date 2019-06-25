package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.MyProfileDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.DateUtils.DateUtils;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 3/18/2018.
 */

public class ItemMessageFriendHolderClass {
    private ArrayList<Integer> colors;
    private LinearLayout mLayoutDateContainer;
    private CircleImageView mDateIconBihind;
    private CircleImageView mDateIconFront;
    private final EmojiconChatTextView mTextDate;
    public Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private Bitmap src;
    private int position;
    private EmojiconTextView replayer_title;
    private EmojiconTextView replayer_message;
    private CircleImageView replayer_Image;
    private LinearLayout replayer_layout_container;
    private LinearLayout message_friend_wallpaper;
    private EmojiconTextView any_mesage_time;
    private LinearLayout friend_row_container;
    private EmojiconChatTextView txtContent;
    private CircleImageView avata;
    private View itemView;

    public ItemMessageFriendHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, ArrayList<Integer> colorList, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, int position, EmojiconTextView replayer_title, EmojiconTextView replayer_message, CircleImageView replayer_image, LinearLayout replayer_layout_container, LinearLayout message_friend_wallpaper, EmojiconTextView any_mesage_time, LinearLayout friend_row_container, EmojiconChatTextView txtContent, CircleImageView avata, LinearLayout mLayoutDateContainer, CircleImageView mDateIconBehind, CircleImageView mDateIconFront, EmojiconChatTextView mTextDate, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataDB = bitmapAvataDB;
        this.bitmapAvataUser = bitmapAvataUser;
        this.colors=colorList;
        this.src = src;
        this.position = position;
        this.replayer_title = replayer_title;
        this.replayer_message = replayer_message;
        this.replayer_Image = replayer_image;
        this.replayer_layout_container = replayer_layout_container;
        this.message_friend_wallpaper = message_friend_wallpaper;
        this.any_mesage_time = any_mesage_time;
        this.friend_row_container = friend_row_container;
        this.txtContent = txtContent;
        this.avata = avata;
        this.mLayoutDateContainer=mLayoutDateContainer;
        this.mDateIconBihind=mDateIconBehind;
        this.mDateIconFront=mDateIconFront;
        this.mTextDate=mTextDate;
        this.itemView = itemView;
    }
    public void setMessageUserHolder(){
        final Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idSender);
        if (position==0) {
            friend_row_container.setPadding(5, 16, Chat_Activity.Divice_with / 6, 0);
            if (!consersation.getListMessageData().get(position).msg_reprayed_id.equals("")) {
                replayer_layout_container.setVisibility(View.VISIBLE);
                replayer_message.setText(consersation.getListMessageData().get(position).msg_reprayed_message);
              final   String id = consersation.getListMessageData().get(position).msg_reprayed_id;
                if (id.equals(ExtractedStrings.UID)){
                    replayer_title.setText("You");
                    replayer_Image.setImageBitmap(ExtractedStrings.mProfileImage);
                    replayer_Image.setBorderColor(colors.get(position));
                    replayer_title.setTextColor(colors.get(position));
                }else {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            String name= FriendDB.getInstance(context).getInfoByIdUser(1,id,context);
                            replayer_title.setText(name);
                            replayer_Image.setImageBitmap(currentAvata);
                            replayer_Image.setBorderColor(colors.get(position));
                            replayer_title.setTextColor(colors.get(position));
                        }
                    });
                }

            } else {
                replayer_layout_container.setVisibility(View.GONE);
            }
        }else {
            String Pid=consersation.getListMessageData().get(position-1).idSender;
            if (Pid.equals(ExtractedStrings.UID)){
                message_friend_wallpaper.setBackgroundResource(R.drawable.balloon_incoming_normal);
                avata.setVisibility(CircleImageView.VISIBLE);
                friend_row_container.setPadding(5,16, Chat_Activity.Divice_with/6,0);
                if (!consersation.getListMessageData().get(position).msg_reprayed_id.equals("")) {
                    replayer_layout_container.setVisibility(View.VISIBLE);
                    replayer_message.setText(consersation.getListMessageData().get(position).msg_reprayed_message);
                  final   String id = consersation.getListMessageData().get(position).msg_reprayed_id;
                    if (id.equals(ExtractedStrings.UID)){
                        replayer_title.setText("You");
                        replayer_Image.setImageBitmap(ExtractedStrings.mProfileImage);
                        replayer_Image.setBorderColor(colors.get(position));
                        replayer_title.setTextColor(colors.get(position));
                    }else {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                String name= FriendDB.getInstance(context).getInfoByIdUser(1,id,context);
                                replayer_title.setText(name);
                                replayer_Image.setImageBitmap(currentAvata);
                                replayer_Image.setBorderColor(colors.get(position));
                                replayer_title.setTextColor(colors.get(position));
                            }
                        });
                    }

                } else {
                    replayer_layout_container.setVisibility(View.GONE);
                }
            }else {
                message_friend_wallpaper.setBackgroundResource(R.drawable.balloon_incoming_normal_ext);
                avata.setVisibility(CircleImageView.GONE);
                friend_row_container.setPadding(5,0, Chat_Activity.Divice_with/6,0);

                if (!consersation.getListMessageData().get(position).msg_reprayed_id.equals("")) {
                    replayer_layout_container.setVisibility(View.VISIBLE);
                    replayer_message.setText(consersation.getListMessageData().get(position).msg_reprayed_message);
                   final String id = consersation.getListMessageData().get(position).msg_reprayed_id;
                    if (id.equals(ExtractedStrings.UID)){
                        replayer_title.setText("You");
                        replayer_Image.setImageBitmap(ExtractedStrings.mProfileImage);
                        replayer_Image.setBorderColor(colors.get(position));
                        replayer_title.setTextColor(colors.get(position));
                    }else {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                String name= FriendDB.getInstance(context).getInfoByIdUser(1,id,context);
                                replayer_title.setText(name);
                                replayer_Image.setImageBitmap(currentAvata);
                                replayer_Image.setBorderColor(colors.get(position));
                                replayer_title.setTextColor(colors.get(position));
                            }
                        });
                    }

                } else {
                    replayer_layout_container.setVisibility(View.GONE);
                }
            }
        }
        String message=consersation.getListMessageData().get(position).text;
        if (message.equals("\uD83D\uDC48")){
            txtContent.IncreaseEmojiconSize(40);
            txtContent.setEmojiconSize(90);
            message_friend_wallpaper.setBackgroundResource(R.drawable.transparent);
        }else {
            txtContent.setEmojiconSize(30);
        }
        String s = message;
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<8;i++){
            builder.append(" ");
        }
        txtContent.setText(s+builder.toString());
        any_mesage_time.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));

        if (position==0){
            mLayoutDateContainer.setVisibility(View.VISIBLE);
            if (currentAvata != null) {
                mDateIconBihind.setImageBitmap(currentAvata);
            } else {
                String imageurl=FriendDB.getInstance(context).getInfoByIdUser(3,  consersation.getListMessageData().get(position).idReceiver,context);
                Glide.with(context).load(imageurl).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mDateIconBihind);
            }
            if (ExtractedStrings.mProfileImage!=null){
                mDateIconFront.setImageBitmap(ExtractedStrings.mProfileImage);
            }else {
                String imagePath= MyProfileDB.getInstance(context).getInfoUser(5,context);
                if (new File(new CompressingImage(context).getRealPathFromURI(imagePath)).exists()){
                    Glide.with(context).load(imagePath).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mDateIconFront);
                }else {
                    mDateIconFront.setImageResource(R.drawable.avatar_default);
                }
            }
            mTextDate.setText(DateUtils.getChatTimeDate(consersation.getListMessageData().get(position).timestamp));
        }else {
            if (!DateUtils.hasSameDate(consersation.getListMessageData().get(position).timestamp, consersation.getListMessageData().get(position-1).timestamp)) {
                mLayoutDateContainer.setVisibility(View.VISIBLE);
                message_friend_wallpaper.setBackgroundResource(R.drawable.balloon_incoming_normal);
                avata.setVisibility(CircleImageView.VISIBLE);
                if (currentAvata != null) {
                    mDateIconBihind.setImageBitmap(currentAvata);
                } else {
                    String imageurl=FriendDB.getInstance(context).getInfoByIdUser(3,  consersation.getListMessageData().get(position).idReceiver,context);
                    Glide.with(context).load(imageurl).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mDateIconBihind);
                }
                if (ExtractedStrings.mProfileImage!=null){
                    mDateIconFront.setImageBitmap(ExtractedStrings.mProfileImage);
                }else {
                    String imagePath= MyProfileDB.getInstance(context).getInfoUser(5,context);
                    if (new File(new CompressingImage(context).getRealPathFromURI(imagePath)).exists()){
                        Glide.with(context).load(imagePath).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mDateIconFront);
                    }else {
                        mDateIconFront.setImageResource(R.drawable.avatar_default);
                    }
                }
                mTextDate.setText(DateUtils.getChatTimeDate(consersation.getListMessageData().get(position).timestamp));
            }else {
                mLayoutDateContainer.setVisibility(View.GONE);
            }
        }

        if (currentAvata != null) {
            avata.setImageBitmap(currentAvata);
        } else {
            final String id = consersation.getListMessageData().get(position).idSender;
            if(bitmapAvataDB.get(id) == null){
                bitmapAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture"));
                bitmapAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            String avataStr = (String) dataSnapshot.getValue();
                            if(!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                            }else{
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                            }
                           // chat_activity.notifyDataSetChanged();
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
        FirebaseDatabase.getInstance().getReference().child("Notifications/"+consersation.getListMessageData().get(position).idSender).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChild(consersation.getListMessageData().get(position).msgId)){
                    Toast.makeText(chat_activity, position, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
   }
}

