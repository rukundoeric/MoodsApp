package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.MyProfileDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.model.MyProfile;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.AudioUtils.PlaySound;
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

public class ItemMessageUserHolderClass {
    private  ArrayList<Integer> colors;
    private CircleImageView replayer_image;
    private ImageView messageStatus;
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
    private LinearLayout replayer_layout_container;
    private LinearLayout message_user_wallpaper;
    private EmojiconTextView any_user_message_time;
    private LinearLayout user_row_container;
    private EmojiconChatTextView txtContent;
    private CircleImageView avata;
    private LinearLayout mLayoutLooper;
    private View itemView;
    private LinearLayout mLayoutDateContainer;
    private CircleImageView mDateIconBihind;
    private CircleImageView mDateIconFront;
    private final EmojiconChatTextView mTextDate;

    public ItemMessageUserHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, ArrayList<Integer> colorList, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, int position, EmojiconTextView replayer_title, EmojiconTextView replayer_message, LinearLayout replayer_layout_container, LinearLayout message_user_wallpaper, EmojiconTextView any_user_message_time, LinearLayout user_row_container, EmojiconChatTextView txtContent, CircleImageView avata, LinearLayout mLayoutDateContainer, CircleImageView mDateIconBehind, CircleImageView mDateIconFront, EmojiconChatTextView mTextDate, LinearLayout mLayout_message_looper, ImageView messageStatus, CircleImageView replayer_image, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.colors=colorList;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataDB = bitmapAvataDB;
        this.bitmapAvataUser = bitmapAvataUser;
        this.src = src;
        this.position = position;
        this.replayer_title = replayer_title;
        this.replayer_message = replayer_message;
        this.replayer_image=replayer_image;
        this.replayer_layout_container = replayer_layout_container;
        this.message_user_wallpaper = message_user_wallpaper;
        this.any_user_message_time = any_user_message_time;
        this.user_row_container = user_row_container;
        this.txtContent = txtContent;
        this.avata = avata;
        this.mLayoutDateContainer=mLayoutDateContainer;
        this.mDateIconBihind=mDateIconBehind;
        this.mDateIconFront=mDateIconFront;
        this.mTextDate=mTextDate;
        this.mLayoutLooper=mLayout_message_looper;
        this.itemView = itemView;
        this.messageStatus=messageStatus;

    }
    public void  setMessageUserMessageHolder(){
        final Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idReceiver);
        if (position==0){
            user_row_container.setPadding(Chat_Activity.Divice_with/5,16,0,2);
            if (!consersation.getListMessageData().get(position).msg_reprayed_message.equals("")){
                replayer_layout_container.setVisibility(View.VISIBLE);
                replayer_message.setText(consersation.getListMessageData().get(position).msg_reprayed_message);
                if (!consersation.getListMessageData().get(position).msg_reprayed_id.equals("")|| consersation.getListMessageData().get(position).msg_reprayed_id!=null){
                    final String id=consersation.getListMessageData().get(position).msg_reprayed_id;
                    if (id.equals(ExtractedStrings.UID)){
                        replayer_title.setText("You");
                        replayer_image.setImageBitmap(ExtractedStrings.mProfileImage);
                        replayer_image.setBorderColor(colors.get(position));
                        replayer_title.setTextColor(colors.get(position));
                    }else {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                String name= FriendDB.getInstance(context).getInfoByIdUser(1,id,context);
                                replayer_title.setText(name);
                                replayer_image.setImageBitmap(currentAvata);
                                replayer_image.setBorderColor(colors.get(position));
                                replayer_title.setTextColor(colors.get(position));
                            }
                        });
                    }

                }
            }else {
                replayer_layout_container.setVisibility(View.GONE);
            }
        }else {
            if (consersation.getListMessageData().get(position-1).idSender.equals(ExtractedStrings.UID)){
                message_user_wallpaper.setBackgroundResource(R.drawable.balloon_outgoing_normal_ext);
                user_row_container.setPadding(Chat_Activity.Divice_with/5, 0,0, 0);
                if (!consersation.getListMessageData().get(position).msg_reprayed_message.equals("")){
                    replayer_layout_container.setVisibility(View.VISIBLE);
                    replayer_message.setText(consersation.getListMessageData().get(position).msg_reprayed_message);
                    if (!consersation.getListMessageData().get(position).msg_reprayed_id.equals("")|| consersation.getListMessageData().get(position).msg_reprayed_id!=null){
                        final String id=consersation.getListMessageData().get(position).msg_reprayed_id;
                        if (id.equals(ExtractedStrings.UID)){
                            replayer_title.setText("You");
                            replayer_image.setImageBitmap(ExtractedStrings.mProfileImage);
                            replayer_image.setBorderColor(colors.get(position));
                            replayer_title.setTextColor(colors.get(position));
                        }else {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    String name= FriendDB.getInstance(context).getInfoByIdUser(1,id,context);
                                    replayer_title.setText(name);
                                    replayer_image.setImageBitmap(currentAvata);
                                    replayer_image.setBorderColor(colors.get(position));
                                    replayer_title.setTextColor(colors.get(position));
                                }
                            });
                        }

                    }
                }else {
                    replayer_layout_container.setVisibility(View.GONE);
                }
            }else {
                message_user_wallpaper.setBackgroundResource(R.drawable.balloon_outgoing_normal);
                user_row_container.setPadding(Chat_Activity.Divice_with/5,10,0,2);
                if (!consersation.getListMessageData().get(position).msg_reprayed_message.equals("")){
                    replayer_layout_container.setVisibility(View.VISIBLE);
                    replayer_message.setText(consersation.getListMessageData().get(position).msg_reprayed_message);
                    if (!consersation.getListMessageData().get(position).msg_reprayed_id.equals("")|| consersation.getListMessageData().get(position).msg_reprayed_id!=null){
                        final String id=consersation.getListMessageData().get(position).msg_reprayed_id;
                        if (id.equals(ExtractedStrings.UID)){
                            replayer_title.setText("You");
                            replayer_image.setImageBitmap(ExtractedStrings.mProfileImage);
                            replayer_image.setBorderColor(colors.get(position));
                            replayer_title.setTextColor(colors.get(position));
                        }else {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    String name= FriendDB.getInstance(context).getInfoByIdUser(1,id,context);
                                    replayer_title.setText(name);
                                    replayer_image.setImageBitmap(currentAvata);
                                    replayer_image.setBorderColor(colors.get(position));
                                    replayer_title.setTextColor(colors.get(position));
                                }
                            });
                        }
                    }
                }else {
                    replayer_layout_container.setVisibility(View.GONE);

                }
            }
        }



      if (position==0){
          mLayoutDateContainer.setVisibility(View.VISIBLE);
          if (currentAvata != null) {
              mDateIconFront.setImageBitmap(currentAvata);
          } else {
              String imageurl=FriendDB.getInstance(context).getInfoByIdUser(3,  consersation.getListMessageData().get(position).idReceiver,context);
              Glide.with(context).load(imageurl).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mDateIconFront);
          }
          if (ExtractedStrings.mProfileImage!=null){
              mDateIconBihind.setImageBitmap(ExtractedStrings.mProfileImage);
          }else {
              String imagePath= MyProfileDB.getInstance(context).getInfoUser(5,context);
              if (new File(new CompressingImage(context).getRealPathFromURI(imagePath)).exists()){
                  Glide.with(context).load(imagePath).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mDateIconBihind);
              }else {
                  mDateIconBihind.setImageResource(R.drawable.avatar_default);
              }
         }
          mTextDate.setText(DateUtils.getChatTimeDate(consersation.getListMessageData().get(position).timestamp));
      }else {
          if (!DateUtils.hasSameDate(consersation.getListMessageData().get(position).timestamp, consersation.getListMessageData().get(position-1).timestamp)) {
              mLayoutDateContainer.setVisibility(View.VISIBLE);
              message_user_wallpaper.setBackgroundResource(R.drawable.balloon_outgoing_normal);
              mTextDate.setText(DateUtils.getChatTimeDate(consersation.getListMessageData().get(position).timestamp));
              if (currentAvata != null) {
                  mDateIconFront.setImageBitmap(currentAvata);
              } else {
                  String imageurl=FriendDB.getInstance(context).getInfoByIdUser(3,  consersation.getListMessageData().get(position).idReceiver,context);
                  Glide.with(context).load(imageurl).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mDateIconFront);
              }
              if (ExtractedStrings.mProfileImage!=null){
                  mDateIconBihind.setImageBitmap(ExtractedStrings.mProfileImage);
              }else {
                  String imagePath= MyProfileDB.getInstance(context).getInfoUser(5,context);
                  if (new File(new CompressingImage(context).getRealPathFromURI(imagePath)).exists()){
                      Glide.with(context).load(imagePath).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mDateIconBihind);
                  }else {
                      mDateIconBihind.setImageResource(R.drawable.avatar_default);
                  }
              }
          }else {
              mLayoutDateContainer.setVisibility(View.GONE);
          }
      }

        String message=consersation.getListMessageData().get(position).text;

            if (message.equals("\uD83D\uDC48")){
                txtContent.IncreaseEmojiconSize(40);
                message_user_wallpaper.setBackgroundResource(R.drawable.transparent);

            }else {
                txtContent.setEmojiconSize(30);
            }
        String s = message;
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<15;i++){
            builder.append(" ");
        }
       txtContent.setText(s+builder.toString());

        any_user_message_time.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));

     /*   if (txtContent.getLineCount()>1){
            mLayoutLooper.setOrientation(LinearLayout.VERTICAL);
        }else {
            mLayoutLooper.setOrientation(LinearLayout.HORIZONTAL);
        }*/

        if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SAVED)){
            if (chat_activity.isRetryAllowed && position==consersation.getListMessageData().size()-1){
                messageStatus.setImageResource(R.drawable.bpg_message_saved_to_storage);
                Handler mHandler=new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (final CharSequence s:chat_activity.idFriends){
                            if (!String.valueOf(s).equals(ExtractedStrings.UID)){
                                final Message newMessage = new Message();
                                newMessage.msgId=consersation.getListMessageData().get(position).msgId;
                                newMessage.msgType=consersation.getListMessageData().get(position).msgType;
                                newMessage.idRoom= consersation.getListMessageData().get(position).idRoom;
                                newMessage.idSender = consersation.getListMessageData().get(position).idSender;
                                newMessage.idReceiver = consersation.getListMessageData().get(position).idReceiver;
                                newMessage.text = consersation.getListMessageData().get(position).text;
                                newMessage.timestamp = consersation.getListMessageData().get(position).timestamp;
                                newMessage.PhotoDeviceUrl=consersation.getListMessageData().get(position).PhotoDeviceUrl;
                                newMessage.PhotoStringBase64=consersation.getListMessageData().get(position).PhotoStringBase64;
                                newMessage.VideoDeviceUrl=consersation.getListMessageData().get(position).VideoDeviceUrl;
                                newMessage.VoiceDeviceUrl=consersation.getListMessageData().get(position).VoiceDeviceUrl;
                                newMessage.DocumentDeviceUrl=consersation.getListMessageData().get(position).DocumentDeviceUrl;
                                newMessage.AnyMediaUrl=consersation.getListMessageData().get(position).AnyMediaUrl;
                                newMessage.AnyMediaStatus=consersation.getListMessageData().get(position).AnyMediaStatus;
                                newMessage.msg_reprayed_message = consersation.getListMessageData().get(position).msg_reprayed_message;
                                newMessage.msg_reprayed_id= consersation.getListMessageData().get(position).msg_reprayed_id;
                                newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SENT;
                                FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(String.valueOf(s))).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(newMessage.msgId)){
                                            AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
                                            messageStatus.setImageResource(R.drawable.bpg_message_saved_to_server);
                                        }else {
                                            FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(String.valueOf(s))).child(newMessage.msgId).setValue(newMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    new PlaySound(context, R.raw.messagesent);
                                                    AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
                                                    messageStatus.setImageResource(R.drawable.bpg_message_saved_to_server);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }
                });
            }
        }else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SENT)) {
            Handler handler=new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    FirebaseDatabase.getInstance().getReference().child("Notifications/"+consersation.getListMessageData().get(position).idReceiver).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(consersation.getListMessageData().get(position).msgId)){
                                messageStatus.setImageResource(R.drawable.bpg_message_saved_to_server);
                            }else {
                                final Message newMessage = new Message();
                                newMessage.msgId=consersation.getListMessageData().get(position).msgId;
                                newMessage.msgType=consersation.getListMessageData().get(position).msgType;
                                newMessage.idRoom= consersation.getListMessageData().get(position).idRoom;
                                newMessage.idSender = consersation.getListMessageData().get(position).idSender;
                                newMessage.idReceiver = consersation.getListMessageData().get(position).idReceiver;
                                newMessage.text = consersation.getListMessageData().get(position).text;
                                newMessage.timestamp = consersation.getListMessageData().get(position).timestamp;
                                newMessage.PhotoDeviceUrl=consersation.getListMessageData().get(position).PhotoDeviceUrl;
                                newMessage.PhotoStringBase64=consersation.getListMessageData().get(position).PhotoStringBase64;
                                newMessage.VideoDeviceUrl=consersation.getListMessageData().get(position).VideoDeviceUrl;
                                newMessage.VoiceDeviceUrl=consersation.getListMessageData().get(position).VoiceDeviceUrl;
                                newMessage.DocumentDeviceUrl=consersation.getListMessageData().get(position).DocumentDeviceUrl;
                                newMessage.AnyMediaUrl=consersation.getListMessageData().get(position).AnyMediaUrl;
                                newMessage.AnyMediaStatus=consersation.getListMessageData().get(position).AnyMediaStatus;
                                newMessage.msg_reprayed_message = consersation.getListMessageData().get(position).msg_reprayed_message;
                                newMessage.msg_reprayed_id= consersation.getListMessageData().get(position).msg_reprayed_id;
                                newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_RECEIVED;
                                AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
                                messageStatus.setImageResource(R.drawable.bpg_message_received_by_user);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
        else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_RECEIVED)){
            messageStatus.setImageResource(R.drawable.bpg_message_received_by_user);
        }
    }

}
